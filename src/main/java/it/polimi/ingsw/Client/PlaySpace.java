package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.GraphicElements.Board.BoardScene;
import it.polimi.ingsw.Client.GraphicElements.Board.Piece;
import it.polimi.ingsw.Client.GraphicElements.Board.PieceType;
import it.polimi.ingsw.Client.GraphicElements.Board.TileGui;
import it.polimi.ingsw.Model.Board;
import it.polimi.ingsw.Model.Position;

import java.util.List;

public class PlaySpace {
    private int[][] buildingMatrix;
    private int[][] constructorMatrix;

    private List<Position> tileToShow;
    private List<Position> tileToCheck;


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
        String ORIZ = "\u2550"+"\u2550"+"\u2550"+"\u2550"+"\u2550"+"\u2550"+"\u2550"+"\u2550"+"\u2550"+"\u2550";
        String VERT= "\u2551";
        String ORIZ2= "\u2550"+"\u2550"+"\u2550"+"\u2550"+"\u2550"+"\u2550"+"\u2550"+"\u2550"+"\u2550";
        System.out.println("       0         1         2         3         4");
        System.out.println("  \u2554"+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ2+"\u2557");

        int k = 0;
        for (int i=0;i<15;i++){
            String line="";
            if((i+2)%3 == 0){
                line+=k+" ";
                line+=VERT;
                for (int j=0;j<5;j++){
                    switch (constructorMatrix[k][j]) {
                        case 1:
                            if (this.printTileToShow(k, j)) {
                                line += (Color.CYAN_BACKGROUND_BRIGHT + "   " + Color.RESET + " " + Color.ANSI_RED+ buildingMatrix[k][j] + Color.RESET + " " + Color.CYAN_BACKGROUND_BRIGHT + "   ");
                            } else {
                                line += (Color.ANSI_RED + "    " + buildingMatrix[k][j] + "    ");
                            }
                            break;
                        case 2:
                            if (this.printTileToShow(k, j)) {
                                line += (Color.CYAN_BACKGROUND_BRIGHT + "   " + Color.RESET + " " + Color.ANSI_YELLOW+ buildingMatrix[k][j] + Color.RESET + " " + Color.CYAN_BACKGROUND_BRIGHT + "   ");
                            } else {
                                line += (Color.ANSI_YELLOW + "    " + buildingMatrix[k][j] + "    ");
                            }
                            break;
                        case 3:
                            if (this.printTileToShow(k, j)) {
                                line += (Color.CYAN_BACKGROUND_BRIGHT + "   " + Color.RESET + " " + Color.ANSI_PURPLE+ buildingMatrix[k][j] + Color.RESET + " " + Color.CYAN_BACKGROUND_BRIGHT + "   ");
                            } else {
                                line += (Color.ANSI_PURPLE + "    " + buildingMatrix[k][j] + "    ");
                            }
                            break;
                        case 0:
                            if (this.printTileToShow(k, j) && buildingMatrix[k][j] < 4) {
                                line += (Color.CYAN_BACKGROUND_BRIGHT + "   " + Color.RESET + " " + buildingMatrix[k][j] + Color.RESET + " " + Color.CYAN_BACKGROUND_BRIGHT + "   ");
                            } else if (buildingMatrix[k][j] != 0 && buildingMatrix[k][j] < 4) {
                                line += (Color.RESET + "    " + buildingMatrix[k][j] + "    ");
                            } else if (buildingMatrix[k][j] >= 4) {
                                line += (Color.ANSI_BLUE + "    D    ");
                            } else {
                                line += (Color.RESET + "         ");
                            }
                    }
                    if (j!=4){
                        line+=(Color.RESET+"|");
                    } else {
                        line+=(Color.RESET+VERT);
                    }
                }
                k++;
            } else {
                line += "  "+VERT;
                for(int l=0; l< 5; l++){
                    if(printTileToShow(i/3,l)){
                        line += Color.CYAN_BACKGROUND_BRIGHT + "         ";
                    } else {
                        line += "         ";
                    }

                    if(l!=4){
                        line += Color.RESET + "|";
                    } else {
                        line += Color.RESET + VERT;
                    }

                }
            }
            System.out.println(line);
        }
        System.out.println("  \u255A"+ORIZ+ORIZ+ORIZ+ORIZ+ORIZ2 + "\u255D");
    }

    public void updateConstructorGUI (int [][]playSpaceUpdated){
        if (BoardScene.isInit() && !BoardScene.isYourTurn()){
            for(int i=0;i<=4;i++){
                for (int j=0;j<=4;j++){
                    if (playSpaceUpdated[i][j]!=0 && constructorMatrix[i][j]==0){
                        System.out.println("creo il pezzo");
                        Piece piece=BoardScene.makePiece(PieceType.RED,i,j,false);
                        System.out.println("creato pezzo avversario"+i+" "+j);
                        BoardScene.pieceGroup.getChildren().add(piece);
                    }
            }

        }}

        for(int i=0;i<=4;i++){
            for(int j=0;j<=4;j++){
                 if (constructorMatrix[i][j]!=0 && playSpaceUpdated[i][j]==0){
                  for (int h=-1;h<=1;h++){
                      for (int k=-1;k<=1;k++){
                          if ((i+h)>=0 && (i+h)<=4 && (j+k)>=0 && (j+k)<=4 && (h!=0 && k!=0)){
                              if (playSpaceUpdated[i+h][j+k]==constructorMatrix[i][j] && constructorMatrix[i+h][j+k]==0){
                                  Piece moved= BoardScene.getTile(i,j).getPiece();
                                  BoardScene.animation(moved,h,k);
                                  BoardScene.getTile(i,j).setPiece(null);
                                  BoardScene.getTile(i+h,j+k).setPiece(moved);
                              }
                          }
                      }
                  }
                }
            }
        }
    }
}
