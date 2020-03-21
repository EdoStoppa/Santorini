package it.polimi.ingsw.Model;

public class Constructor {

    private boolean canMove;
    private final int playerNumber;
    private Position posConstructor;
    private Position prevPosConstructor;

    public Constructor(int playerNumber){
        this.canMove = true;
        this.playerNumber = playerNumber;
        this.posConstructor = new Position(-1, -1);
        this.prevPosConstructor = new Position(-1, -1);
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

    public void setPos(Position p){
        this.posConstructor = p;
    }

    public Position getPrevPos(){
        return this.posConstructor;
    }

    public void setPrevPos(Position old){
        this.prevPosConstructor = old;
    }


}
