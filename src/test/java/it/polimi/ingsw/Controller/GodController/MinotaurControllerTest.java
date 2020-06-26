package it.polimi.ingsw.Controller.GodController;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.MoveMessages.MoveMessage;
import it.polimi.ingsw.Message.MoveMessages.PushMessage;
import it.polimi.ingsw.Message.MoveMessages.StandardMoveMessage;
import it.polimi.ingsw.Message.PosMessage;
import it.polimi.ingsw.Message.TileToShowMessages.StandardTileMessage;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Observer.Observer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MinotaurControllerTest {
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
    MinotaurControllerTest.Receiver r = new MinotaurControllerTest.Receiver();

    @BeforeEach
    void init(){
        pList = createPlayer(new Minotaur(), new Artemis());
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

        MinotaurController minotaurController = (MinotaurController) model.getCurrentGod().getGodController();
        minotaurController.prepareSpecialChooseConstructor(model, controller);

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

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.startGame();

        MinotaurController minotaurController = (MinotaurController) model.getCurrentGod().getGodController();
        minotaurController.prepareSpecialChooseConstructor(model, controller);

        assertTrue(pList.get(0).getAllConstructors().get(0).getCanMove(), "Should be able to move");

        assertNotNull(r.receivedMessage, "Should be received a message");
        assertTrue(r.receivedMessage instanceof StandardTileMessage, "This should be a standard TileToShowMessage");

        StandardTileMessage message = (StandardTileMessage) r.receivedMessage;
        List<Position> toShow = message.getTileToShow();
        assertTrue(toShow.get(0).equals(posList.get(0)), "This should be " + posList.get(0).toString() + " and instead is " + toShow.get(0).toString());
        assertTrue(toShow.get(1).equals(posList.get(1)), "This should be " + posList.get(1).toString() + " and instead is " + toShow.get(1).toString());
    }

    //Check in console if the message are: 1) Lose, 2) no more phases... and finally 3) Win
    @Test
    void cantMovecantPushSoIsLosing(){
        List<Position> posList = new ArrayList<>();
        posList.add(new Position(1,0));
        posList.add(new Position(1,1));
        posList.add(new Position(0,0));
        posList.add(new Position(0,1));

        int n = 0;
        for(Player p : pList){
            for(int i = 0; i < 2; i++){
                model.setCurrentConstructor(p.getAllConstructors().get(i));
                model.performMove(posList.get(i + n));
            }
            n = 2;
        }

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        increaseConstrLevel(new Position(2,0), 3);
        increaseConstrLevel(new Position(2,1), 3);
        increaseConstrLevel(new Position(2,2), 3);
        increaseConstrLevel(new Position(1,2), 3);
        increaseConstrLevel(new Position(0,2), 3);

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.startGame();

        MinotaurController minotaurController = (MinotaurController) model.getCurrentGod().getGodController();
        minotaurController.prepareSpecialChooseConstructor(model, controller);

        assertNotNull(r.receivedMessage, "Should be received a message");
    }

    //check console if says: "Win"
    @Test
    void minotaurWins(){
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
        model.removePlayer(pList.get(1).getIdPlayer());

        MinotaurController minotaurController = (MinotaurController) model.getCurrentGod().getGodController();
        minotaurController.prepareSpecialChooseConstructor(model, controller);
    }

    // -----------------      handleSpecialMove TESTS      -----------------
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

        PosMessage message = new PosMessage("standard", "uno", null, new Position(1,3));
        MinotaurController minotaurController = (MinotaurController) model.getCurrentGod().getGodController();
        minotaurController.handleSpecialMove(model, controller, message);

        assertTrue(r.receivedMessage instanceof MoveMessage, "This should be a move message");
        assertTrue(r.receivedMessage instanceof StandardMoveMessage, "This should be a standard move message");

        StandardMoveMessage m = (StandardMoveMessage) r.receivedMessage;
        assertEquals(1, m.getConstructorMatrix()[1][3], "(1,3) Should be occupied by a Player 1 constructor");
    }

    @Test
    void pushMove(){
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

        PosMessage message = new PosMessage("standard", "uno", null, new Position(1,0));
        MinotaurController minotaurController = (MinotaurController) model.getCurrentGod().getGodController();
        minotaurController.handleSpecialMove(model, controller, message);

        assertTrue(r.receivedMessage instanceof MoveMessage, "This should be a move message");
        assertTrue(r.receivedMessage instanceof PushMessage, "This should be a push move message");

        PushMessage m = (PushMessage) r.receivedMessage;
        assertEquals(1, m.getConstructorMatrix()[1][0], "(1,0) Should be occupied by a Player 1 constructor");
        assertEquals(2, m.getConstructorMatrix()[2][0], "(2,0) Should be occupied by a Player 2 constructor (it was pushed from Position (1,0))");
    }

    @Test
    void pushWithWin(){
        List<Position> posList = new ArrayList<>();
        posList.add(new Position(0,0));
        posList.add(new Position(0,1));
        posList.add(new Position(1,0));
        posList.add(new Position(1,1));

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        increaseConstrLevel(new Position(0,0), 2);
        increaseConstrLevel(new Position(1,0), 3);

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

        PosMessage message = new PosMessage("standard", "uno", null, new Position(1,0));
        MinotaurController minotaurController = (MinotaurController) model.getCurrentGod().getGodController();
        minotaurController.handleSpecialMove(model, controller, message);
    }

    @Test
    void handleSpecialChooseConstructorTest()   {
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
        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(1));

        model.startGame();
        PosMessage posMessage = new PosMessage("shish", pList.get(0).getIdPlayer(), null, new Position(0,0));
        MinotaurController minotaurController = (MinotaurController) model.getCurrentGod().getGodController();
        minotaurController.handleSpecialChooseConstructor(model, controller, posMessage);

        assertEquals(pList.get(0).getAllConstructors().get(0), model.getCurrentConstructor());
    }

    @Test
    void prepareSpecialMove()   {
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

        MinotaurController minotaurController = (MinotaurController) model.getCurrentGod().getGodController();
        minotaurController.prepareSpecialMove(model, controller);
        Minotaur minotaur = (Minotaur) model.getCurrentGod();

        assertTrue(minotaur.getMoveAddList(model).size() == 2);
        assertTrue(r.receivedMessage instanceof StandardTileMessage);
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