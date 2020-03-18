package it.polimi.ingsw.Model;

public class Constructor {

    private boolean canMove;
    private final int playerNumber;
    private Position posConstructor;
    private Position prevPosConstructor;

    public Constructor(int playerNumber){

        this.canMove = true;
        this.playerNumber = playerNumber;
    }


    public boolean getCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public Position getPos(){
        return this.posConstructor;
    }

    public void setPos(int x, int y){
        posConstructor.setX(x);
        posConstructor.setY(y);
    }

    public Position getPrevPos(){
        return this.posConstructor;
    }

    public void setPrevPos(Position old){
        posConstructor.setX(old.getX());
        posConstructor.setY(old.getY());
    }
}
