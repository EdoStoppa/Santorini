package it.polimi.ingsw.Client.GraphicElements.Board;

import it.polimi.ingsw.Client.ClientGuiApp;
import it.polimi.ingsw.Model.PossiblePhases;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Piece extends StackPane {
    private static final int TILE_SIZE=120;

    private final PieceType type;

    private double oldX,oldY;
    private double translationX=0,translationY=0;
    private final boolean isYourPiece;

    public void setTranslationX(double translationX) {
        this.translationX = this.translationX+translationX;
    }

    public double getTranslationY() {
        return translationY;
    }

    public double getTranslationX() {
        return translationX;
    }

    public void setTranslationY(double translationY) {
        this.translationY = this.translationY+translationY;
    }

    public void setOldX(double oldX) {
        this.oldX = oldX;
    }

    public void setOldY(double oldY) {
        this.oldY = oldY;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public PieceType getType(){
        return type;
    }

    public Piece(PieceType type, int x, int y, boolean isYourPiece){
        this.type=type;
        this.isYourPiece = isYourPiece;
        move(x,y);

        Ellipse bg= new Ellipse(TILE_SIZE*0.3125,TILE_SIZE*0.26);
        bg.setFill(Color.BLACK);

        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(TILE_SIZE*0.03);
        bg.setTranslateX((TILE_SIZE- TILE_SIZE*0.3125*2)/2);
        bg.setTranslateY((TILE_SIZE- TILE_SIZE*0.26*2)/2+TILE_SIZE*0.07);

        Ellipse ellipse= new Ellipse(TILE_SIZE*0.3125,TILE_SIZE*0.26);
        ellipse.setFill(type==PieceType.RED? Color.valueOf("#c40003"): type==PieceType.WHITE?Color.valueOf("fff9f4"):Color.valueOf("#45ffff"));
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(TILE_SIZE*0.03);
        ellipse.setTranslateX((TILE_SIZE- TILE_SIZE*0.3125*2)/2);
        ellipse.setTranslateY((TILE_SIZE- TILE_SIZE*0.26*2)/2);
        getChildren().addAll(bg,ellipse);

        setOnMouseClicked(e->{
            if (BoardScene.isYourTurn()){
            if(BoardScene.getPhase()== PossiblePhases.CHOOSE_CONSTRUCTOR || BoardScene.getPhase()==PossiblePhases.SPECIAL_CHOOSE_CONSTRUCTOR){
                BoardScene.setPieceToMove(this);
                System.out.println(BoardScene.toBoard(oldY)+","+BoardScene.toBoard(oldX));
                ClientGuiApp.getClient().asyncWriteToSocketGUI(BoardScene.toBoard(oldY)+","+BoardScene.toBoard(oldX));
            }
            if(BoardScene.getPhase()==PossiblePhases.SPECIAL_MOVE){
                BoardScene.setSpecialPieceToMove(this);
                System.out.println("sono qui");
                ClientGuiApp.getClient().asyncWriteToSocketGUI(BoardScene.toBoard(oldY)+","+BoardScene.toBoard(oldX));
            }
        }});
    }

    public void move(double x, double y){
        oldX=x*TILE_SIZE;
        oldY=y*TILE_SIZE;
        relocate(oldX+translationX,oldY+translationY);
        System.out.println("piece messo "+(oldX)+" "+(oldY));

    }

    public void abortMove(){
        relocate(oldX+translationX,oldY+translationY);
    }


    public boolean isYourPiece() {
        return isYourPiece;
    }
}
