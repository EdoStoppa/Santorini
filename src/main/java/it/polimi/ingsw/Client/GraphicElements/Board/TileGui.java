package it.polimi.ingsw.Client.GraphicElements.Board;

import it.polimi.ingsw.ClientGuiApp;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;



    public class TileGui extends StackPane {

        private Text text=new Text();
        private Piece piece;
        private int level;






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

            relocate(x*BoardScene.TILE_SIZE,y*BoardScene.TILE_SIZE);
            text.setFont(Font.font(72));

            border.setFill(light ? Color.valueOf("#feb") : Color.valueOf("#582"));
            getChildren().addAll(border,text);

            setOnMouseClicked(e->{
                if(BoardScene.isYourTurn()){
                if(e.getButton()== MouseButton.PRIMARY && !BoardScene.isInit()){
                    switch (level) {
                        case 0 -> {
                            draw1();
                            level++;
                        }
                        case 1 -> {
                            draw2();
                            level++;
                        }
                        case 2 -> {
                            draw3();
                            level++;
                        }
                        case 3 -> {
                            drawD();
                            level++;
                        }
                    }
                }else if(e.getButton()== MouseButton.PRIMARY){
                    this.piece=BoardScene.makePiece(PieceType.WHITE,x,y,true);
                    this.setPiece(piece);
                    BoardScene.pieceGroup.getChildren().add(piece);
                    ClientGuiApp.getClient().asyncWriteToSocketGUI(x+","+y);
                    BoardScene.setInit(false);
                    BoardScene.setYourTurn(false);
                    


            }
                }
            });
        }

        private void draw1(){
            text.setText("1");
        }

        private void draw2(){
            text.setText("2");
        }
        private void draw3(){
            text.setText("3");
        }
        private void drawD(){
            text.setText("D");
        }

    }

