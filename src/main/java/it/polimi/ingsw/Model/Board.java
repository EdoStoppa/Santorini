package it.polimi.ingsw.Model;
import java.util.ArrayList;

public class Board {
    private Tile[][] tiles;
    private boolean canGoUp = true;

    public Board(Tile[][] tiles) {
        this.tiles = tiles;
    }


    //not complete
    public ArrayList<Position> possibleMoveset(Constructor CurrentConstructor){
        ArrayList<Position>moves= new ArrayList<>();
        Position currentPos=CurrentConstructor.getPos();
        int curX=currentPos.getRow();
        int curY=currentPos.getCol();
        for(int i=curX-1;i<=curX+1;i++) {
            for(int j=curY-1;j<=curY+1;j++) {
                if((i>=0 && i<=4) && (j>=0 && j<=4) && (i!=curX && j!=curY) && (tiles[curX][curY].getConstructionLevel()+1<=tiles[i][j].getConstructionLevel())) {
                    moves.add(new Position(i, j));
                }
             }
         }
        return moves;
    }

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

    public void placeContructor(Tile pos,Constructor currentCon) {
        currentCon.setPos(pos.getPos());
    }

    public void placeBuilding(Tile current){
        current.increaseConstructionLevel();
    }

    public void setCanGoUp(boolean current){
        this.canGoUp=current;
    }

    public boolean getCanGoUp(){
        return canGoUp;
    }

    public void swapConstructors ( Constructor player1,Constructor player2){
        Position temp;
        temp=player1.getPos().clone();
        player1.setPos(player2.getPos().clone());
        player2.setPos(temp);
    }

    public void pushConstructor (Constructor constructorPushed, int x, int y){
        Position prev;
        prev=constructorPushed.getPos().clone();
        int pushX=prev.getRow()+x;
        int pushY=prev.getCol()+y;
        constructorPushed.setPos(new Position(pushX, pushY));
    }

}
