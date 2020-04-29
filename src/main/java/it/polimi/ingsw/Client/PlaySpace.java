package it.polimi.ingsw.Client;

import it.polimi.ingsw.Model.Position;

import java.util.List;

public class PlaySpace {
    private int[][] buildingMatrix;
    private int[][] constructorMatrix;

    private List<Position> tileToShow;
    private List<Position> tileToCheck;
    String ORIZ ="\u2550";
    String VERT= "\u2551";

    public PlaySpace(){
        this.tileToShow = null;
        this.tileToCheck = null;

        this.buildingMatrix = new int[5][5];
        this.constructorMatrix = new int[5][5];

    }

    public int[][] getBuildingMatrix() {
        return buildingMatrix;
    }

    public int[][] getConstructorMatrix() {
        return constructorMatrix;
    }

    public void setBuildingMatrix(int[][] buildingMatrix) {
        this.buildingMatrix = buildingMatrix;
    }

    public void setConstructorMatrix(int[][] constructorMatrix) {
        this.constructorMatrix = constructorMatrix;
    }

    public List<Position> getTileToShow() {
        return tileToShow;
    }

    public void setTileToShow(List<Position> tileToShow) {
        this.tileToShow = tileToShow;
    }

    public List<Position> getTileToCheck() {
        return tileToCheck;
    }

    public void setTileToCheck(List<Position> tileToCheck) {
        this.tileToCheck = tileToCheck;
    }

    public void reset(){
        this.tileToShow = null;
        this.tileToCheck = null;
    }

    /**
     * this funtion control if the position is contain in TileToShow
     * @param i row of the position to check
     * @param j column pf the position to check
     * @return true is the position is contain in TileToShow
     */
    public boolean printTileToShow(int i, int j){
        if(this.tileToShow != null){
            for (int k=0;k<this.tileToShow.size();k++){
                if (this.tileToShow.get(k).getRow()==i && this.tileToShow.get(k).getCol()==j){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * print of board with construction and constructor during the game
     */
    public void printPlaySpace(){
        System.out.println("    0   1   2   3   4");
        System.out.println("  \u2554"+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+"\u2557");
        for (int i=0;i<5;i++){
            String line="";
            line+=i+" ";
            line+=VERT;
            for (int j=0;j<5;j++){
                switch (constructorMatrix[i][j]) {
                    case 1:
                        if (this.printTileToShow(i, j)) {
                            line += (Color.CYAN_BACKGROUND_BRIGHT + " " + Color.ANSI_RED + buildingMatrix[i][j] + Color.CYAN_BACKGROUND_BRIGHT + " ");
                        } else {
                            line += (Color.ANSI_RED + " " + buildingMatrix[i][j] + " ");
                        }
                        break;
                    case 2:
                        if (this.printTileToShow(i, j)) {
                            line += (Color.CYAN_BACKGROUND_BRIGHT + " " + Color.ANSI_YELLOW + buildingMatrix[i][j] + Color.CYAN_BACKGROUND_BRIGHT + " ");
                        } else {
                            line += (Color.ANSI_YELLOW + " " + buildingMatrix[i][j] + " ");
                        }
                        break;
                    case 3:
                        if (this.printTileToShow(i, j)) {
                            line += (Color.CYAN_BACKGROUND_BRIGHT + " " + Color.ANSI_PURPLE + buildingMatrix[i][j] + Color.CYAN_BACKGROUND_BRIGHT + " ");
                        } else {
                            line += (Color.ANSI_PURPLE + " " + buildingMatrix[i][j] + " ");
                        }
                        break;
                    case 0:
                        if (this.printTileToShow(i, j) && buildingMatrix[i][j] < 4) {
                            line += (Color.CYAN_BACKGROUND_BRIGHT + " " + Color.ANSI_BLACK + buildingMatrix[i][j] + Color.CYAN_BACKGROUND_BRIGHT + " ");
                        } else if (buildingMatrix[i][j] != 0 && buildingMatrix[i][j] < 4) {
                            line += (Color.RESET + " " + buildingMatrix[i][j] + " ");
                        } else if (buildingMatrix[i][j] >= 4) {
                            line += (Color.ANSI_BLUE + " D ");
                        } else {
                            line += (Color.RESET + "   ");
                        }
                }
                if (j!=4){
                    line+=(Color.RESET+"|");
                }
            }
            line+=(Color.RESET+VERT);
            System.out.println(line);
        }
        System.out.println("  \u255A"+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ + "\u255D");
    }
}
