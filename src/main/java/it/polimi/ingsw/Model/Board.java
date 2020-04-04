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
        for (int i=0;i<=4;i++)
        {
            for (int j=0;j<=4;j++)
            {this.tiles[i][j]=new Tile(new Position(i,j));}
        }
        canGoUp=true;

    }


    /**
     * this method create a list of position where the chosen constructor can move in this turn
     * @param CurrentConstructor
     * @return return positions of possible move
     */

    public ArrayList<Position> possibleMoveset(Constructor CurrentConstructor){
        ArrayList<Position>moves= new ArrayList<>();
        Position currentPos=CurrentConstructor.getPos();
        int curX=currentPos.getRow();
        int curY=currentPos.getCol();
        for(int i=curX-1;i<=curX+1;i++) {
            for(int j=curY-1;j<=curY+1;j++) {
                if((i>=0 && i<=4) && (j>=0 && j<=4) && (i!=curX && j!=curY) &&(!tiles[i][j].getOccupied()) &&
                        ((getTile(CurrentConstructor.getPos()).getConstructionLevel()+1<=tiles[i][j].getConstructionLevel() && canGoUp)
                                || (!canGoUp && getTile(CurrentConstructor.getPos()).getConstructionLevel()==tiles[i][j].getConstructionLevel()))
                        && (!tiles[i][j].getDome())) {
                        moves.add(new Position(i, j));
                    }
             }
         }
        return moves;
    }

    /**
     * this method create a list of position where the chosen constructor can build in this turn
     * @param CurrentConstructor
     * @return return positions where player can build
     */
    public ArrayList<Position> possibleBuild(Constructor CurrentConstructor){
        ArrayList<Position>build= new ArrayList<>();
        Position currentPos=CurrentConstructor.getPos();
        int curX=currentPos.getRow();
        int curY=currentPos.getCol();
        for(int i=curX-1;i<=curX+1;i++) {
            for (int j = curY - 1; j <= curY + 1; j++) {
                if ((i >= 0 && i <= 4) && (j >= 0 && j <= 4) && (i != curX && j != curY) && !tiles[i][j].getDome() && tiles[i][j].getOccupied()) {
                   build.add(new Position(i,j));
                }
            }
        }
        return build;}

    /**
     * this method move the chosen constructor
     * @param pos is the new position for the constructor
     * @param currentCon is the constructor to move
     */
    public void placeConstructor(Tile pos, Constructor currentCon)
    {   currentCon.setPrevPos(currentCon.getPos());
        currentCon.setPos(pos.getPos());
        if (currentCon.getPrevPos().getRow()!=-1)
        getTile(currentCon.getPrevPos()).setOccupied(false);
        getTile(currentCon.getPos()).setOccupied(true);
    }

    /**
     * build next level of the construction
     * @param current
     */
    public void placeBuilding(Tile current){
        current.increaseConstructionLevel();
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
        Position temp;
        temp=player1.getPos().clone();
        player1.setPos(player2.getPos().clone());
        player2.setPos(temp);
    }

    /**
     * implement Minotaur's special power
     * @param constructorPushed
     * @param row
     * @param col
     */
    public void pushConstructor (Constructor constructorPushed, int row, int col){
        Position prev;
        prev=constructorPushed.getPos().clone();
        int pushRow=prev.getRow()+row;
        int pushCol=prev.getCol()+col;
        constructorPushed.setPos(new Position(pushRow, pushCol));
    }

    public Tile getTile(Position pos)   {
        return tiles[pos.getRow()][pos.getCol()];
    }

}
