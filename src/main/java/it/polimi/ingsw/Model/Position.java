package it.polimi.ingsw.Model;

/**This is an helper class used to represent the position of
 * tiles or any constructors
 */

public class Position implements Cloneable{
    private final int row;
    private final int col;

    /**Creates a Position given the row and column
     *
     * @param row Actual row on the board
     * @param col Actual column on the board
     */
    public Position(int row, int col){
        this.row = row;
        this.col = col;
    }


    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public Position clone(){
        return new Position(this.row, this.col);
    }

    public String toString(){
        String s = row + "," + col;
        return s;
    }
}
