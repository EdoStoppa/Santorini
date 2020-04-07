package it.polimi.ingsw.Model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;


import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    /**
     * the constructor is in a generic position
     */
    @Test
    void possibleMoveSet() {
        ArrayList<Position> possibleMoveSet;
        Board board = new Board();
        Constructor constructor=new Constructor(1);
        Position position= new Position(2,2);
        board.placeConstructor(board.getTile(position),constructor);
        board.placeBuilding(board.getTile(new Position(1,1)));
        possibleMoveSet= board.possibleMoveset(constructor);
        assertEquals(8,possibleMoveSet.size());
        assertEquals(1,possibleMoveSet.get(0).getRow());
        assertEquals(1,possibleMoveSet.get(0).getCol());
        assertEquals(1,possibleMoveSet.get(1).getRow());
        assertEquals(2,possibleMoveSet.get(1).getCol());
        assertEquals(1,possibleMoveSet.get(2).getRow());
        assertEquals(3,possibleMoveSet.get(2).getCol());
        assertEquals(2,possibleMoveSet.get(3).getRow());
        assertEquals(1,possibleMoveSet.get(3).getCol());
        assertEquals(2,possibleMoveSet.get(4).getRow());
        assertEquals(3,possibleMoveSet.get(4).getCol());
        assertEquals(3,possibleMoveSet.get(5).getRow());
        assertEquals(1,possibleMoveSet.get(5).getCol());
        assertEquals(3,possibleMoveSet.get(6).getRow());
        assertEquals(2,possibleMoveSet.get(6).getCol());
        assertEquals(3,possibleMoveSet.get(7).getRow());
        assertEquals(3,possibleMoveSet.get(7).getCol());
    }

    /**
     * the constructor is in the top-left corner
     */
    @Test
    void topLeftCornerPos(){
        ArrayList<Position> possibleMoveSet;
        Board board = new Board();
        Constructor constructor=new Constructor(1);
        Position position= new Position(0,0);
        board.placeConstructor(board.getTile(position),constructor);
        possibleMoveSet= board.possibleMoveset(constructor);
        assertEquals(3,possibleMoveSet.size());
        assertEquals(0,possibleMoveSet.get(0).getRow());
        assertEquals(1,possibleMoveSet.get(0).getCol());
        assertEquals(1,possibleMoveSet.get(1).getRow());
        assertEquals(0,possibleMoveSet.get(1).getCol());
        assertEquals(1,possibleMoveSet.get(2).getRow());
        assertEquals(1,possibleMoveSet.get(2).getCol());
    }

    /**
     * the constructor is in the down-right corner
     */
    @Test
    void DownRightCornerPos(){
        ArrayList<Position> possibleMoveSet;
        Board board = new Board();
        Constructor constructor=new Constructor(1);
        Position position= new Position(4,4);
        board.placeConstructor(board.getTile(position),constructor);
        possibleMoveSet= board.possibleMoveset(constructor);
        assertEquals(3,possibleMoveSet.size());
        assertEquals(3,possibleMoveSet.get(0).getRow());
        assertEquals(3,possibleMoveSet.get(0).getCol());
        assertEquals(3,possibleMoveSet.get(1).getRow());
        assertEquals(4,possibleMoveSet.get(1).getCol());
        assertEquals(4,possibleMoveSet.get(2).getRow());
        assertEquals(3,possibleMoveSet.get(2).getCol());
    }

    /**
     * there aren't tiles where is possible build
     */
    @Test
    void NoPossibleMove(){
        ArrayList<Position> possibleMoveSet;
        Board board = new Board();
        Constructor constructor=new Constructor(1);
        Position position= new Position(4,4);
        board.placeConstructor(board.getTile(position),constructor);
        board.getTile(new Position(3,3)).setOccupied(true);
        board.getTile(new Position(3,4)).setDome(true);
        board.getTile(new Position(4,3)).increaseConstructionLevel();
        board.getTile(new Position(4,3)).increaseConstructionLevel();
        possibleMoveSet= board.possibleMoveset(constructor);
        assertEquals(0,possibleMoveSet.size());
    }

    /**
     * test when canGoUp is false
     */
    @Test
    void CanGoUpFalse(){
        ArrayList<Position> possibleMoveSet;
        Board board = new Board();
        board.setCanGoUp(false);
        Constructor constructor=new Constructor(1);
        Position position= new Position(0,0);
        board.placeConstructor(board.getTile(position),constructor);
        board.placeBuilding(board.getTile(new Position(1,1)));
        possibleMoveSet= board.possibleMoveset(constructor);
        assertEquals(2,possibleMoveSet.size());
        assertEquals(0,possibleMoveSet.get(0).getRow());
        assertEquals(1,possibleMoveSet.get(0).getCol());
        assertEquals(1,possibleMoveSet.get(1).getRow());
        assertEquals(0,possibleMoveSet.get(1).getCol());
    }


    /**
     * the constructor is in a generic position
     */
    @Test
    void possibleBuild() {
        ArrayList<Position> possibleBuildSet;
        Board board = new Board();
        Constructor constructor=new Constructor(1);
        Position position= new Position(2,2);
        board.placeConstructor(board.getTile(position),constructor);
        possibleBuildSet= board.possibleBuild(constructor);
        assertEquals(8,possibleBuildSet.size());
        assertEquals(1,possibleBuildSet.get(0).getRow());
        assertEquals(1,possibleBuildSet.get(0).getCol());
        assertEquals(1,possibleBuildSet.get(1).getRow());
        assertEquals(2,possibleBuildSet.get(1).getCol());
        assertEquals(1,possibleBuildSet.get(2).getRow());
        assertEquals(3,possibleBuildSet.get(2).getCol());
        assertEquals(2,possibleBuildSet.get(3).getRow());
        assertEquals(1,possibleBuildSet.get(3).getCol());
        assertEquals(2,possibleBuildSet.get(4).getRow());
        assertEquals(3,possibleBuildSet.get(4).getCol());
        assertEquals(3,possibleBuildSet.get(5).getRow());
        assertEquals(1,possibleBuildSet.get(5).getCol());
        assertEquals(3,possibleBuildSet.get(6).getRow());
        assertEquals(2,possibleBuildSet.get(6).getCol());
        assertEquals(3,possibleBuildSet.get(7).getRow());
        assertEquals(3,possibleBuildSet.get(7).getCol());
    }

    /**
     * the constructor is in the top-left corner
     */
    @Test
    void topLeftCorner(){
        ArrayList<Position> possibleBuildSet;
        Board board = new Board();
        Constructor constructor=new Constructor(1);
        Position position= new Position(0,0);
        board.placeConstructor(board.getTile(position),constructor);
        possibleBuildSet= board.possibleBuild(constructor);
        assertEquals(3,possibleBuildSet.size());
        assertEquals(0,possibleBuildSet.get(0).getRow());
        assertEquals(1,possibleBuildSet.get(0).getCol());
        assertEquals(1,possibleBuildSet.get(1).getRow());
        assertEquals(0,possibleBuildSet.get(1).getCol());
        assertEquals(1,possibleBuildSet.get(2).getRow());
        assertEquals(1,possibleBuildSet.get(2).getCol());
    }

    /**
     * the constructor is in the down-right corner
     */
    @Test
    void downRightCorner(){
        ArrayList<Position> possibleBuildSet;
        Board board = new Board();
        Constructor constructor=new Constructor(1);
        Position position= new Position(4,4);
        board.placeConstructor(board.getTile(position),constructor);
        possibleBuildSet= board.possibleBuild(constructor);
        assertEquals(3,possibleBuildSet.size());
        assertEquals(3,possibleBuildSet.get(0).getRow());
        assertEquals(3,possibleBuildSet.get(0).getCol());
        assertEquals(3,possibleBuildSet.get(1).getRow());
        assertEquals(4,possibleBuildSet.get(1).getCol());
        assertEquals(4,possibleBuildSet.get(2).getRow());
        assertEquals(3,possibleBuildSet.get(2).getCol());
    }

    /**
     * there aren't tiles where is possible build
     */
    @Test
    void allDomePossibleBuild()
    {ArrayList<Position> possibleBuildSet;
        Board board = new Board();
        Constructor constructor=new Constructor(1);
        Position position= new Position(2,2);
        board.placeConstructor(board.getTile(position),constructor);
        board.getTile(new Position(1,1)).setDome(true);
        board.getTile(new Position(1,2)).setDome(true);
        board.getTile(new Position(1,3)).setDome(true);
        board.getTile(new Position(2,1)).setDome(true);
        board.getTile(new Position(2,3)).setDome(true);
        board.getTile(new Position(3,1)).setDome(true);
        board.getTile(new Position(3,2)).setDome(true);
        board.getTile(new Position(3,3)).setDome(true);
        possibleBuildSet= board.possibleBuild(constructor);
        assertEquals(0,possibleBuildSet.size()); }

    /**
     * another constructor near current constructor
     */
    @Test
    void nearConstructor(){
        ArrayList<Position> possibleBuildSet;
        Board board = new Board();
        Constructor constructor=new Constructor(1);
        Position position= new Position(4,4);
        board.placeConstructor(board.getTile(position),constructor);
        board.getTile(new Position(4,3)).setOccupied(true);
        possibleBuildSet= board.possibleBuild(constructor);
        assertEquals(2,possibleBuildSet.size());
        assertEquals(3,possibleBuildSet.get(0).getRow());
        assertEquals(3,possibleBuildSet.get(0).getCol());
        assertEquals(3,possibleBuildSet.get(1).getRow());
        assertEquals(4,possibleBuildSet.get(1).getCol());
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
        assertTrue(board.getTile(position).getOccupied());
        assertEquals(position.getRow(),currentConstructor.getPos().getRow());
        assertEquals(position.getCol(),currentConstructor.getPos().getCol());
        assertEquals(currentConstructor,tile.getActualConstuctor());
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
        assertTrue(board.getTile(position).getOccupied());
        assertEquals(position.getRow(),currentConstructor.getPos().getRow());
        assertEquals(position.getCol(),currentConstructor.getPos().getCol());
        assertEquals(currentConstructor,tile.getActualConstuctor());
        assertFalse(board.getTile(currentConstructor.getPrevPos()).getOccupied());
        assertEquals(0,currentConstructor.getPrevPos().getCol());
        assertEquals(0,currentConstructor.getPrevPos().getRow());
        assertNull(board.getTile(currentConstructor.getPrevPos()).getActualConstuctor());
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
        assertTrue(tile.getDome());
    }


    @Test
    void swapConstructors() {
        Board board= new Board();
        Constructor player1 = new Constructor(1);
        Constructor player2 = new Constructor(2);
        Position pos1 = new Position(2,2);
        Position pos2= new Position(3,3);
        player1.setPos(pos1);
        player2.setPos(pos2);
        board.swapConstructors(player1,player2);
        assertEquals(pos2.getRow(),player1.getPos().getRow());
        assertEquals(pos2.getCol(),player1.getPos().getCol());
        assertEquals(pos1.getRow(),player2.getPos().getRow());
        assertEquals(pos1.getCol(),player2.getPos().getCol());
        assertEquals(player1,board.getTile(player1.getPos()).getActualConstuctor());
        assertEquals(player2,board.getTile(player2.getPos()).getActualConstuctor());
        assertTrue(board.getTile(pos1).getOccupied());
        assertTrue(board.getTile(pos2).getOccupied());
        assertEquals(pos2.getRow(),player2.getPrevPos().getRow());
        assertEquals(pos2.getCol(),player2.getPrevPos().getCol());
        assertEquals(pos1.getRow(),player1.getPrevPos().getRow());
        assertEquals(pos1.getCol(),player1.getPrevPos().getCol());
    }

    @Test
    void pushConstructorHorizontal() {
        Board board= new Board();
        Constructor player1= new Constructor(1);
        Constructor player2= new Constructor(2);
        Position pos1 = new Position(3,3);
        Position pos2= new Position(3,2);
        player1.setPos(pos1);
        player2.setPos(pos2);
        board.pushConstructor(player2,player1);
        assertEquals(pos2.getRow(),player1.getPos().getRow());
        assertEquals(pos2.getCol(),player1.getPos().getCol());
        assertEquals(3,player2.getPos().getRow());
        assertEquals(1,player2.getPos().getCol());


            }


    @Test
    void pushConstructorVertical(){
        Board board= new Board();
        Constructor player1= new Constructor(1);
        Constructor player2= new Constructor(2);
        Position pos1 = new Position(2,2);
        Position pos2= new Position(3,2);
        player1.setPos(pos1);
        player2.setPos(pos2);
        board.pushConstructor(player2,player1);
        assertEquals(pos2.getRow(),player1.getPos().getRow());
        assertEquals(pos2.getCol(),player1.getPos().getCol());
        assertEquals(4,player2.getPos().getRow());
        assertEquals(2,player2.getPos().getCol());
    }

    @Test
    void pushConstructorDiagonal(){
        Board board= new Board();
        Constructor player1= new Constructor(1);
        Constructor player2= new Constructor(2);
        Position pos1 = new Position(1,1);
        Position pos2= new Position(2,2);
        player1.setPos(pos1);
        player2.setPos(pos2);
        board.pushConstructor(player2,player1);
        assertEquals(pos2.getRow(),player1.getPos().getRow());
        assertEquals(pos2.getCol(),player1.getPos().getCol());
        assertEquals(3,player2.getPos().getRow());
        assertEquals(3,player2.getPos().getCol());
    }

    /**
     * This test creates a board, which has in the attribute constructionLevel of every Tile the number of the column.
     * CreateBuildingMatrix should return the same value as the board. We use two different tests to verify
     * the possible board in a game with and without Atlas.
     */
    @RepeatedTest(2)
    @DisplayName("First rep: with Atlas, second rep: without Atlas")
    void createBuildingMatrixTest(RepetitionInfo repetitionInfo) {
        if(repetitionInfo.getCurrentRepetition() == 1)  {
            System.out.println("With Atlas");
            int[][] matrix;
            int[][] expected = new int[5][5];
            Random random = new Random();
            Board board = new Board();
            int countDome = 0;

            for (int i = 0; i < 5; i++) { //creates a random board
                for (int j = 0; j < 5; j++) {
                    Position pos = new Position(i, j);
                    int miavar = random.nextInt(4);
                    if(miavar < 4)  {
                        expected[i][j] = miavar;
                    }
                    else if(miavar == 4)    {
                        expected[i][j] = miavar + 3;
                    }
                    for(int k = 0; k < miavar; k++) {
                        board.getTile(pos).increaseConstructionLevel();
                    }
                }
            }
            while(countDome < 4)    { //It creates 4 domes in random position of the board
                int x = random.nextInt(4);
                int y = random.nextInt(4);
                Position pos = new Position(x, y);
                if(!board.getTile(pos).getDome())   {
                    board.getTile(pos).setDome(true);
                    countDome++;
                    expected[x][y] = expected[x][y] + 4;
                }
            }

            matrix = board.createBuildingMatrix();

            for(int i = 0; i < 5; i++)  {
                for(int j = 0; j < 5; j++)  {
                    assertEquals(expected[i][j], matrix[i][j], "The numbers should be the same");
                }
            }
        }

        if(repetitionInfo.getCurrentRepetition() == 2)  {
            System.out.println("Without Atlas");
            int[][] matrix;
            int[][] expected = new int[5][5];
            Random random = new Random();
            Board board = new Board();

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    Position pos = new Position(i, j);
                    int miavar = random.nextInt(4);
                    if(miavar < 4)  {
                        expected[i][j] = miavar;
                    }
                    else if(miavar == 4)    {
                        expected[i][j] = miavar + 3;
                    }
                    for(int k = 0; k < miavar;k++)  {
                        board.getTile(pos).increaseConstructionLevel();
                    }
                }
            }
            matrix = board.createBuildingMatrix();
            for(int i = 0; i < 5; i++)  {
                for(int j = 0; j < 5; j++)  {
                    assertEquals(expected[i][j], matrix[i][j], "The numbers should be the same");
                }
            }

        }
    }

    /**
     * This test check that the constructorMatrix created in the Playspace represents the positions of the cosntructors
     * in the game.
     */
    @Test
    void createConstructorMatrix()  {
        int[][] matrix;
        int[][] expected = new int[5][5];
        Random random = new Random();
        Board board = new Board();
        int numberConstructorsP1 = 0, numberConstructorsP2 = 0;

        for(int i = 0; i < 5; i++)  {//expected matrix initialization
            for(int j = 0; j < 5; j++)  {
                expected[i][j] = 0;
            }
        }

        while(numberConstructorsP1 < 2 || numberConstructorsP2 < 2) {//It creates 2 constructors for each player and it places them in random positions
            int x = random.nextInt(4);
            int y = random.nextInt(4);
            Position pos = new Position(x, y);
            if(board.getTile(pos).getActualConstuctor() == null)    {
                if(numberConstructorsP1 < 2)    {
                    Constructor c1 = new Constructor(1);
                    board.getTile(pos).setActualConstuctor(c1);
                    expected[x][y] = 1;
                    numberConstructorsP1++;
                }
                else if(numberConstructorsP2 < 2)   {
                    Constructor c2 = new Constructor(2);
                    board.getTile(pos).setActualConstuctor(c2);
                    expected[x][y] = 2;
                    numberConstructorsP2++;
                }
            }
        }
        matrix = board.createConstructorMatrix();
        for(int i = 0; i < 5; i++)  {
            for(int j = 0; j < 5; j++)  {
                assertEquals(expected[i][j], matrix[i][j], "These numbers should be the same");
            }
        }
    }
}