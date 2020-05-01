package it.polimi.ingsw.Controller.GodController;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Message.BuildMessages.StandardBuildMessage;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.TileToShowMessages.CanEndTileMessage;
import it.polimi.ingsw.Message.TileToShowMessages.MoreTileToCheckMessage;
import it.polimi.ingsw.Message.TileToShowMessages.StandardTileMessage;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Observer.Observer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrometheusControllerTest {
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
    PrometheusControllerTest.Receiver r = new PrometheusControllerTest.Receiver();

    @BeforeEach
    void init(){
        pList = createPlayer(new Prometheus(), new Artemis());
        model = new Model(pList);
        controller = new Controller(model);
        model.addObserver(r);
    }

    // -----------------      prepareSpecialChooseConstructor TESTS      -----------------
    @Test
    void standardSpecialChoose(){
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

        PrometheusController prometheusController = (PrometheusController) model.getCurrentGod().getGodController();
        prometheusController.prepareSpecialChooseConstructor(model, controller);

        assertNotNull(r.receivedMessage, "Should be received a message");
        assertTrue(r.receivedMessage instanceof StandardTileMessage, "This should be a standard TileToShowMessage");

        StandardTileMessage message = (StandardTileMessage) r.receivedMessage;
        List<Position> toShow = message.getTileToShow();
        assertTrue(toShow.get(0).equals(posList.get(0)), "This should be " + posList.get(0).toString() + " and instead is " + toShow.get(0).toString());
        assertTrue(toShow.get(1).equals(posList.get(1)), "This should be " + posList.get(1).toString() + " and instead is " + toShow.get(1).toString());
    }

    @Test
    void moreTileChoose(){
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

        for(int i=0; i<3; i++){
            for(int j=1; j<4; j++){
                if(!(i == 1 && j == 2)){
                    increaseConstrLevel(new Position(i,j), 1);
                }
            }
        }

        PrometheusController prometheusController = (PrometheusController) model.getCurrentGod().getGodController();
        prometheusController.prepareSpecialChooseConstructor(model, controller);

        assertNotNull(r.receivedMessage, "Should be received a message");
        assertTrue(r.receivedMessage instanceof MoreTileToCheckMessage, "This should be a more tile to check message");

        MoreTileToCheckMessage message = (MoreTileToCheckMessage)r.receivedMessage;
        List<Position> toShow = message.getTileToShow();
        List<Position> toCheck = message.getSpecialList();

        assertEquals(2, toShow.size(), "The size should be only 2 (every constructor is ok)");
        assertEquals(1, toCheck.size(), "The size should be only 1 (only the first constructor should be here)");
        assertEquals("1,2", toCheck.get(0).toString(), "Check the actual position added to toCheck list");
    }

    //check on console if it's written: 1) Lose, 2) No more etc... and finally 3) Win
    @Test
    void isLosing(){
        List<Position> posList = new ArrayList<>();
        posList.add(new Position(0,0));
        posList.add(new Position(0,1));
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
        increaseConstrLevel(new Position(0,2), 3);
        increaseConstrLevel(new Position(1,0), 3);
        increaseConstrLevel(new Position(1,1), 3);
        increaseConstrLevel(new Position(1,2), 3);
        model.startGame();

        PrometheusController prometheusController = (PrometheusController) model.getCurrentGod().getGodController();
        prometheusController.prepareSpecialChooseConstructor(model, controller);
    }

    //check on console if it is written "Win"
    @Test
    void win(){
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
        model.removePlayer(pList.get(1).getIdPlayer());
        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.startGame();

        PrometheusController prometheusController = (PrometheusController) model.getCurrentGod().getGodController();
        prometheusController.prepareSpecialChooseConstructor(model, controller);

        //test went ok if on console you read "Win"

    }

    // -----------------      prepareSpecialBuild TESTS      -----------------
    @Test
    void standardPrepareBuild(){
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

        PrometheusController prometheusController = (PrometheusController) model.getCurrentGod().getGodController();
        prometheusController.prepareSpecialBuild(model, controller);

        assertNotNull(r.receivedMessage, "Should be received a message");
        assertTrue(r.receivedMessage instanceof CanEndTileMessage, "This should be a canEnd message");

        CanEndTileMessage message = (CanEndTileMessage)r.receivedMessage;
        List<Position> toShow = message.getTileToShow();

        assertEquals(8, toShow.size(), "Every tile should be a possible build");
    }

    @Test
    void toCheck1Tile(){
        List<Position> posList = new ArrayList<>();
        posList.add(new Position(0,0));
        posList.add(new Position(4,0));
        posList.add(new Position(1,0));
        posList.add(new Position(2,1));

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        increaseConstrLevel(new Position(1,1), 1);
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

        PrometheusController prometheusController = (PrometheusController) model.getCurrentGod().getGodController();
        prometheusController.prepareSpecialBuild(model, controller);

        assertNotNull(r.receivedMessage, "Should be received a message");
        assertTrue(r.receivedMessage instanceof CanEndTileMessage, "This should be a canEnd message");

        CanEndTileMessage message = (CanEndTileMessage)r.receivedMessage;
        List<Position> toShow = message.getTileToShow();
        assertEquals(1, toShow.size(), "Only the tile at level one should be here");
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