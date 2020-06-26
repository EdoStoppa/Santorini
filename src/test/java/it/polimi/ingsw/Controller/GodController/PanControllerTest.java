package it.polimi.ingsw.Controller.GodController;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.MoveMessages.MoveMessage;
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

class PanControllerTest {
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
    PanControllerTest.Receiver r = new PanControllerTest.Receiver();

    @BeforeEach
    void init(){
        pList = createPlayer(new Pan(), new Apollo());
        model = new Model(pList);
        controller = new Controller(model);
        model.addObserver(r);
    }

    // -----------------      handleSpecialMove TESTS      -----------------
    @Test
    void standardHandleSpecialMove() {
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
        PanController panController = (PanController) model.getCurrentGod().getGodController();
        panController.handleSpecialMove(model, controller, message);

        assertTrue(r.receivedMessage instanceof MoveMessage, "This should be a move message");
        assertTrue(r.receivedMessage instanceof StandardMoveMessage, "This should be a standard move message");

        StandardMoveMessage m = (StandardMoveMessage) r.receivedMessage;
        assertEquals(1, m.getConstructorMatrix()[1][3], "(1,3) Should be occupied by a Player 1 constructor");
    }

    //For now it should be verified by looking at the console if this test went ok
    @Test
    void wonDown2Level(){
        List<Position> posList = new ArrayList<>();
        posList.add(new Position(1,2));
        posList.add(new Position(2,0));
        posList.add(new Position(3,2));
        posList.add(new Position(3,4));

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        increaseConstrLevel(new Position(1,2), 2);


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
        PanController panController = (PanController) model.getCurrentGod().getGodController();
        panController.handleSpecialMove(model, controller, message);
        //Should output "Win" on console
    }

    @Test
    void wonDown3Level(){
        List<Position> posList = new ArrayList<>();
        posList.add(new Position(1,2));
        posList.add(new Position(2,0));
        posList.add(new Position(3,2));
        posList.add(new Position(3,4));

        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        increaseConstrLevel(new Position(1,2), 3);


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
        PanController panController = (PanController) model.getCurrentGod().getGodController();
        panController.handleSpecialMove(model, controller, message);
        //Should output "Win" on console
    }

    @Test
    void prepareSpecialMoveTest()   {
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

        PanController panController = (PanController) model.getCurrentGod().getGodController();
        panController.prepareSpecialMove(model, controller);

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
        for(int i=0; i<howManyLevel; i++){
            model.performBuild(pos);
        }
    }
}