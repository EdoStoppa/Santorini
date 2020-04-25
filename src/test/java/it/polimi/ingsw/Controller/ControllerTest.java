package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.PosMessage;
import it.polimi.ingsw.Message.TileToShowMessage;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Observer.Observer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

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
                for(int i = 0; i < receivedList.size(); i++)    {
                    System.out.println(receivedList.get(i).toString());
                    assertEquals(list.get(i).getRow(), receivedList.get(i).getRow(), "The row should be the same");
                    assertEquals(list.get(i).getCol(), receivedList.get(i).getCol(), "The col should be the same");
                }
            }
        }
        if(repetitionInfo.getCurrentRepetition() == 2)  {//MOVE
            for(Player p : controller.getModel().getListPlayer()) {
                controller.getModel().nextPhase();
                controller.getModel().nextPhase();
                if(player.getIdPlayer().equals(controller.getModel().getCurrentPlayerId()))  {
                    player = p;
                    break;
                }
            }


        }
    }

    private List<Player> createPlayer(God p1God, God p2God){
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