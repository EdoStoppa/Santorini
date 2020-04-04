package it.polimi.ingsw.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void possibleMoveSet() {
    }

    @Test
    void possibleBuild() {
    }

    @Test
    void placeConstructor() {
        Board board = new Board();
        Constructor currentConstructor = new Constructor(1);
        Position position = new Position(3,2);
        Tile tile = new Tile(position);
        board.placeConstructor(tile,currentConstructor);
        assertEquals(true, board.getTile(position).getOccupied());
        assertEquals(position.getRow(),currentConstructor.getPos().getRow());
        assertEquals(position.getCol(),currentConstructor.getPos().getCol());
    }

    @Test
    void placeBuilding() {
    }

    @Test
    void setCanGoUp() {
    }

    @Test
    void getCanGoUp() {
    }

    @Test
    void swapConstructors() {
        Constructor player1 = new Constructor(1);
        Constructor player2 = new Constructor(2);


    }

    @Test
    void pushConstructor() {
    }

    @Test
    void getTile() {
    }
}