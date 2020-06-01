package it.polimi.ingsw.Client.GraphicElements.Board;

import it.polimi.ingsw.Client.ClientGuiApp;
import it.polimi.ingsw.Client.GraphicElements.AlertBox;
import it.polimi.ingsw.Message.HelpMessage;
import it.polimi.ingsw.Model.PossiblePhases;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;



    public class TileGui extends StackPane {

        private Text text=new Text();
        private Piece piece;
        private int level;
        private final int x, y;
        private boolean highlighted=false;

        public  int getLevel() {
            return level;
        }

        public  void setLevel(int level) {
            this.level = level;
        }

        public  Text getText() {
            return text;
        }

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
                    if(BoardScene.isCheckDome() && BoardScene.getPhase()==PossiblePhases.SPECIAL_BUILD){
                        ClientGuiApp.getClient().asyncWriteToSocketGUI(y+","+x);
                        Boolean answer=AlertBox.CheckDome("dome");
                        if(answer){
                            ClientGuiApp.getClient().asyncWriteToSocketGUI("y");
                        }else{
                            ClientGuiApp.getClient().asyncWriteToSocketGUI("n");
                        }
                    }
                }
            });
        }



        public void drawDome(){
            switch (this.level) {
                case 0 -> {
                    this.text.setText("5");
                    this.level=5;

                }
                case 1 -> {
                    this.text.setText("6");
                    this.level=6;

                }
                case 2 -> {
                    this.text.setText("7");
                    this.level=7;

                }
                case 3 -> {
                    this.text.setText("D");
                    this.level=4;

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





