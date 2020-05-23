package it.polimi.ingsw.Controller.GodController;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Message.BuildMessages.ForcedDomeMessage;
import it.polimi.ingsw.Message.BuildMessages.StandardBuildMessage;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.PosMessage;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Observer.Observer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AtlasControllerTest {
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
    Receiver r = new Receiver();

    @BeforeEach
    void init(){
        pList = createPlayer(new Atlas(), new Apollo());
        model = new Model(pList);
        controller = new Controller(model);
        model.addObserver(r);

        List<Position> posList = new ArrayList<>();
        posList.add(new Position(1, 1));
        posList.add(new Position(3, 4));
        posList.add(new Position(2, 2));
        posList.add(new Position(4,4));
        int n = 0;
        for(Player p : pList)   {
            for(int i = 0; i < 2; i++)  {
                model.setCurrentConstructor(p.getAllConstructors().get(i));
                model.performMove(posList.get(i + n));
            }
            n = 2;
        }
    }

    @RepeatedTest(2)
    void handleSpecialBuildTest(RepetitionInfo repetitionInfo)  {
        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
        model.startGame();
        Position pos = new Position(1, 2);
        if(repetitionInfo.getCurrentRepetition() == 1)  {
            PosMessage message = new PosMessage("dome", model.getCurrentPlayerId(), null, pos);

            AtlasController atlasController = (AtlasController) model.getCurrentGod().getGodController();
            atlasController.handleSpecialBuild(model, controller,message);

            assertTrue(r.receivedMessage instanceof ForcedDomeMessage, "The message should be a ForcedDomeMessage");
            assertTrue(model.getDome(pos));
        }
        else    {
            PosMessage message = new PosMessage("boh", model.getCurrentPlayerId(), null, pos);

            AtlasController atlasController = (AtlasController) model.getCurrentGod().getGodController();
            atlasController.handleSpecialBuild(model, controller,message);

            assertTrue(r.receivedMessage instanceof StandardBuildMessage, "The message should be a StandardBuildMessage");
            assertFalse(model.getDome(pos));
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