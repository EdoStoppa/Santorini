package it.polimi.ingsw.Model;

/**This is an helper class used to represent the <em>Position</em> of
 * <em>Tiles</em> or any <em>Constructor</em>
 */

public class Position implements Cloneable{
    private final int row;
    private final int col;

    /**Creates a <em>Position</em> given the row and column
     *
     * @param row Actual row on the <em>Board</em>
     * @param col Actual column on the <em>Board</em>
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
        return (row + "," + col);
    }
}
