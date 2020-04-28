package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.MoveMessages.StandardMoveMessage;
import it.polimi.ingsw.Message.PosMessage;
import it.polimi.ingsw.Message.TileToShowMessages.CanEndTileMessage;
import it.polimi.ingsw.Message.TileToShowMessages.StandardTileMessage;
import it.polimi.ingsw.Message.TileToShowMessages.TileToShowMessage;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.View.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private class Receiver implements Observer<GameMessage> {
        GameMessage receivedMessage;

        @Override
        public void update(GameMessage message) {
            this.receivedMessage = message;
        }
    }

    private class Sender extends Observable<PosMessage> {
        public void forceNotify(PosMessage message) {
            notify(message);
        }
    }

    Controller controller;
    Receiver r;
    Sender s;

    @BeforeEach
    void init() {
        Random random = new Random();
        List<Player> pList = createPlayer(new Artemis(), new Atlas());
        Model model = new Model(pList);
        controller = new Controller(model);
        Tile t;
        r = new Receiver();
        s = new Sender();
        model.addObserver(r);
        s.addObserver(controller);
        for(Player p : pList)   {
            Position pos1 = new Position(random.nextInt(5), random.nextInt(5));
            t = controller.getModel().getTile(pos1);
            while(t.getOccupied())  {
                pos1 = new Position(random.nextInt(5), random.nextInt(5));
                t = controller.getModel().getTile(pos1);
            }
            Position pos2 = new Position(random.nextInt(5), random.nextInt(5));
            t = controller.getModel().getTile(pos2);
            while(t.getOccupied())    {
                pos2 = new Position(random.nextInt(5), random.nextInt(5));
                t = controller.getModel().getTile(pos2);
            }
            Constructor c = p.getAllConstructors().get(0);
            model.setCurrentConstructor(c);
            model.performMove(pos1);

            c = p.getAllConstructors().get(1);
            model.setCurrentConstructor(c);
            model.performMove(pos2);
        }
        for (int i = 0; i < 5; i++) { //creates a random board without Atlas as God
            for (int j = 0; j < 5; j++) {
                Position pos = new Position(i, j);
                int miavar = random.nextInt(5);
                for(int k = 0; k < miavar; k++) {
                    controller.getModel().performBuild(pos);
                }
            }
        }
        model.changeActiveConstructors();
        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.startGame();
    }

    @RepeatedTest(1000)
    void preparePhaseBasicTest(RepetitionInfo repetitionInfo)    {//Artemis as God
        List<Position> list = new ArrayList<>();
        Player player = null;
        Random random = new Random();
        int miavar = random.nextInt(3);

        if(miavar == 0)    {//CHOOSE_CONSTRUCTOR
            for(Player p : controller.getModel().getListPlayer())   {
                if(p.getIdPlayer().equals(controller.getModel().getCurrentPlayerId()))   {
                    player = p;
                    break;
                }
            }
            for(Constructor c : player.getAllConstructors())    {
                System.out.println(c.getCanMove());
                if(c.getCanMove())  {
                    System.out.println(c.getPos().toString());
                    list.add(c.getPos());
                }
            }
            System.out.println(controller.getModel().getCurrentPhase());
            System.out.println(controller.getModel().isLastStanding());
            System.out.println(controller.getModel().isLosing(player));
            controller.preparePhase();
            if(r.receivedMessage instanceof TileToShowMessage)  {
                List<Position> receivedList = ((TileToShowMessage) r.receivedMessage).getTileToShow();
                assertEquals(list.size(), receivedList.size(), "The size should be the same");
                for(int i = 0; i < receivedList.size(); i++)    {
                    System.out.println(receivedList.get(i).toString());
                    assertEquals(list.get(i).getRow(), receivedList.get(i).getRow(), "The row should be the same");
                    assertEquals(list.get(i).getCol(), receivedList.get(i).getCol(), "The col should be the same");
                }
            }
        }
        if(miavar == 1)  {//MOVE
            controller.getModel().nextPhase();
            for(Player p : controller.getModel().getListPlayer()) {
                if(p.getIdPlayer().equals(controller.getModel().getCurrentPlayerId()))  {
                    player = p;
                    break;
                }
            }
            controller.getModel().createPossibleMovePos(null, null);
            list = ((TileToShowMessage) r.receivedMessage).getTileToShow();
            System.out.println(list.size());
            controller.preparePhase();
            if(r.receivedMessage instanceof TileToShowMessage)  {
                List<Position> receivedList = ((TileToShowMessage) r.receivedMessage).getTileToShow();
                assertEquals(list.size(), receivedList.size(), "The size should be the same");
                for(int i = 0; i < receivedList.size(); i++)    {
                    assertEquals(list.get(i).getRow(), receivedList.get(i).getRow(), "The row should be the same");
                    assertEquals(list.get(i).getCol(), receivedList.get(i).getCol(), "The col should be the same");
                }
            }
        }
        if(miavar == 2)  {//BUILD
            controller.getModel().nextPhase();
            controller.getModel().nextPhase();
            controller.getModel().nextPhase();
            for(Player p : controller.getModel().getListPlayer()) {
                if(p.getIdPlayer().equals(controller.getModel().getCurrentPlayerId()))  {
                    player = p;
                    break;
                }
            }
            controller.getModel().createPossibleBuildPos(null, null);
            list = ((TileToShowMessage) r.receivedMessage).getTileToShow();
            System.out.println(list.size());
            controller.preparePhase();
            if(r.receivedMessage instanceof TileToShowMessage)  {
                List<Position> receivedList = ((TileToShowMessage) r.receivedMessage).getTileToShow();
                assertEquals(list.size(), receivedList.size(), "The size should be the same");
                for(int i = 0; i < receivedList.size(); i++)    {
                    assertEquals(list.get(i).getRow(), receivedList.get(i).getRow(), "The row should be the same");
                    assertEquals(list.get(i).getCol(), receivedList.get(i).getCol(), "The col should be the same");
                }
            }
        }
    }

    @RepeatedTest(1000)
    void handleActionTest(RepetitionInfo repetitionInfo)    {
        Random random = new Random();
        int miavar = random.nextInt(3);
        Player player = null;
        View view = new View();
        if(miavar == 0)  {
            for(Player p : controller.getModel().getListPlayer())   {
                if(p.getIdPlayer().equals(controller.getModel().getCurrentPlayerId()))  {
                    player = p;
                    break;
                }
            }
            PosMessage message = new PosMessage("Boh", player.getIdPlayer(), view, player.getAllConstructors().get(1).getPos());
            controller.handleChooseConstructor(message);
            assertEquals(player.getAllConstructors().get(1).getPos().getRow(), controller.getModel().getCurrentConstructor().getPos().getRow(),"The row should be the same");
            assertEquals(player.getAllConstructors().get(1).getPos().getCol(), controller.getModel().getCurrentConstructor().getPos().getCol(),"The col should be the same");
        }
        if(miavar == 1)  {
            controller.getModel().nextPhase();
            for(Player p : controller.getModel().getListPlayer())   {
                if(p.getIdPlayer().equals(controller.getModel().getCurrentPlayerId()))  {
                    player = p;
                    break;
                }
            }
            Position pos = new Position(random.nextInt(5), random.nextInt(5));
            Tile t = new Tile(pos);
            while(t.getOccupied())  {
                pos = new Position(random.nextInt(5), random.nextInt(5));
                t = new Tile(pos);
            }
            PosMessage message = new PosMessage("Boh", player.getIdPlayer(), view, pos);
            controller.handleMove(message);
            assertEquals(pos.getRow(), controller.getModel().getCurrentConstructor().getPos().getRow(), "The row should be the same");
            assertEquals(pos.getCol(), controller.getModel().getCurrentConstructor().getPos().getCol(), "The col should be the same");
        }
        if(miavar == 2)  {
            int row;
            int col;
            Position currConsPos = controller.getModel().getCurrentConstructor().getPos();
            controller.getModel().nextPhase();
            controller.getModel().nextPhase();
            controller.getModel().nextPhase();
            for(Player p : controller.getModel().getListPlayer())   {
                if(p.getIdPlayer().equals(controller.getModel().getCurrentPlayerId()))  {
                    player = p;
                    break;
                }
            }
            if(currConsPos.getRow() < 3)    {
                row = currConsPos.getRow() + random.nextInt(2);
                if(currConsPos.getCol() < 3)    {
                    col = currConsPos.getCol() + random.nextInt(2);
                }
                else    {
                    col = currConsPos.getCol() - random.nextInt(2);
                }
            }
            else    {
                row = currConsPos.getRow() - random.nextInt(2);
                if(currConsPos.getCol() < 3)    {
                    col = currConsPos.getCol() + random.nextInt(2);
                }
                else    {
                    col = currConsPos.getCol() - random.nextInt(2);
                }
            }
            Position pos = new Position(row, col);
            Tile t = controller.getModel().getTile(pos);
            while(t.getOccupied())  {
                if(currConsPos.getRow() < 3)    {
                    row = currConsPos.getRow() + random.nextInt(2);
                    if(currConsPos.getCol() < 3)    {
                        col = currConsPos.getCol() + random.nextInt(2);
                    }
                    else    {
                        col = currConsPos.getCol() - random.nextInt(2);
                    }
                }
                else    {
                    row = currConsPos.getRow() - random.nextInt(2);
                    if(currConsPos.getCol() < 3)    {
                        col = currConsPos.getCol() + random.nextInt(2);
                    }
                    else    {
                        col = currConsPos.getCol() - random.nextInt(2);
                    }
                }
                pos = new Position(row, col);
                t = controller.getModel().getTile(pos);
            }
            PosMessage message = new PosMessage("Boh", player.getIdPlayer(), view, pos);
            if(!(t.getConstructionLevel() == 3 && t.getDome())) {
                controller.handleBuild(message);
                assertEquals(pos.getRow(), controller.getModel().getCurrentConstructor().getLastBuildPos().getRow(), "The row should be the same");
                assertEquals(pos.getCol(), controller.getModel().getCurrentConstructor().getLastBuildPos().getCol(), "The col should be the same");
            }
        }
    }

    @RepeatedTest(1000)
    void updateTest()  {
        Position pos;
        Random random = new Random();
        int miavar = random.nextInt(3);
        Tile t;
        View view = new View();
        PossiblePhases possiblePhases = null;
        PosMessage message;
        List<Position> list;
        Player player = null;
        for(Player p : controller.getModel().getListPlayer())    {
            if(controller.getModel().getCurrentConstructor().getPlayerNumber() == p.getPlayerNumber())  {
                player = p;
                break;
            }
        }
        if(miavar == 0)  {//CHOOSE_CONSTRUCTOR
            pos = player.getAllConstructors().get(1).getPos();
            message = new PosMessage("standard", player.getIdPlayer(), view, pos);
            s.forceNotify(message);
            possiblePhases = PossiblePhases.MOVE;
            assertEquals(possiblePhases, controller.getModel().getCurrentPhase(), "The phase should be SPECIAL_CHOOSE_CONSTRUCTOR");
            assertEquals(pos.getRow(), controller.getModel().getCurrentConstructor().getPos().getRow(), "The row should be the same");
            assertEquals(pos.getCol(), controller.getModel().getCurrentConstructor().getPos().getCol(), "The col shoul be the same");
        }
        if(miavar == 1)  {//MOVE
            controller.getModel().nextPhase();
            controller.getModel().createPossibleMovePos(null, null);
            list = ((StandardTileMessage) r.receivedMessage).getTileToShow();
            if(list.size() > 0) {
                message = new PosMessage("standard", player.getIdPlayer(), view, list.get(0));
                s.forceNotify(message);
                assertTrue(r.receivedMessage instanceof TileToShowMessage, "The message should be CanEndTile");
                controller.getModel().createPossibleMovePos(null, null);
                list = ((TileToShowMessage) r.receivedMessage).getTileToShow();
                if(list.size() == 1 && list.get(0).equals(controller.getModel().getCurrentConstructor().getPrevPos()))   {
                    possiblePhases = possiblePhases.BUILD;
                }
                else    {
                    possiblePhases = possiblePhases.SPECIAL_MOVE;
                }
                assertEquals(possiblePhases, controller.getModel().getCurrentPhase(), "The phase should be SPECIAL_MOVE");
            }
        }
        if(miavar == 3) {//BUILD
            controller.getModel().nextPhase();
            controller.getModel().nextPhase();
            controller.getModel().nextPhase();
            controller.getModel().createPossibleBuildPos(null, null);
            list = ((StandardTileMessage) r.receivedMessage).getTileToShow();
            if (list.size() > 0) {
                message = new PosMessage("standard", player.getIdPlayer(), view, list.get(0));
                s.forceNotify(message);
                assertTrue(r.receivedMessage instanceof TileToShowMessage, "The message should be a TileToShow");
                possiblePhases = PossiblePhases.CHOOSE_CONSTRUCTOR;
                assertEquals(possiblePhases, controller.getModel().getCurrentPhase(), "This should be the first phase of Atlas");
                list = ((TileToShowMessage) r.receivedMessage).getTileToShow();
                for(Player p : controller.getModel().getListPlayer())   {
                    if(!p.equals(player))   {
                        player = p;
                        break;
                    }
                }
                int i = 0;
                for(Constructor c : player.getAllConstructors())    {
                    System.out.println(c.getPos().toString());
                    System.out.println(list.get(i).toString());
                    assertEquals(c.getPos().getRow(), list.get(i).getRow(), "The row should be the same");
                    assertEquals(c.getPos().getCol(), list.get(i).getCol(), "The col should be the same");
                    i++;
                }
            }
        }
    }

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
}