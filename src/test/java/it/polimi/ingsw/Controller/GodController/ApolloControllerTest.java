package it.polimi.ingsw.Controller.GodController;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.MoveMessages.MoveMessage;
import it.polimi.ingsw.Message.MoveMessages.StandardMoveMessage;
import it.polimi.ingsw.Message.MoveMessages.SwapMessage;
import it.polimi.ingsw.Message.PosMessage;
import it.polimi.ingsw.Message.TileToShowMessages.StandardTileMessage;
import it.polimi.ingsw.Message.WinMessage;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Observer.Observer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApolloControllerTest {
    private class Receiver implements Observer<GameMessage> {
        GameMessage receivedMessage;

        @Override
        public void update(GameMessage message) {
            this.receivedMessage = message;
        }
    }

    Model model;
    List<Player> pList;
    Controller controller;
    ApolloControllerTest.Receiver r = new ApolloControllerTest.Receiver();

    @BeforeEach
    void init(){
        pList = createPlayer(new Apollo(), new Artemis());
        model = new Model(pList);
        controller = new Controller(model);
        model.addObserver(r);
    }

    // -----------------      prepareSpecialChooseConstructor TESTS      -----------------

    @Test
    void standardPrepareConstructor(){
        List<Position> posList = new ArrayList<>();
        posList.add(new Position(1,2));
        posList.add(new Position(2,0));
        posList.add(new Position(3,2));
        posList.add(new Position(3,4));

        int n = 0;
        for(Player p : pList){
            for(int i = 0; i < 2; i++){
                model.setCurrentConstructor(p.getAllConstructors().get(i));
                model.performMove(posList.get(i + n));
            }
            n = 2;
        }

        model.startGame();

        ApolloController apController = (ApolloController) model.getCurrentGod().getGodController();
        apController.prepareSpecialChooseConstructor(model, controller);

        assertNotNull(r.receivedMessage, "Should be received a message");
        assertTrue(r.receivedMessage instanceof StandardTileMessage, "This should be a standard TileToShowMessage");

        StandardTileMessage message = (StandardTileMessage) r.receivedMessage;
        List<Position> toShow = message.getTileToShow();
        assertTrue(toShow.get(0).equals(posList.get(0)), "This should be " + posList.get(0).toString() + " and instead is " + toShow.get(0).toString());
        assertTrue(toShow.get(1).equals(posList.get(1)), "This should be " + posList.get(1).toString() + " and instead is " + toShow.get(1).toString());
    }

    @Test
    void notBlockedByOtherConstructor(){
        List<Position> posList = new ArrayList<>();
        posList.add(new Position(0,0));
        posList.add(new Position(0,1));
        posList.add(new Position(1,0));
        posList.add(new Position(1,1));

        int n = 0;
        for(Player p : pList){
            for(int i = 0; i < 2; i++){
                model.setCurrentConstructor(p.getAllConstructors().get(i));
                model.performMove(posList.get(i + n));
            }
            n = 2;
        }

        model.startGame();

        ApolloController apController = (ApolloController) model.getCurrentGod().getGodController();
        apController.prepareSpecialChooseConstructor(model, controller);

        assertTrue(pList.get(0).getAllConstructors().get(0).getCanMove(), "Should be able to move");

        assertNotNull(r.receivedMessage, "Should be received a message");
        assertTrue(r.receivedMessage instanceof StandardTileMessage, "This should be a standard TileToShowMessage");

        StandardTileMessage message = (StandardTileMessage) r.receivedMessage;
        List<Position> toShow = message.getTileToShow();
        assertTrue(toShow.get(0).equals(posList.get(0)), "This should be " + posList.get(0).toString() + " and instead is " + toShow.get(0).toString());
        assertTrue(toShow.get(1).equals(posList.get(1)), "This should be " + posList.get(1).toString() + " and instead is " + toShow.get(1).toString());
    }

    @Test
    void blockedByDome(){
        List<Position> posList = new ArrayList<>();
        posList.add(new Position(0,0));
        posList.add(new Position(2,0));
        posList.add(new Position(3,2));
        posList.add(new Position(3,4));

        int n = 0;
        for(Player p : pList){
            for(int i = 0; i < 2; i++){
                model.setCurrentConstructor(p.getAllConstructors().get(i));
                model.performMove(posList.get(i + n));
            }
            n = 2;
        }
        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        increaseConstrLevel(new Position(0,1), 4);
        increaseConstrLevel(new Position(1,0), 3);
        increaseConstrLevel(new Position(1,1), 4);


        model.startGame();

        ApolloController apController = (ApolloController) model.getCurrentGod().getGodController();
        apController.prepareSpecialChooseConstructor(model, controller);

        assertNotNull(r.receivedMessage, "Should be received a message");
        assertTrue(r.receivedMessage instanceof StandardTileMessage, "This should be a standard TileToShowMessage");

        StandardTileMessage message = (StandardTileMessage) r.receivedMessage;
        List<Position> toShow = message.getTileToShow();

        assertEquals(1, toShow.size(), "Should only be the second constructor (size == 1)");
        assertTrue(toShow.get(0).equals(posList.get(1)), "This should be " + posList.get(1).toString() + " and instead is " + toShow.get(0).toString());
    }

    @RepeatedTest(2)
    void specialChooseConstructorTest(RepetitionInfo repetitionInfo)    {
        if(repetitionInfo.getCurrentRepetition() == 1)  {//isLosing == TRUE
            List<Position> posList = new ArrayList<>();
            posList.add(new Position(0,0));
            posList.add(new Position(0,1));
            posList.add(new Position(3,3));
            posList.add(new Position(4,4));

            int n = 0;
            System.out.println(pList.get(0).getGod().getGodName());
            for(Player p : pList){
                for(int i = 0; i < 2; i++){
                    model.setCurrentConstructor(p.getAllConstructors().get(i));
                    model.performMove(posList.get(i + n));
                }
                n = 2;
            }

            increaseConstrLevel(new Position(1,0), 2);
            increaseConstrLevel(new Position(1,1), 2);
            increaseConstrLevel(new Position(1,2), 2);
            increaseConstrLevel(new Position(0,2), 2);

            model.startGame();

            ApolloController apolloController = new ApolloController();
            apolloController.prepareSpecialChooseConstructor(model, controller);

            assertTrue(pList.size() == 1);
        }

        else if(repetitionInfo.getCurrentRepetition() == 2) {
            List<Position> posList = new ArrayList<>();
            posList.add(new Position(0,0));
            posList.add(new Position(0,1));
            posList.add(new Position(3,3));
            posList.add(new Position(4,4));

            int n = 0;
            System.out.println(pList.get(0).getGod().getGodName());
            for(Player p : pList){
                for(int i = 0; i < 2; i++){
                    model.setCurrentConstructor(p.getAllConstructors().get(i));
                    model.performMove(posList.get(i + n));
                }
                n = 2;
            }
            model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
            model.removePlayer("due");
            model.startGame();

            ApolloController apolloController = new ApolloController();
            apolloController.prepareSpecialChooseConstructor(model, controller);

            assertTrue(r.receivedMessage instanceof WinMessage);
        }
    }

    // -----------------            prepareSpecialMove TESTS            -----------------
    @Test
    void standardPrepareSpecialMove(){
        List<Position> posList = new ArrayList<>();
        posList.add(new Position(1,2));
        posList.add(new Position(2,0));
        posList.add(new Position(3,2));
        posList.add(new Position(3,4));

        int n = 0;
        for(Player p : pList){
            for(int i = 0; i < 2; i++){
                model.setCurrentConstructor(p.getAllConstructors().get(i));
                model.performMove(posList.get(i + n));
            }
            n = 2;
        }

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.startGame();

        ApolloController apController = (ApolloController) model.getCurrentGod().getGodController();
        apController.prepareSpecialMove(model, controller);

        assertTrue(r.receivedMessage instanceof StandardTileMessage, "This should be a normal tile message");

        StandardTileMessage message = (StandardTileMessage)r.receivedMessage;
        List<Position> toShow = message.getTileToShow();

        assertEquals(8, toShow.size(), "This should contain all 8 tiles around position (1,2)");

        int k = 0;
        for(int i=0; i<3; i++){
            for(int j=1; j<4; j++){
                if(!(i == 1 && j == 2)){
                    assertTrue(toShow.get(k).equals(new Position(i,j)), "This Position should be (" + i + "," + j + ") and instead is " + toShow.get(k).toString());
                    k++;
                }
            }
        }

    }

    @Test
    void notBlockedByDome(){
        List<Position> posList = new ArrayList<>();
        posList.add(new Position(0,0));
        posList.add(new Position(0,1));
        posList.add(new Position(1,0));
        posList.add(new Position(1,1));

        int n = 0;
        for(Player p : pList){
            for(int i = 0; i < 2; i++){
                model.setCurrentConstructor(p.getAllConstructors().get(i));
                model.performMove(posList.get(i + n));
            }
            n = 2;
        }

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.startGame();

        ApolloController apController = (ApolloController) model.getCurrentGod().getGodController();
        apController.prepareSpecialMove(model, controller);

        assertTrue(r.receivedMessage instanceof StandardTileMessage, "This should be a normal tile message");

        StandardTileMessage message = (StandardTileMessage)r.receivedMessage;
        List<Position> toShow = message.getTileToShow();

        assertEquals(2, toShow.size(), "This should contain the 2 occupied cell at (1,0) and (1,1)");
        assertTrue(toShow.get(0).equals(posList.get(2)), "This Position should be " + posList.get(2).toString() + " and instead is " + toShow.get(0).toString());
        assertTrue(toShow.get(1).equals(posList.get(3)), "This Position should be " + posList.get(3).toString() + " and instead is " + toShow.get(1).toString());

    }

    // -----------------             handleSpecialMove TESTS             -----------------
    // TO DO: TESTS ON checkWin() == true
    @Test
    void standardSpecialMove(){
        List<Position> posList = new ArrayList<>();
        posList.add(new Position(1,2));
        posList.add(new Position(2,0));
        posList.add(new Position(3,2));
        posList.add(new Position(3,4));

        int n = 0;
        for(Player p : pList){
            for(int i = 0; i < 2; i++){
                model.setCurrentConstructor(p.getAllConstructors().get(i));
                model.performMove(posList.get(i + n));
            }
            n = 2;
        }

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.startGame();

        PosMessage message = new PosMessage("shish", "uno", null, new Position(1,3));
        ApolloController apController = (ApolloController) model.getCurrentGod().getGodController();
        apController.handleSpecialMove(model, controller, message);

        assertTrue(r.receivedMessage instanceof MoveMessage, "This should be a move message");
        assertTrue(r.receivedMessage instanceof StandardMoveMessage, "This should be a standard move message");

        StandardMoveMessage m = (StandardMoveMessage) r.receivedMessage;
        assertEquals(1, m.getConstructorMatrix()[1][3], "(1,3) Should be occupied by a Player 1 constructor");
    }

    @Test
    void trySwap(){
        List<Position> posList = new ArrayList<>();
        posList.add(new Position(0,0));
        posList.add(new Position(0,1));
        posList.add(new Position(1,0));
        posList.add(new Position(1,1));

        int n = 0;
        for(Player p : pList){
            for(int i = 0; i < 2; i++){
                model.setCurrentConstructor(p.getAllConstructors().get(i));
                model.performMove(posList.get(i + n));
            }
            n = 2;
        }

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.startGame();

        PosMessage message = new PosMessage("shish", "uno", null, new Position(1,0));
        ApolloController apController = (ApolloController) model.getCurrentGod().getGodController();
        apController.handleSpecialMove(model, controller, message);

        assertTrue(r.receivedMessage instanceof MoveMessage, "This should be a move message");
        assertTrue(r.receivedMessage instanceof SwapMessage, "This should be a swap move message");

        SwapMessage m = (SwapMessage) r.receivedMessage;
        assertEquals(1, m.getConstructorMatrix()[1][0], "(1,0) Should be occupied by a Player 1 constructor");
        assertEquals(2, m.getConstructorMatrix()[0][0], "(0,0) Should be occupied by a Player 2 constructor");
    }

    @Test
    void handleSpecialMoveExecuteWinTest()  {
        List<Position> posList = new ArrayList<>();
        posList.add(new Position(0,0));
        posList.add(new Position(0,1));
        posList.add(new Position(1,0));
        posList.add(new Position(1,1));

        int n = 0;
        for(Player p : pList){
            for(int i = 0; i < 2; i++){
                model.setCurrentConstructor(p.getAllConstructors().get(i));
                model.performMove(posList.get(i + n));
            }
            n = 2;
        }

        increaseConstrLevel(new Position(1,0), 3);
        increaseConstrLevel(new Position(0,0), 2);

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.startGame();

        ApolloController apolloController = (ApolloController)model.getCurrentGod().getGodController();
        PosMessage message = new PosMessage("shish", "uno", null, new Position(1,0));
        apolloController.handleSpecialMove(model, controller, message);

        assertTrue(r.receivedMessage instanceof WinMessage, "This should be a WinMessage");
    }

    @Test
    void handleSpecialChooseConstructor()   {
        List<Position> posList = new ArrayList<>();
        posList.add(new Position(0,0));
        posList.add(new Position(0,1));
        posList.add(new Position(1,0));
        posList.add(new Position(1,1));

        int n = 0;
        for(Player p : pList){
            for(int i = 0; i < 2; i++){
                model.setCurrentConstructor(p.getAllConstructors().get(i));
                model.performMove(posList.get(i + n));
            }
            n = 2;
        }
        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.startGame();
        ApolloController apolloController = (ApolloController) model.getCurrentGod().getGodController();
        PosMessage posMessage = new PosMessage("Shish", pList.get(0).getIdPlayer(), null, new Position(0,0));
        apolloController.handleSpecialChooseConstructor(model, controller, posMessage);

        assertEquals(pList.get(0).getAllConstructors().get(0), model.getCurrentConstructor());
    }

    // ----------------           Helper methods           ----------------
    private List<Player> createPlayer(God p1God, God p2God) {
        Player p1 = new Player("uno", 1);
        Player p2 = new Player("due", 2);

        p1.setGod(p1God);
        p2.setGod(p2God);

        List<Player> pList = new ArrayList<>();
        pList.add(p1);
        pList.add(p2);

        return pList;
    }

    private void increaseConstrLevel(Position pos, int howManyLevel){
        for(int i=0; i< howManyLevel; i++)
            model.performBuild(pos);
    }

}