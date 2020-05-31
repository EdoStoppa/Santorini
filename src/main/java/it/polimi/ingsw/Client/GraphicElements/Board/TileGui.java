package it.polimi.ingsw.Client.GraphicElements.Board;

import it.polimi.ingsw.Client.ClientGuiApp;
import it.polimi.ingsw.Model.PossiblePhases;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;



    public class TileGui extends StackPane {

        private static Text text=new Text();
        private Piece piece;
        private static int level;
        private final int x, y;
        private boolean highlighted=false;






        public  boolean hasPiece(){
            return piece!=null;
        }

        public void setPiece(Piece piece) {
            this.piece = piece;
        }

        public Piece getPiece() {
            return piece;
        }


        public TileGui(boolean light, int x, int y){
            Rectangle border= new Rectangle(BoardScene.TILE_SIZE, BoardScene.TILE_SIZE);
            level=0;
            this.x=x;
            this.y=y;

            relocate(x*BoardScene.TILE_SIZE,y*BoardScene.TILE_SIZE);
            text.setFont(Font.font(72));

            border.setFill(light ? Color.valueOf("#feb") : Color.valueOf("#582"));
            getChildren().addAll(border,text);

            setOnMouseClicked(e->{
                if(BoardScene.isYourTurn()){
                    if(BoardScene.isInit() || BoardScene.getPhase()==PossiblePhases.MOVE || BoardScene.getPhase()==PossiblePhases.BUILD
                    || BoardScene.getPhase()==PossiblePhases.SPECIAL_MOVE || BoardScene.getPhase()==PossiblePhases.SPECIAL_BUILD){
                        ClientGuiApp.getClient().asyncWriteToSocketGUI(y+","+x);
                    }
                }
            });
        }

        public static void drawNextLevel(int x, int y){
            switch (level) {
                case 0 -> {
                    text.setText("1");
                    level++;
                }
                case 1 -> {
                    text.setText("2");
                    level++;
                }
                case 2 -> {
                    text.setText("3");
                    level++;
                }
                case 3 -> {
                    text.setText("D");
                    level++;
                }
            }
            }

        public static void drawDome(int x,int y){
            switch (level) {
                case 0 -> {
                    text.setText("5");
                    level=5;

                }
                case 1 -> {
                    text.setText("6");
                    level=6;

                }
                case 2 -> {
                    text.setText("7");
                    level=7;

                }
                case 3 -> {
                    text.setText("D");
                    level=4;

                }
            }

        }

        /*private void HandeMove(){
            int  newX=x;
            int newY=y;
            MoveResult result=BoardScene.tryMove(newX,newY);

            int x0= BoardScene.toBoard(BoardScene.getPieceToMove().getOldX());
            int y0=BoardScene.toBoard(BoardScene.getPieceToMove().getOldY());

            System.out.println(newX+" "+newY+" "+x0+" "+y0);
            switch (result.getType()) {
                case NONE -> BoardScene.getPieceToMove().abortMove();
                case NORMAL -> {
                    BoardScene.animation(BoardScene.getPieceToMove(), newX - x0, newY - y0);
                    BoardScene.getPieceToMove().setOldX(newX * 120);
                    BoardScene.getPieceToMove().setOldY(newY * 120);
                    BoardScene.board[x0][y0].setPiece(null);
                    BoardScene.board[newX][newY].setPiece(BoardScene.getPieceToMove());
                    BoardScene.setPieceToMove(null);
                }
            }

        }*/



        }




