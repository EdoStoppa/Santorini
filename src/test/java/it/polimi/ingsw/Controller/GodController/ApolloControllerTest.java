package it.polimi.ingsw.Controller.GodController;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.TileToShowMessages.StandardTileMessage;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Observer.Observer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
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
    // TO DO: TESTS ON isLastStanding == true OR isLosing == true FOR PREPARE SPECIAL CHOOSE CONSTRUCTOR
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

    // -----------------            prepareSpecialMove TESTS            -----------------
    // TO DO: TESTS ON isLastStanding == true OR isLosing == true FOR PREPARE SPECIAL CHOOSE CONSTRUCTOR
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


    // Helper methods
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