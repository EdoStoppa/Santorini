package it.polimi.ingsw.Controller.GodController;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.PosMessage;
import it.polimi.ingsw.Message.TileToShowMessages.CanEndTileMessage;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Observer.Observer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HephaestusControllerTest {
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
        pList = createPlayer(new Hephaestus(), new Apollo());
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

    @RepeatedTest(3)
    void hephaestusControllerTest(RepetitionInfo repetitionInfo)   {
        List<Position> constructionPos = new ArrayList<>();
        constructionPos.add(new Position(2, 3));
        constructionPos.add(new Position(3, 3));
        constructionPos.add(new Position(4, 3));
        List<Position> toShow;
        CanEndTileMessage message;
        int i;
        PossiblePhases possiblePhases = PossiblePhases.SPECIAL_CHOOSE_CONSTRUCTOR;
        for(Position pos : constructionPos) {
            i = 0;
            while(i < 4)    {
                model.performBuild(pos);
                i++;
            }
        }
        Position specBuildPos = new Position(2, 4);
        if(repetitionInfo.getCurrentRepetition() == 2)  {
            for(int j = 0; j < 2; j++)   {
                model.performBuild(specBuildPos);
            }
        }
        if(repetitionInfo.getCurrentRepetition() == 3)  {
            for(int j = 0; j < 3; j++)   {
                model.performBuild(specBuildPos);
            }
        }

        HephaestusController hephaestusController = (HephaestusController) model.getCurrentGod().getGodController();

        model.nextPhase();
        model.nextPhase();
        model.performBuild(specBuildPos);
        model.nextPhase();

        hephaestusController.prepareSpecialBuild(model, controller);

        if(repetitionInfo.getCurrentRepetition() == 1)  {
            assertTrue(r.receivedMessage instanceof CanEndTileMessage, "The message should be a CanEndTile");

            message = (CanEndTileMessage) r.receivedMessage;
            toShow = message.getTileToShow();

            assertTrue(toShow.size() == 1, "There should be 1 position available");

            PosMessage posMessage = new PosMessage("boh", model.getCurrentPlayerId(), null, specBuildPos);
            hephaestusController.handleSpecialBuild(model, controller, posMessage);

            assertEquals(2, model.getConstructionLevel(specBuildPos));
        }
        else    {
            assertTrue(model.getCurrentPhase() == possiblePhases, "The phase should be the SPECIAL_CHOOSE_CONSTRUCTOR");
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