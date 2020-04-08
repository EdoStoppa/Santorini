package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.God.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;


import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {
    GameState gameState;
    Player p1, p2;
    List<Player> list;

    @BeforeEach
    void init(){
        p1 = new Player("First", "01/01/2000", 1);
        p1.setGod(new Athena());

        p2 = new Player("Second", "02/01/2000", 2);
        p2.setGod(new Atlas());

        list = new ArrayList<Player>();
        list.add(p1);
        list.add(p2);

        gameState = new GameState(list);
    }

    @Test
    void phaseChangeTest(){
        gameState.nextPhase();
        assertSame(gameState.getCurrentPhase(), PossiblePhases.SPECIAL_MOVE, "Wrong change of phase");
    }

    @Test
    void needTurnChangeTest(){
        gameState.nextPhase();
        gameState.nextPhase();
        assertThrows(IndexOutOfBoundsException.class, () -> gameState.nextPhase(), "Should throw an exception to change turn");
    }

    @Test
    void nextPlayerTest(){
        gameState.nextTurn();
        assertSame(gameState.getCurrentPlayer(), p2, "Should get the next Player (in this case p2)");
    }

    @Test
    void turnChangeTest(){
        gameState.nextTurn();
        assertSame(gameState.getCurrentPlayer(), p2, "Should get the next Player (in this case p2)");
        assertSame(gameState.getCurrentPhase(), PossiblePhases.CHOOSE_CONSTRUCTOR, "Should set to first phase (always CHOOSE_CONSTRUCTOR)");
    }

    @Test
    void changeLastPlayerTest(){
        gameState.nextTurn();
        gameState.nextTurn();
        assertSame(gameState.getCurrentPlayer(), p1, "If at the end of PlayerList should return first Player in list (in this case p1)");
    }

    @Test
    void removePlayerTest(){
        gameState.removePlayer(p1);
        assertSame(gameState.getCurrentPlayer(), p2, "Should have changed to next Player");
        gameState.nextTurn();
        assertSame(gameState.getCurrentPlayer(), p2, "Should have been again p2 because that's the oly player remaining");
    }

}