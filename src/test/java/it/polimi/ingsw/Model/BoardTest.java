package it.polimi.ingsw.Model;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void possibleMoveSet() {
    }

    @Test
    void possibleBuild() {
    }

    /**
     * testing place constructor during game setup
     */
    @Test
    void GameSetUpPlaceConstructor() {
        Board board = new Board();
        Constructor currentConstructor = new Constructor(1);
        Position position = new Position(3,2);
        Tile tile = new Tile(position);
        board.placeConstructor(tile,currentConstructor);
        assertEquals(true, board.getTile(position).getOccupied());
        assertEquals(position.getRow(),currentConstructor.getPos().getRow());
        assertEquals(position.getCol(),currentConstructor.getPos().getCol());
    }

    /**
     * test of a random move during the game
     */
    @Test
    void PlaceConstructor(){
        Board board= new Board();
        Constructor currentConstructor = new Constructor(1);
        Position position=new Position(1,1);
        Tile tile= new Tile(position);
        currentConstructor.setPrevPos(new Position(1,0));
        currentConstructor.setPos(new Position(0,0));
        board.placeConstructor(tile,currentConstructor);
        assertEquals(true, board.getTile(position).getOccupied());
        assertEquals(position.getRow(),currentConstructor.getPos().getRow());
        assertEquals(position.getCol(),currentConstructor.getPos().getCol());
    }

    /**
     * test when in a tile a place the first block of construction
     */
    @Test
    void placeBuilding() {
        Board board=new Board();
        Tile tile= new Tile(new Position(2,1));
        board.placeBuilding(tile);
        assertEquals(1,tile.getConstructionLevel());
    }

    /**
     * place a dome when construction level is 3
     */
    @Test
    void placeDome(){
        Board board=new Board();
        Tile tile= new Tile(new Position(2,1));
        for(int i=1; i<=4;i++)
        board.placeBuilding(tile);
        assertEquals(3,tile.getConstructionLevel());
        assertEquals(true, tile.getDome());
    }


    @Test
    void swapConstructors() {
        Board board= new Board();
        Constructor player1 = new Constructor(1);
        Constructor player2 = new Constructor(2);
        Position pos1 = new Position(3,2);
        Position pos2= new Position(3,3);
        player1.setPos(pos1);
        player2.setPos(pos2);
        board.swapConstructors(player1,player2);
        assertEquals(pos2.getRow(),player1.getPos().getRow());
        assertEquals(pos2.getCol(),player1.getPos().getCol());
        assertEquals(pos1.getRow(),player2.getPos().getRow());
        assertEquals(pos1.getCol(),player2.getPos().getCol());
    }

    @Test
    void pushConstructorHorizontal() {
        Board board= new Board();
        Constructor player1= new Constructor(1);
        Constructor player2= new Constructor(2);
        Position pos1 = new Position(3,2);
        Position pos2= new Position(3,3);
        player1.setPos(pos1);
        player2.setPos(pos2);
        board.pushConstructor(player2,player1);
        assertEquals(pos2.getRow(),player1.getPos().getRow());
        assertEquals(pos2.getCol(),player1.getPos().getCol());
        assertEquals(3,player2.getPos().getRow());
        assertEquals(4,player2.getPos().getCol());


            }


    @Test
    void pushConstructorVertical(){}

}