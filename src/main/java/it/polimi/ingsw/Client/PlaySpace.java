package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.GraphicElements.Board.BoardScene;
import it.polimi.ingsw.Client.GraphicElements.Board.Piece;
import it.polimi.ingsw.Client.GraphicElements.Board.PieceType;
import it.polimi.ingsw.Client.GraphicElements.Board.TileGui;
import it.polimi.ingsw.Model.Position;
import javafx.scene.paint.ImagePattern;


import java.util.List;

/**
 *this class contains two matrix to indicate the position of all buildings and constructor.
 * it is responsible to update the matrix when a new <em>BuildMessage</em> or <em>MoveMessage</em>.
 * it is also responsible to find the changes between the new matrix and the old one and indicate to <em>
 *     BoardScene</em> a movement or a construction.
 */
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

    /**
     * this funtion control if the position is contain in TileToShow
     * @param i row of the position to check
     * @param j column pf the position to check
     * @return true is the position is contain in TileToShow
     */
    public boolean isTileToShow(int i, int j){
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
                            if (this.isTileToShow(k, j)) {
                                line += (Color.CYAN_BACKGROUND_BRIGHT + "   " + Color.RESET + " " + Color.ANSI_RED+ buildingMatrix[k][j] + Color.RESET + " " + Color.CYAN_BACKGROUND_BRIGHT + "   ");
                            } else {
                                line += (Color.ANSI_RED + "    " + buildingMatrix[k][j] + "    ");
                            }
                            break;
                        case 2:
                            if (this.isTileToShow(k, j)) {
                                line += (Color.CYAN_BACKGROUND_BRIGHT + "   " + Color.RESET + " " + Color.ANSI_YELLOW+ buildingMatrix[k][j] + Color.RESET + " " + Color.CYAN_BACKGROUND_BRIGHT + "   ");
                            } else {
                                line += (Color.ANSI_YELLOW + "    " + buildingMatrix[k][j] + "    ");
                            }
                            break;
                        case 3:
                            if (this.isTileToShow(k, j)) {
                                line += (Color.CYAN_BACKGROUND_BRIGHT + "   " + Color.RESET + " " + Color.ANSI_PURPLE+ buildingMatrix[k][j] + Color.RESET + " " + Color.CYAN_BACKGROUND_BRIGHT + "   ");
                            } else {
                                line += (Color.ANSI_PURPLE + "    " + buildingMatrix[k][j] + "    ");
                            }
                            break;
                        case 0:
                            if (this.isTileToShow(k, j) && buildingMatrix[k][j] < 4) {
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
                    if(isTileToShow(i/3,l)){
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

    /**
     * this method identify movements on the board and update the constructorMatrix
     * @param playSpaceUpdated is the new constructorMatrix
     */
    public void updateConstructorGUI (int [][]playSpaceUpdated){
        if (BoardScene.isInit()) {
            for (int i = 0; i <= 4; i++) {
                for (int j = 0; j <= 4; j++) {
                    if (playSpaceUpdated[i][j] != 0 && constructorMatrix[i][j] == 0) {
                        if (!BoardScene.isYourTurn()) {
                            Piece piece;
                            if (playSpaceUpdated[i][j] == 1) {
                                piece = BoardScene.makePiece(PieceType.RED, j, i);
                            } else if(playSpaceUpdated[i][j]==2){
                                piece = BoardScene.makePiece(PieceType.WHITE, j, i);
                            }else{
                                piece = BoardScene.makePiece(PieceType.LIGHT_BLU, j, i);
                            }
                            BoardScene.getTile(j,i).setPiece(piece);
                            BoardScene.pieceGroup.getChildren().add(piece);
                            setConstructorMatrix(playSpaceUpdated);
                        } else {
                            Piece piece;
                            if (playSpaceUpdated[i][j] == 1) {
                                piece = BoardScene.makePiece(PieceType.RED, j, i);
                            } else if(playSpaceUpdated[i][j]==2){
                                piece = BoardScene.makePiece(PieceType.WHITE, j, i);
                            }else {
                                piece = BoardScene.makePiece(PieceType.LIGHT_BLU, j, i);
                            }
                            BoardScene.getTile(j,i).setPiece(piece);
                            BoardScene.pieceGroup.getChildren().add(piece);
                            setConstructorMatrix(playSpaceUpdated);
                        }
                    }

                }
            }
        }else{
        for(int i=0;i<=4;i++){
            for(int j=0;j<=4;j++){
                 if (constructorMatrix[i][j]!=0 && playSpaceUpdated[i][j]==0){
                  for (int h=-1;h<=1;h++){
                      for (int k=-1;k<=1;k++){
                          if ((i+h)>=0 && (i+h)<=4 && (j+k)>=0 && (j+k)<=4) {
                              if ((h==0 && k!=0) || (h!=0 && k==0) || (h!=0 && k!=0) ){
                              if (playSpaceUpdated[i + h][j + k] == constructorMatrix[i][j] && constructorMatrix[i + h][j + k] == 0) {
                                  Piece moved = BoardScene.getTile(j, i).getPiece();
                                  if (moved==null)
                                      System.out.println("Piece not found");
                                  BoardScene.animation(moved, k, h);
                                  BoardScene.getTile(j, i).setPiece(null);
                                  BoardScene.getTile(j + k, i + h).setPiece(moved);
                                  if (moved!=null){
                                  moved.setPosX((j + k)*BoardScene.TILE_SIZE);
                                  moved.setPosY((i + h)*BoardScene.TILE_SIZE);
                              }}

                          }
                          }
                      }
                  }
                }
            }
        }
            setConstructorMatrix(playSpaceUpdated);
        }
    }

    /**
     * this method identify update from buildingMatrix to updatedBuildingMatrix and draw this difference
     * @param updatedBuildingMatrix is the new BuildingMatrix
     * @param dome is true if is a dome to build
     */
    public void updateBuildingGUI(int[][] updatedBuildingMatrix,boolean dome) {
        for (int i=0;i<=4;i++){
            for (int j=0;j<=4;j++){
                if (buildingMatrix[i][j]!=updatedBuildingMatrix[i][j] && !dome){
                    BoardScene.getTile(j,i).drawNextLevel();
                }else if(buildingMatrix[i][j]!=updatedBuildingMatrix[i][j] && dome){
                    BoardScene.getTile(j,i).drawDome();
                }
            }
        }
        setBuildingMatrix(updatedBuildingMatrix);
    }

    /**
     * method call from Apollo when used is special power. it is to swap two piece
     * @param playSpaceUpdated is the new constructorMatrix
     */
    public void swapConstructorGUI (int [][]playSpaceUpdated){
        for (int i=0;i<=4;i++){
            for (int j=0;j<=4;j++){
                if (constructorMatrix[i][j]!=playSpaceUpdated[i][j]){
                    for (int h=-1;h<=1;h++){
                        for (int k=-1;k<=1;k++){
                            if ((i+h)>=0 && (i+h)<=4 && (j+k)>=0 && (j+k)<=4 && ((h==0 && k!=0) || (h!=0 && k==0) || (h!=0 && k!=0))){
                                if (playSpaceUpdated[i+h][j+k]==constructorMatrix[i][j] && constructorMatrix[i+h][j+k]==playSpaceUpdated[i][j]){
                                    Piece moved= BoardScene.getTile(j,i).getPiece();
                                    Piece swapped=BoardScene.getTile(j+k,i+h).getPiece();
                                    BoardScene.animation(moved,k,h);
                                    BoardScene.animation(swapped,-k,-h);
                                    BoardScene.getTile(j+k,i+h).setPiece(moved);
                                    BoardScene.getTile(j,i).setPiece(swapped);
                                    if (moved!=null){
                                        moved.setPosX((j+k)*BoardScene.TILE_SIZE);
                                        moved.setPosY((i+h)*BoardScene.TILE_SIZE);
                                    }
                                    if (swapped!=null){
                                        swapped.setPosX(j*BoardScene.TILE_SIZE);
                                        swapped.setPosY(i*BoardScene.TILE_SIZE);
                                    }
                                    setConstructorMatrix(playSpaceUpdated);
                                    return;


            }
        }

    }
                }
            }
        }
    }
    }

    /**
     * method call from minotaur when used is special power
     * @param playSpaceUpdated is the new constructorMatrix
     */
    public void pushConstructorGUI (int[][] playSpaceUpdated){
        for (int i=0;i<=4;i++){
            for (int j=0;j<=4;j++){
                if(constructorMatrix[i][j]!=0 && playSpaceUpdated[i][j]==0){
                    for (int h=-1;h<=1;h++){
                        for (int k=-1;k<=1;k++){
                            if ((i+h)>=0 && (i+h)<=4 && (j+k)>=0 && (j+k)<=4 && ((h==0 && k!=0) || (h!=0 && k==0) || (h!=0 && k!=0))){
                                if(playSpaceUpdated[i+h][j+k]==constructorMatrix[i][j] && playSpaceUpdated[i+h][j+k]!=constructorMatrix[i+h][j+k]){
                                   Piece minotaur=BoardScene.getTile(j,i).getPiece();
                                   Piece pushed=BoardScene.getTile(j+k,i+h).getPiece();
                                   if (minotaur==null){
                                       System.out.println((i+h*2)+" error minotaur "+(j+k*2));
                                   }
                                    if (pushed==null){
                                        System.out.println((i+h)+" error pushed "+(j+k));
                                    }
                                   BoardScene.animation(minotaur,k,h);
                                   BoardScene.animation(pushed,k,h);
                                   BoardScene.getTile(j+k*2,i+h*2).setPiece(pushed);
                                   BoardScene.getTile(j+k,i+h).setPiece(minotaur);
                                   BoardScene.getTile(j,i).setPiece(null);
                                    if (pushed!=null){
                                        pushed.setPosX((j+k*2)*BoardScene.TILE_SIZE);
                                        pushed.setPosY((i+h*2)*BoardScene.TILE_SIZE);
                                    }
                                    if (minotaur!=null){
                                        minotaur.setPosX((j+k)*BoardScene.TILE_SIZE);
                                        minotaur.setPosY((i+h)*BoardScene.TILE_SIZE);
                                    }
                                    setConstructorMatrix(playSpaceUpdated);
                                }
                            }
                        }
                    }

                }

            }
        }
    }


    /**
     * this method removes piece of the player who lost
     * @param playSpaceUpdated is the new constructorMatrix
     */
    public void removeConstructorGUI (int[][] playSpaceUpdated){

               for(int i=0;i<=4;i++){
            for (int j=0;j<=4;j++){
                if (playSpaceUpdated[i][j]==0 && constructorMatrix[i][j]!=0){
                    BoardScene.pieceGroup.getChildren().remove(BoardScene.getTile(j,i).getPiece());
                    BoardScene.getTile(j,i).setPiece(null);
                }
            }
        }
        setConstructorMatrix(playSpaceUpdated);
    }

    /**
     * this method count the number of piece remains on the board
     * @param playSpaceUpdated PlaySpace to count the number of piece
     * @return number of player remains
     */
    public int CountPlayerRemains(int[][] playSpaceUpdated){
        int count=0;
        for(int i=0;i<=4;i++){
            for (int j=0;j<=4;j++){
                if (playSpaceUpdated[i][j]!=0)
                    count++;
            }
        }
        return count;}

    /**
     * this method highlights tiles contains in the List tiles
     * @param tiles tiles to highlight
     */
    public void tileToShowGUI (List<Position> tiles){
        for(Position position:tiles)
            BoardScene.getTile(position.getCol(),position.getRow()).highlightsTile();
        setTileToShow(tiles);
    }

    public void disHighlightsTile(){
        if (tileToShow!=null)
        for (Position position: tileToShow) {
            TileGui highlight = BoardScene.getTile(position.getCol(), position.getRow());
            if (highlight.isLight())
                highlight.getTile().setFill(new ImagePattern(BoardScene.lightTileHashMap.get(highlight.getLevel())));
            else
                highlight.getTile().setFill(new ImagePattern(BoardScene.darkTileHashMap.get(highlight.getLevel())));
        }
    }

    //-------------------------- getter and setter methods for variables ---------------------------

    public void setSpecial(boolean special){
        BoardScene.setEndPhase(special);
    }

    public void setCheckDome(boolean checkDome){
        BoardScene.setCheckDome(checkDome);
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




}
