package it.polimi.ingsw.Model;

/**Represents a Constructor pawn. It's one of the actual game object and during the game
 * will be moved on the board.
*/
public class Constructor {
    /**Used to know if a Constructor can move. If the Constructor is blocked during the game
     * this will assume FALSE value (and so the Constructor will be "deactivated")
     */
    private boolean canMove;
    /**Represents the owner of the Constructor
     */
    private final int playerNumber;
    /**Represents the Constructor's position on the board
     */
    private Position posConstructor;
    /**Represents the previous Constructor's Position on the Board
     */
    private Position prevPosConstructor;


    /**Creates a Constructor in a "dummy" position (-1,-1) that obviously do not
     * exist on the board, but during the start phase of the game will be changed to
     * a real Position
     *
     * @param playerNumber Number assigned to a specific Player
     */
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
