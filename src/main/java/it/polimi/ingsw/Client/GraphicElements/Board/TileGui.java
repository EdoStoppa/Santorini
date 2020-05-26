package it.polimi.ingsw.Client.GraphicElements.Board;

import it.polimi.ingsw.ClientGuiApp;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;



    public class TileGui extends StackPane {

        private static Text text=new Text();
        private Piece piece;
        private static int level;
        private int x, y;






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
                if(e.getButton()== MouseButton.PRIMARY && !BoardScene.isInit()){

                }else if(e.getButton()== MouseButton.PRIMARY){
                    ClientGuiApp.getClient().asyncWriteToSocketGUI(x+","+y);
                }
                }
            });
        }

        public static void drawNextLevel(int x, int y){
            TileGui tile=BoardScene.getTile(x,y);
            switch (tile.level) {
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



        }





