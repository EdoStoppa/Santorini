package it.polimi.ingsw.Model;

import org.junit.jupiter.api.*;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PlaySpaceTest {

    /**
     * This test creates a board, which has in the attribute constructionLevel of every Tile the number of the column.
     * CreateBuildingMatrix should return the same value as the board. We use two different tests to verify
     * the possible board in a game with and without Atlas.
     */
    @RepeatedTest(2)
    @DisplayName("First rep: without Atlas, second rep: with Atlas")
    void createBuildingMatrixTest(RepetitionInfo repetitionInfo) {
        if(repetitionInfo.getCurrentRepetition() == 1)  {
            System.out.println("With Atlas");
            int[][] matrix = new int[5][5];
            Board board = new Board();
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    Position pos = new Position(i, j);
                    for (int k = 0; k < j; k++) {
                        board.getTile(pos).increaseConstructionLevel();
                    }
                    if(i == 0 && j == 0 || i == 2 && j == 3 || i == 1 && j == 2 || i == 4 && j == 1)    {
                        board.getTile(pos).setDome(true);
                    }
                }
            }

            PlaySpace p = new PlaySpace(board);

            int[][] expected = {
                    {4, 1, 2, 3, 7},
                    {0, 1, 6, 3, 7},
                    {0, 1, 2, 7, 7},
                    {0, 1, 2, 3, 7},
                    {0, 5, 2, 3, 7},
            };

            matrix = p.getBuildingMatrix();

            for(int i = 0; i < 5; i++)  {
                for(int j = 0; j < 5; j++)  {
                    assertEquals(expected[i][j], matrix[i][j], "The numbers should be the same");
                }
            }
        }

        if(repetitionInfo.getCurrentRepetition() == 2)  {
            System.out.println("Without Atlas");
            int[][] matrix = new int[5][5];
            Board board = new Board();
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    Position pos = new Position(i, j);
                    for (int k = 0; k < j; k++) {
                        board.getTile(pos).increaseConstructionLevel();
                    }
                }
            }

            PlaySpace p = new PlaySpace(board);

            int[] expected = new int[5];
            for(int i = 0; i < 5; i++)  {
                if(i < 4)   {
                    expected[i] = i;
                }
                else if(i == 4) {
                    expected[i] = 7;
                }
            }

            matrix = p.getBuildingMatrix();

            for(int i = 0; i < 5; i++)  {
                for(int j = 0; j < 5; j++)  {
                    assertEquals(expected[j], matrix[i][j], "The numbers should be the same");
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
        Board board = new Board();
        int[][] matrix = new int[5][5];

        for(int i = 0; i < 5; i++)  {
            for(int j = 0; j < 5; j++)  {
                if(i == 0 && j == 4 || i == 3 && j == 1)    {
                    Position pos = new Position(i, j);
                    Constructor c1 = new Constructor(1);
                    board.getTile(pos).setActualConstuctor(c1);
                }
                else if(i == 1 && j == 1 || i == 4 && j == 4)   {
                    Position pos = new Position(i, j);
                    Constructor c2 = new Constructor(2);
                    board.getTile(pos).setActualConstuctor(c2);
                }
            }
        }
        PlaySpace p = new PlaySpace(board);

        int[][] expected = {
                {0, 0, 0, 0, 1},
                {0, 2, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {0, 0, 0, 0, 2}
        };

        matrix = p.getConstructorMatrix();

        for(int i = 0; i < 5; i++)  {
            for(int j = 0; j < 5; j++)  {
                assertEquals(expected[i][j], matrix[i][j], "These numbers should be the same");
            }
        }
    }
}