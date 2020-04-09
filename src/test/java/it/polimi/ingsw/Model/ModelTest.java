package it.polimi.ingsw.Model;

import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Model.God.Apollo;
import it.polimi.ingsw.Model.God.Athena;
import it.polimi.ingsw.Observer.Observer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    private class Receiver implements Observer<GameMessage>  {
        private GameMessage receivedMessage;

        @Override
        public void update(GameMessage message) {
            this.receivedMessage = message;
        }
    }
    Model model;
    List<Player> list;
    Position posCurrentConstructor;
    Receiver receiver;


    @BeforeEach
    void init() {//It creates a random board, a GameState and  before every Test
        Player p1, p2;

        p1 = new Player("First", "01/01/2000", 1);
        p1.setGod(new Athena());
        p2 = new Player("Second", "02/01/2000", 2);
        p2.setGod(new Apollo());
        list = new ArrayList<Player>();
        list.add(p1);
        list.add(p2);

        model = new Model(list);
        receiver = new Receiver();
        model.addObserver(receiver);

        int numberConstructorsP1 = 0, numberConstructorsP2 = 0;
        Random random = new Random();
        Board board = model.getBoard();

        while(numberConstructorsP1 < 2 || numberConstructorsP2 < 2) {//It creates 2 constructors for each player and it places them in random positions
            int x = random.nextInt(4);
            int y = random.nextInt(4);
            Position pos = new Position(x, y);
            if(model.getBoard().getTile(pos).getActualConstuctor() == null)    {
                if(numberConstructorsP1 < 2)    {
                    Constructor c1 = new Constructor(1);
                    model.getBoard().getTile(pos).setActualConstuctor(c1);
                    numberConstructorsP1++;
                }
                else if(numberConstructorsP2 < 2)   {
                    Constructor c2 = new Constructor(2);
                    if(numberConstructorsP2 == 1)   {
                        Position pos1 = new Position(3, 3);
                        model.getBoard().getTile(pos1).setActualConstuctor(c2);
                        posCurrentConstructor = pos1;
                    }
                    else    {
                        model.getBoard().getTile(pos).setActualConstuctor(c2);
                    }
                    numberConstructorsP2++;
                }
            }
        }
        for (int i = 0; i < 5; i++) { //creates a random board without Atlas as God
            for (int j = 0; j < 5; j++) {
                Position pos = new Position(i, j);
                int miavar = random.nextInt(4);
                for(int k = 0; k < miavar; k++) {
                    model.getBoard().getTile(pos).increaseConstructionLevel();
                }
            }
        }
    }

    @Test
    void isOccupiedTest()   {
        boolean t = false;
        Random random = new Random();
        Position pos = new Position(random.nextInt(4), random.nextInt(4));

        for(int i = 0; i < 5; i++)  {
            for(int j = 0; j < 5; j++)  {
                Position p1 = new Position(i, j);
                if(model.getBoard().getTile(p1).getOccupied())  {
                    if(p1.equals(pos))  {
                        assertEquals(true, model.isOccupied(pos), "The Tile should be occupied");
                        t = true;
                        break;
                    }
                }
            }
        }
        if(t == false)  {
            assertEquals(false, model.isOccupied(pos),"The Tile should not be occupied");
        }
    }

    @RepeatedTest(3)
    void isLosingTest(RepetitionInfo repetitionInfo) {
        if(repetitionInfo.getCurrentRepetition() == 1)  {//The player can move 2 constructors
            Random random = new Random();
            Player p = model.getListPlayer().get(random.nextInt(1));//It chooses a random player
            List<Constructor> list = p.getAllConstructors();
            list.get(0).setCanMove(true);
            list.get(1).setCanMove(true);
            assertEquals(false, model.isLosing(p), "The player is not losing");
        }
        if(repetitionInfo.getCurrentRepetition() == 2)  {//The player can move just 1 constructor
            Random random = new Random();
            Player p = model.getListPlayer().get(random.nextInt(1));//It chooses a random player
            List<Constructor> list = p.getAllConstructors();
            list.get(0).setCanMove(false);
            list.get(1).setCanMove(true);
            assertEquals(false, model.isLosing(p), "The player is not losing");
        }
        if(repetitionInfo.getCurrentRepetition() == 3)  {//The player can not move any constructor
            Random random = new Random();
            Player p = model.getListPlayer().get(random.nextInt(1));//It chooses a random player
            List<Constructor> list = p.getAllConstructors();
            list.get(0).setCanMove(false);
            list.get(1).setCanMove(false);
            assertEquals(true, model.isLosing(p), "The player is losing");
        }
    }

    @RepeatedTest(2)
    void isPlayerTurnTest(RepetitionInfo repetitionInfo) {
        if(repetitionInfo.getCurrentRepetition() == 1)  {
            model.getGameState().startGame();
            assertAll(
                    ()  ->  assertEquals(true, model.isPlayerTurn(list.get(0)), "Should be the turn of player number 1"),
                    ()  ->  assertEquals(false, model.isPlayerTurn(list.get(1)),"Should not be the turn of player number 2")
            );
        }
        if(repetitionInfo.getCurrentRepetition() == 2)  {
            model.getGameState().startGame();
            model.getGameState().nextTurn();
            assertAll(
                    ()  ->  assertEquals(false, model.isPlayerTurn(list.get(0)), "Should not be the turn of player number 1"),
                    ()  ->  assertEquals(true, model.isPlayerTurn(list.get(1)),"Should be the turn of player number 2")
            );
        }
    }

    @Test
    void performMoveTest() {
        String expectedMessage = new String();

        model.setCurrentConstructor(model.getBoard().getTile(posCurrentConstructor).getActualConstuctor());
        Position nextPosCurrentConstructor = new Position(posCurrentConstructor.getRow() + 1, posCurrentConstructor.getCol() -1);
        model.performMove(nextPosCurrentConstructor);
        expectedMessage = model.getGameState().getCurrentPlayer().getIdPlayer() + " moved to position: " + nextPosCurrentConstructor.toString();
        assertAll(
                ()  ->  assertEquals(nextPosCurrentConstructor.getRow(), model.getCurrentConstructor().getPos().getRow(), "The row should be the same"),
                ()  ->  assertEquals(nextPosCurrentConstructor.getCol(), model.getCurrentConstructor().getPos().getCol(), "The column should be the same")
        );
        assertEquals(expectedMessage, receiver.receivedMessage.getMessage(), "The message should be the same");
        assertEquals(model.getGameState().getCurrentPlayer(), receiver.receivedMessage.getPlayer(),"The player should be the same");
    }

    @Test
    void performBuild() {
        Random random = new Random();
        String expectedMessage = new String();
        Position p1 = new Position(random.nextInt(4), random.nextInt(4));
        int expectedConstructionLevel = 0;
        boolean expectedDome = false;

        while(model.getBoard().getTile(p1).getDome())  {
            p1 = new Position(random.nextInt(4), random.nextInt(4));
        }
        if(model.getBoard().getTile(p1).getConstructionLevel() < 3)    {
            expectedConstructionLevel = model.getBoard().getTile(p1).getConstructionLevel() + 1;
            expectedDome = false;
        }
        else if(model.getBoard().getTile(p1).getConstructionLevel() == 3)  {
            expectedConstructionLevel = 3;
            expectedDome = true;
        }
        model.performBuild(p1);
        expectedMessage = model.getGameState().getCurrentPlayer().getIdPlayer() + " built on position: " + p1.toString();
        assertEquals(expectedConstructionLevel, model.getBoard().getTile(p1).getConstructionLevel(),"The level should be the same");
        assertEquals(expectedDome, model.getBoard().getTile(p1).getDome(),"The dome should be the same");
        assertEquals(expectedMessage, receiver.receivedMessage.getMessage(), "The message should be the same");
        assertEquals(model.getGameState().getCurrentPlayer(), receiver.receivedMessage.getPlayer(), "The player should be the same");
    }
}