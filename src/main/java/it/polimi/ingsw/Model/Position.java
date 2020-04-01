package it.polimi.ingsw.Model;

/**This is an helper class used to represent the position of
 * tiles or any constructors
 */

public class Position implements Cloneable{
    private int row;
    private int col;

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

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
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
