package it.polimi.ingsw.Controller.GodController;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.PosMessage;
import it.polimi.ingsw.Message.TileToShowMessages.CanEndTileMessage;
import it.polimi.ingsw.Message.TileToShowMessages.StandardTileMessage;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.View.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DemeterControllerTest {

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
    View view = new View();

    @BeforeEach
    void init(){
        pList = createPlayer(new Demeter(), new Apollo());
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
        model.setCurrentConstructor(pList.get(0).getAllConstructors().get(1));
        model.startGame();
    }

    @RepeatedTest(2)
    void demeterTest(RepetitionInfo repetitionInfo) {
        List<Position> constructionPos = new ArrayList<>();
        List<Position> toShow;
        CanEndTileMessage message;
        PosMessage posMessage;
        Position buildPos = new Position(2, 4);
        Position specBuildPos;
        int i;
        if(repetitionInfo.getCurrentRepetition() == 1)  {
            constructionPos.add(new Position(4, 3));
            constructionPos.add(new Position(3, 3));
            constructionPos.add(new Position(2, 3));
            for(Position pos : constructionPos) {
                i = 0;
                while(i < 4)    {
                    model.performBuild(pos);
                    i++;
                }
            }
        }

        DemeterController demeterController = (DemeterController) model.getCurrentGod().getGodController();

        model.nextPhase();
        model.nextPhase();
        model.performBuild(buildPos);
        model.nextPhase();

        demeterController.prepareSpecialBuild(model, controller);
        assertTrue(r.receivedMessage instanceof CanEndTileMessage, "The message should be a CanEndTileMessage");

        message = (CanEndTileMessage) r.receivedMessage;
        toShow = message.getTileToShow();

        if(repetitionInfo.getCurrentRepetition() == 1)  {
            assertTrue(toShow.size() == 0, "The size should be 0");
        }
        else    {
            assertTrue(toShow.size() == 3, "The size should be 3");
        }

        if(toShow.size() > 0)   {
            specBuildPos = toShow.get(0);
        }
        else    {
            specBuildPos = null;
        }

        posMessage = new PosMessage("Boh", model.getCurrentPlayerId(), view, specBuildPos);
        demeterController.handleSpecialBuild(model, controller, posMessage);

        if(repetitionInfo.getCurrentRepetition() == 1)  {
            assertNull(specBuildPos, "The position should be null");
        }
        else    {
            assertNotNull(specBuildPos, "The position should not be null");
            assertEquals(1, model.getConstructionLevel(specBuildPos), "The level should be 1");
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