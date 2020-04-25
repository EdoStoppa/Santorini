package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.PosMessage;
import it.polimi.ingsw.Message.TileToShowMessages.TileToShowMessage;
import it.polimi.ingsw.Model.*;
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

    Controller controller;
    Receiver r = new Receiver();

    @BeforeEach
    void init() {
        Random random = new Random();
        List<Player> pList = createPlayer(new Artemis(), new Apollo());
        Model model = new Model(pList);
        controller = new Controller(model);
        model.addObserver(r);
        for(Player p : pList)   {
            Position pos1 = new Position(random.nextInt(4), random.nextInt(4));
            Position pos2 = new Position(random.nextInt(4), random.nextInt(4));
            while(pos1.equals(pos2))    {
                pos2 = new Position(random.nextInt(4), random.nextInt(4));
            }
            Constructor c = p.getAllConstructors().get(0);
            model.setCurrentConstructor(c);
            model.performMove(pos1);

            c = p.getAllConstructors().get(1);
            model.setCurrentConstructor(c);
            model.performMove(pos2);
        }
        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.startGame();
    }

    @RepeatedTest(3)
    void preparePhaseBasicTest(RepetitionInfo repetitionInfo)    {//Artemis as God
        List<Position> list = new ArrayList<>();
        Player player = null;

        if(repetitionInfo.getCurrentRepetition() == 1)    {//CHOOSE_CONSTRUCTOR
            for(Player p : controller.getModel().getListPlayer())   {
                if(p.getIdPlayer().equals(controller.getModel().getCurrentPlayerId()))   {
                    player = p;
                    break;
                }
            }
            for(Constructor c : player.getAllConstructors())    {
                System.out.println(c.getPos().toString());
                list.add(c.getPos());
            }
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
        if(repetitionInfo.getCurrentRepetition() == 2)  {//MOVE
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
        if(repetitionInfo.getCurrentRepetition() == 3)  {//BUILD
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

    @RepeatedTest(3)
    void handleActionTest(RepetitionInfo repetitionInfo)    {
        Random random = new Random();
        Player player = null;
        View view = new View();
        if(repetitionInfo.getCurrentRepetition() == 1)  {
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
        if(repetitionInfo.getCurrentRepetition() == 2)  {
            controller.getModel().nextPhase();
            for(Player p : controller.getModel().getListPlayer())   {
                if(p.getIdPlayer().equals(controller.getModel().getCurrentPlayerId()))  {
                    player = p;
                    break;
                }
            }
            Position pos = new Position(random.nextInt(4), random.nextInt(4));
            Tile t = new Tile(pos);
            while(t.getOccupied())  {
                pos = new Position(random.nextInt(4), random.nextInt(4));
                t = new Tile(pos);
            }
            PosMessage message = new PosMessage("Boh", player.getIdPlayer(), view, pos);
            controller.handleMove(message);
            assertEquals(pos.getRow(), controller.getModel().getCurrentConstructor().getPos().getRow(), "The row should be the same");
            assertEquals(pos.getCol(), controller.getModel().getCurrentConstructor().getPos().getCol(), "The col should be the same");
        }
        if(repetitionInfo.getCurrentRepetition() == 3)  {
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
            if(random.nextBoolean())    {
                row = currConsPos.getRow() + random.nextInt(1);
                if(random.nextBoolean())    {
                    col = currConsPos.getCol() + random.nextInt(1);
                }
                else    {
                    col = currConsPos.getCol() - random.nextInt(1);
                }
            }
            else    {
                row = currConsPos.getRow() - random.nextInt(1);
                if(random.nextBoolean())    {
                    col = currConsPos.getCol() + random.nextInt(1);
                }
                else    {
                    col = currConsPos.getCol() - random.nextInt(1);
                }
            }
            Position pos = new Position(row, col);
            Tile t = new Tile(pos);
            while(t.getOccupied())  {
                if(random.nextBoolean())    {
                    row = currConsPos.getRow() + random.nextInt(1);
                    if(random.nextBoolean())    {
                        col = currConsPos.getCol() + random.nextInt(1);
                    }
                    else    {
                        col = currConsPos.getCol() - random.nextInt(1);
                    }
                }
                else    {
                    row = currConsPos.getRow() - random.nextInt(1);
                    if(random.nextBoolean())    {
                        col = currConsPos.getCol() + random.nextInt(1);
                    }
                    else    {
                        col = currConsPos.getCol() - random.nextInt(1);
                    }
                }
                pos = new Position(row, col);
                t = new Tile(pos);
            }
            PosMessage message = new PosMessage("Boh", player.getIdPlayer(), view, pos);
            controller.handleBuild(message);
            assertEquals(pos.getRow(), controller.getModel().getCurrentConstructor().getPos().getRow(), "The row should be the same");
            assertEquals(pos.getCol(), controller.getModel().getCurrentConstructor().getPos().getCol(), "The col should be the same");
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