package it.polimi.ingsw.Client.GraphicElements.Board;

import it.polimi.ingsw.Client.ClientGuiApp;
import it.polimi.ingsw.Model.PossiblePhases;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;



public class TileGui extends StackPane {

    private final Text text=new Text();
    private Piece piece;
    private int level;


    public  int getLevel() {
        return level;
    }

    public  void setLevel(int level) {
        this.level = level;
    }

    public  Text getText() {
        return text;
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
                if(BoardScene.isInit() || BoardScene.getPhase()==PossiblePhases.MOVE || BoardScene.getPhase()==PossiblePhases.BUILD
                        || BoardScene.getPhase()==PossiblePhases.SPECIAL_MOVE || BoardScene.getPhase()==PossiblePhases.SPECIAL_BUILD){
                    ClientGuiApp.getClient().asyncWriteToSocketGUI(y+","+x);
                }
                if(BoardScene.isCheckDome() && BoardScene.getPhase()==PossiblePhases.SPECIAL_BUILD){
                    ClientGuiApp.getClient().asyncWriteToSocketGUI(y+","+x);
                }
            }
        });
    }



    public void drawDome(){
        switch (this.level) {
            case 0 -> {
                this.text.setText("D");
                this.level=5;

            }
            case 1 -> {
                this.text.setText("D");
                this.level=6;

            }
            case 2 -> {
                this.text.setText("D");
                this.level=7;

            }
            case 3 -> {
                this.text.setText("D");
                this.level=4;

            }
        }

    }



}




