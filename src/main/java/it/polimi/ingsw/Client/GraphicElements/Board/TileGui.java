package it.polimi.ingsw.Client.GraphicElements.Board;

import it.polimi.ingsw.Client.ClientGuiApp;
import it.polimi.ingsw.Model.PossiblePhases;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;



public class TileGui extends StackPane {

    private final Text text=new Text();
    private Piece piece;
    private int level;
    private final boolean light;
    private final Rectangle tile;


    public boolean isLight() {
        return light;
    }


    public Rectangle getTile() {
        return tile;
    }

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
        tile = new Rectangle(BoardScene.TILE_SIZE, BoardScene.TILE_SIZE);
        this.light=light;
        if (light)
        tile.setFill(new ImagePattern(BoardScene.lightTileHashMap.get(0)));
        else
            tile.setFill(new ImagePattern(BoardScene.darkTileHashMap.get(0)));
        level = 0;

        getChildren().addAll(tile,text);


        relocate(x*BoardScene.TILE_SIZE,y*BoardScene.TILE_SIZE);
        text.setFont(Font.font(72));


        setOnMouseClicked(e->{
            if(BoardScene.isYourTurn()){
                if(BoardScene.isInit() || BoardScene.getPhase()==PossiblePhases.MOVE || BoardScene.getPhase()==PossiblePhases.BUILD
                        || BoardScene.getPhase()==PossiblePhases.SPECIAL_MOVE || BoardScene.getPhase()==PossiblePhases.SPECIAL_BUILD){

                    ClientGuiApp.getClient().writeToSocketGUI(y+","+x);
                }
                if(BoardScene.isCheckDome() && BoardScene.getPhase()==PossiblePhases.SPECIAL_BUILD){
                    ClientGuiApp.getClient().writeToSocketGUI(y+","+x);
                }
            }
        });
    }



    public void drawDome(){
        switch (this.level) {
            case 0 -> {
                if (this.light)
                    this.tile.setFill(new ImagePattern(BoardScene.lightTileHashMap.get(4)));
                else
                    this.tile.setFill(new ImagePattern(BoardScene.darkTileHashMap.get(4)));
                this.level=4;

            }
            case 1 -> {
                if (this.light)
                    this.tile.setFill(new ImagePattern(BoardScene.lightTileHashMap.get(5)));
                else
                    this.tile.setFill(new ImagePattern(BoardScene.darkTileHashMap.get(5)));
                this.level=5;

            }
            case 2 -> {
                if (this.light)
                    this.tile.setFill(new ImagePattern(BoardScene.lightTileHashMap.get(6)));
                else
                    this.tile.setFill(new ImagePattern(BoardScene.darkTileHashMap.get(6)));
                this.level=6;

            }
            case 3 -> {
                if (this.light)
                    this.tile.setFill(new ImagePattern(BoardScene.lightTileHashMap.get(7)));
                else
                    this.tile.setFill(new ImagePattern(BoardScene.darkTileHashMap.get(7)));
                this.level=7;

            }
        }

    }

    public void drawNextLevel(){
        switch (this.level) {
            case 0 -> {
                if (this.light)
                    this.tile.setFill(new ImagePattern(BoardScene.lightTileHashMap.get(1)));
                else
                    this.tile.setFill(new ImagePattern(BoardScene.darkTileHashMap.get(1)));
                this.level++;
            }
            case 1 -> {
                if (this.light)
                    this.tile.setFill(new ImagePattern(BoardScene.lightTileHashMap.get(2)));
                else
                    this.tile.setFill(new ImagePattern(BoardScene.darkTileHashMap.get(2)));
                this.level++;
            }
            case 2 -> {
                if (this.light)
                    this.tile.setFill(new ImagePattern(BoardScene.lightTileHashMap.get(3)));
                else
                    this.tile.setFill(new ImagePattern(BoardScene.darkTileHashMap.get(3)));
                this.level++;
            }
            case 3 -> {
                if (this.light)
                    this.tile.setFill(new ImagePattern(BoardScene.lightTileHashMap.get(7)));
                else
                    this.tile.setFill(new ImagePattern(BoardScene.darkTileHashMap.get(7)));
                this.level++;
            }
        }
    }

    public void highlightsTile(){
        if (this.light)
            this.tile.setFill(new ImagePattern(BoardScene.HighlightedLightTileHashMap.get(this.level)));
        else
            this.tile.setFill(new ImagePattern(BoardScene.HighlightedDarkTileHashMap.get(this.level)));

    }




}




