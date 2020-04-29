package it.polimi.ingsw.Controller.GodController;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.TileToShowMessages.CanEndTileMessage;
import it.polimi.ingsw.Message.TileToShowMessages.StandardTileMessage;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Observer.Observer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArtemisControllerTest {
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
    ArtemisControllerTest.Receiver r = new ArtemisControllerTest.Receiver();

    @BeforeEach
    void init(){
        pList = createPlayer(new Artemis(), new Apollo());
        model = new Model(pList);
        controller = new Controller(model);
        model.addObserver(r);
    }

    // ----------------           handleSpecialMove           ----------------
    @Test
    void standardPrepareSpecialMove(){
        List<Position> posList = new ArrayList<>();
        posList.add(new Position(1,2));
        posList.add(new Position(2,0));
        posList.add(new Position(3,2));
        posList.add(new Position(3,4));

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.performMove(new Position(1,1));

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

        ArtemisController artemisController = (ArtemisController) model.getCurrentGod().getGodController();
        artemisController.prepareSpecialMove(model, controller);

        assertTrue(r.receivedMessage instanceof CanEndTileMessage, "This should be a normal tile message");

        CanEndTileMessage message = (CanEndTileMessage)r.receivedMessage;
        List<Position> toShow = message.getTileToShow();
        assertEquals(7, toShow.size(), "This should contain all 7 tiles around position (1,2), except for position (1,1)");

        int k = 0;
        for(int i=0; i<3; i++){
            for(int j=1; j<4; j++){
                if(!(i == 1 && j == 2) && !(i == 1 && j == 1)){
                    assertTrue(toShow.get(k).equals(new Position(i,j)), "This Position should be (" + i + "," + j + ") and instead is " + toShow.get(k).toString());
                    k++;
                }
            }
        }
    }

    @Test
    void jumpToNextPhase(){
        List<Position> posList = new ArrayList<>();
        posList.add(new Position(0,1));
        posList.add(new Position(4,4));
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
        model.nextPhase();
        model.nextPhase();
        model.performMove(new Position(0,0));

        ArtemisController artemisController = (ArtemisController) model.getCurrentGod().getGodController();
        artemisController.prepareSpecialMove(model, controller);

        assertTrue(r.receivedMessage instanceof StandardTileMessage, "This should be a CanEnd tile message");

        StandardTileMessage message = (StandardTileMessage)r.receivedMessage;
        List<Position> toShow = message.getTileToShow();

        assertEquals(PossiblePhases.BUILD, message.getPhase(), "Should be a Build phase");
        assertEquals(1, toShow.size(), "Should only be one tile");
        assertEquals("0,1", toShow.get(0).toString(), "Should be at position 0,1, but is at " + toShow.get(0).toString());
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