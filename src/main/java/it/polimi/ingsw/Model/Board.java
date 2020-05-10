package it.polimi.ingsw.Model;
import java.util.ArrayList;


/** This class contains the set of tiles of a game and variable canGoUp
 * is used to control Athena's God power.
 */
public class Board {
    private Tile[][] tiles;
    private boolean canGoUp;

    public Board() {
        this.tiles= new Tile[5][5];
        for (int i=0;i<=4;i++) {
            for (int j=0;j<=4;j++){
                this.tiles[i][j]=new Tile(new Position(i,j));
            }
        }
        canGoUp=true;

    }

    /**
     * this method create a list of position where the chosen constructor can move in this turn
     * @param CurrentConstructor the constructor to find the moveset
     * @return return positions of possible move
     */
    public ArrayList<Position> possibleMoveset(Constructor CurrentConstructor){
        ArrayList<Position>moves= new ArrayList<>();
        Position currentPos=CurrentConstructor.getPos();
        int curX=currentPos.getRow();
        int curY=currentPos.getCol();
        for(int i=curX-1; i<=curX+1; i++) {
            for(int j=curY-1; j<=curY+1; j++) {
                if( isAcceptablePos(i, j, currentPos) ){
                    int constructorHeight = getTile(CurrentConstructor.getPos()).getConstructionLevel();
                    int tileHeight = tiles[i][j].getConstructionLevel();

                    if(((canGoUp && (constructorHeight + 1 >= tileHeight)) || (!canGoUp && constructorHeight >= tileHeight)) && !tiles[i][j].getDome()) {
                        moves.add(new Position(i, j));
                    }
                }
             }
         }
        return moves;
    }

    private boolean isAcceptablePos(int i, int j, Position currentPos){
        return ((i>=0 && i<=4) && (j>=0 && j<=4) && !currentPos.equals(new Position(i,j)) && !tiles[i][j].getOccupied());
    }

    /**
     * this method create a list of position where the chosen constructor can build in this turn
     * @param CurrentConstructor the constructor to find the moveset
     * @return return positions where player can build
     */
    public ArrayList<Position> possibleBuild(Constructor CurrentConstructor){
        ArrayList<Position> build= new ArrayList<>();
        Position currentPos=CurrentConstructor.getPos();
        int curX = currentPos.getRow();
        int curY = currentPos.getCol();
        for(int i=curX-1;i<=curX+1;i++) {
            for (int j = curY - 1; j <= curY + 1; j++) {
                if (isAcceptablePos(i, j, currentPos) && !this.tiles[i][j].getDome()) {
                   build.add(new Position(i,j));
                }
            }
        }
        return build;}

    /**
     * this method move the chosen constructor
     * @param tile is the new tile for the constructor
     * @param currentCon is the constructor to move
     */
    public void placeConstructor(Tile tile, Constructor currentCon) {
        Position posC = currentCon.getPos().clone();

        currentCon.setPrevPos(posC);
        currentCon.setPos(tile.getPos().clone());
        tile.setOccupied(true);
        tile.setActualConstructor(currentCon);

        if(posC.getCol() != -1){
            getTile(posC).setActualConstructor(null);
            getTile(posC).setOccupied(false);
        }
    }

    /**
     * build next level of the construction
     * @param tile where increase the construction level
     */
    public void placeBuilding(Tile tile){
        tile.increaseConstructionLevel();
    }

    public void setCanGoUp(boolean current){
        this.canGoUp=current;
    }

    public boolean getCanGoUp(){
        return canGoUp;
    }

    /**
     * implement Apollo's special power
     * @param player1 Apollo's constructor
     * @param player2 opponent's constructor
     */
    public void swapConstructors ( Constructor player1,Constructor player2){
        placeConstructor(getTile(player2.getPos()),player1);
        player2.setPos(player1.getPrevPos());
        player2.setPrevPos(player1.getPos());
        getTile(player2.getPos()).setActualConstructor(player2);
        getTile(player2.getPos()).setOccupied(true);
    }

    /**
     * implement Minotaur's special power
     * @param constructorPushed the constructor who will be pushed
     * @param minotaur the constructor who'll push
     */
    public void pushConstructor (Constructor constructorPushed, Constructor minotaur){
        Position nextPosMinotaur = constructorPushed.getPos().clone();
        int pushRow = (constructorPushed.getPos().getRow() - minotaur.getPos().getRow()) + constructorPushed.getPos().getRow();
        int pushCol = (constructorPushed.getPos().getCol() - minotaur.getPos().getCol()) + constructorPushed.getPos().getCol();

        placeConstructor(getTile(new Position(pushRow,pushCol)), constructorPushed);
        placeConstructor(getTile(nextPosMinotaur), minotaur);
    }

    public Tile getTile(Position pos)   {
        return tiles[pos.getRow()][pos.getCol()];
    }

    /**
     *This method creates a matrix of integers, which represents the constructions in the board. Every cell has
     * a number, which represents the level of the building and the presence (or not) of the dome. The presence
     * of the dome is reported adding 4 to the integer we get from getConstructionLevel.
     *
     * @return the matrix in which there are all information about the level of every construction.
     */
    public int[][] createBuildingMatrix() {
        int[][] matrix = new int[5][5];
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                Position pos = new Position(i, j);
                matrix[i][j] = getTile(pos).getConstructionLevel();
                if(getTile(pos).getDome())    {
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
     * @return the matrix in which there are positions of every constructor in the game.
     */
    public int[][] createConstructorMatrix()  {
        int[][] matrix = new int[5][5];
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                Position pos = new Position(i, j);
                if(getTile(pos).getActualConstructor() != null)    {
                    matrix[i][j] = getTile(pos).getActualConstructor().getPlayerNumber();
                }
                else    {
                    matrix[i][j] = 0;
                }
            }
        }
        return matrix;
    }

    /**
     * This method search for every occupied <em>Tile</em> near the <em>Position</em> used as parameter.
     *
     * @param pos it's the <em>Position</em> from which it searches for every occupied <em>Tile</em>.
     * @return a list with all the <em>Positions</em> of every occupied <em>Tile</em>.
     */
    public ArrayList<Position> searchForOccupied(Position pos)  {
        ArrayList<Position> list = new ArrayList<>();
        int curX = pos.getRow();
        int curY = pos.getCol();

        for(int i = curX - 1; i <= curX + 1; i++)   {
            for(int j = curY - 1; j <= curY + 1; j++)   {
                    if(i >= 0 && i <= 4 && j >= 0 && j <= 4 && !pos.equals(new Position(i, j)) && tiles[i][j].getOccupied())    {
                        list.add(new Position(i, j));
                }
            }
        }
        return list;
    }
}
