package it.polimi.ingsw.Model;

public class Position implements Cloneable{
    private int row;
    private int col;

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
