package it.polimi.ingsw.Model;

public class PlaySpace {
    private int[][] buildingMatrix;
    private int[][] constructorMatrix;
    private Tile[] tileToShow;

    public PlaySpace(Board board)  {
        buildingMatrix = createBuildingMatrix(board);
        constructorMatrix = createConstructorMatrix(board);
    }

    /**
     *This method creates a matrix of integers, which represents the constructions in the board. Every cell has
     * a number, which represents the level of the building and the presence (or not) of the dome. The presence
     * of the dome is reported adding 4 to the integer we get from getConstructionLevel.
     *
     * @param board it's the object which contains all informations of every tile.
     * @return the matrix in which there are all informations about the level of every construction.
     */
    private int[][] createBuildingMatrix(Board board) {
        int[][] matrix = new int[5][5];
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                Position pos = new Position(i, j);
                matrix[i][j] = board.getTile(pos).getConstructionLevel();
                if(board.getTile(pos).getDome())    {
                    matrix[i][j] = matrix[i][j] + 4;
                }
            }
        }
        return matrix;
    }

    /**
     *This method creates a matrix of integer, which represents the constructors in the board. Every constructor is
     * identified with playerNumber, which is used as identifier in the matrix.
     *
     * @param board it's the object which contains all informations of every tile.
     * @return the matrix in which there are positions of every constructor in the game.
     */
    private int[][] createConstructorMatrix(Board board)  {
        int[][] matrix = new int[5][5];
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                Position pos = new Position(i, j);
                if(board.getTile(pos).getActualConstuctor() != null)    {
                    matrix[i][j] = board.getTile(pos).getActualConstuctor().getPlayerNumber();
                }
                else    {
                    matrix[i][j] = 0;
                }
            }
        }
        return matrix;
    }

    public int[][] getBuildingMatrix() {
        return buildingMatrix;
    }

    public int[][] getConstructorMatrix() {
        return constructorMatrix;
    }

    public Tile[] getTileToShow() {
        return tileToShow;
    }
}
