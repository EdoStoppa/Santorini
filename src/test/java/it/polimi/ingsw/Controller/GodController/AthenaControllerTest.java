package it.polimi.ingsw.Controller.GodController;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.PosMessage;
import it.polimi.ingsw.Message.TileToShowMessages.StandardTileMessage;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Observer.Observer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AthenaControllerTest {
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
        pList = createPlayer(new Athena(), new Apollo());
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

    @Test
    void handleSpecialChooseConstructorTest()   {
        PosMessage posMessage = new PosMessage("boh", null, null, new Position(1,1));
        AthenaController athenaController = new AthenaController();
        athenaController.handleSpecialChooseConstructor(model, controller, posMessage);
        assertEquals(1, model.getCurrentConstructor().getPos().getRow());
        assertEquals(1, model.getCurrentConstructor().getPos().getCol());
    }

    @RepeatedTest(3)
    void particularAthenaConditionsTest(RepetitionInfo repetitionInfo)  {
        List<Position> constructionPos = new ArrayList<>();

        if(repetitionInfo.getCurrentRepetition() != 3)  {
            if(repetitionInfo.getCurrentRepetition() == 1)  {
                constructionPos.add(new Position(1,2));
                constructionPos.add(new Position(4, 3));
            }
            if(repetitionInfo.getCurrentRepetition() == 2)  {
                constructionPos.add(new Position(1,2));
                constructionPos.add(new Position(4, 3));
                constructionPos.add(new Position(3, 3));
            }
            for(int i = 0; i < constructionPos.size(); i++) {
                model.performBuild(constructionPos.get(i));
            }


            model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
            model.startGame();
            model.nextPhase();

            AthenaController athenaController = (AthenaController) model.getCurrentGod().getGodController();
            PosMessage message = new PosMessage("special", model.getCurrentPlayerId(), null, constructionPos.get(0));
            athenaController.handleSpecialMove(model, controller, message);

            assertFalse(model.getCanGoUp(), "The next player can't go up");

            model.nextPhase();//next turn;
            model.nextPhase();

            ApolloController apolloController = (ApolloController) model.getCurrentGod().getGodController();
            model.setCurrentConstructor(pList.get(1).getAllConstructors().get(1));

            apolloController.prepareSpecialMove(model, controller);
            StandardTileMessage message1 = (StandardTileMessage) r.receivedMessage;
            List<Position> toShow = message1.getTileToShow();
            assertFalse(model.getCanGoUp(), "The player can't go up");

            if(repetitionInfo.getCurrentRepetition() == 1)  {
                assertTrue(toShow.size() == 2);
            }
            else    {
                assertTrue(toShow.size() == 1);
            }

            model.nextPhase();
            model.nextPhase();
            model.nextPhase();
            athenaController.prepareSpecialChooseConstructor(model, controller);

            assertTrue(model.getCanGoUp(), "The player can go up");
        }
        else    {
            model.setCurrentConstructor(pList.get(0).getAllConstructors().get(1));
            model.performMove(new Position(2, 4));
            constructionPos.add(new Position(1,2));
            constructionPos.add(new Position(4, 3));
            constructionPos.add(new Position(3, 3));
            constructionPos.add(new Position(3, 4));

            for(int i = 0; i < constructionPos.size(); i++) {
                model.performBuild(constructionPos.get(i));
            }

            model.setCurrentConstructor(pList.get(0).getAllConstructors().get(0));
            model.startGame();
            model.nextPhase();

            AthenaController athenaController = (AthenaController) model.getCurrentGod().getGodController();
            PosMessage message = new PosMessage("special", model.getCurrentPlayerId(), null, constructionPos.get(0));
            athenaController.handleSpecialMove(model, controller, message);

            assertFalse(model.getCanGoUp(), "The next player can't go up");

            model.nextPhase();//next turn;
            model.nextPhase();
            model.changeActiveConstructors();
            ApolloController apolloController = (ApolloController) model.getCurrentGod().getGodController();
            apolloController.prepareSpecialChooseConstructor(model, controller);

            StandardTileMessage message1 = (StandardTileMessage) r.receivedMessage;
            List<Position> toShow = message1.getTileToShow();

            assertTrue(toShow.size() == 1, "There should be just one constructor available");
            assertEquals(2, toShow.get(0).getRow(), "The row should be the same");
            assertEquals(2, toShow.get(0).getCol(), "The col should be the same");
        }
    }



    //          -----------     HELPER METHODS      ----------------
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