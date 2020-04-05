package it.polimi.ingsw.Model;

/**Represents a <em>Constructor</em> pawn. It's one of the actual game object and during the game
 * will be moved on the board.
*/
public class Constructor {
    /**Used to know if a <em>Constructor</em> can move. If the <em>Constructor</em> is blocked during the game
     * this will assume FALSE value (and so the <em>Constructor</em> will be "deactivated")
     */
    private boolean canMove;
    /**Represents the owner of the <em>Constructor</em>
     */
    private final int playerNumber;
    /**Represents the <em>Constructor</em>'s position on the board
     */
    private Position posConstructor;
    /**Represents the previous <em>Constructor</em>'s Position on the Board
     */
    private Position prevPosConstructor;


    /**Creates a <em>Constructor</em> in a "dummy" <em>Position</em> (-1,-1) that obviously does not
     * exist on the <em>Board</em>, but during the start phase of the game will be changed to
     * a real <em>Position</em>
     *
     * @param playerNumber Number assigned to a specific <em>Player</em>
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
        return this.prevPosConstructor;
    }

    public void setPrevPos(Position old){
        this.prevPosConstructor = old;
    }


}
