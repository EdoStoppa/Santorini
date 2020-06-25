package it.polimi.ingsw.Client.GraphicElements.Board;

import it.polimi.ingsw.Client.ClientGuiApp;
import it.polimi.ingsw.Model.PossiblePhases;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Piece extends StackPane {



    private double oldX,oldY;


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


    public Piece(PieceType type, int x, int y){
        move(x,y);


        Ellipse ellipse= new Ellipse(BoardScene.TILE_SIZE*0.26,BoardScene.TILE_SIZE*0.26);
        ellipse.setFill(type==PieceType.RED? Color.valueOf("#c40003"): type==PieceType.WHITE?Color.valueOf("fff9f4"):Color.valueOf("#45ffff"));
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(BoardScene.TILE_SIZE*0.03);
        ellipse.setTranslateX((BoardScene.TILE_SIZE- BoardScene.TILE_SIZE*0.26*2)/2);
        ellipse.setTranslateY((BoardScene.TILE_SIZE- BoardScene.TILE_SIZE*0.23*2)/2);
        getChildren().add(ellipse);

        setOnMouseClicked(e->{
            System.out.println(BoardScene.isYourTurn()+" "+BoardScene.getPhase());
            if (BoardScene.isYourTurn()){
            if(BoardScene.getPhase()== PossiblePhases.CHOOSE_CONSTRUCTOR || BoardScene.getPhase()==PossiblePhases.SPECIAL_CHOOSE_CONSTRUCTOR ){
                System.out.println(BoardScene.toBoard(oldY)+","+BoardScene.toBoard(oldX));
                ClientGuiApp.getClient().writeToSocketGUI(BoardScene.toBoard(oldY)+","+BoardScene.toBoard(oldX));
            }
            if(BoardScene.getPhase()==PossiblePhases.SPECIAL_MOVE){
                System.out.println("sono qui");
                ClientGuiApp.getClient().writeToSocketGUI(BoardScene.toBoard(oldY)+","+BoardScene.toBoard(oldX));
            }
        }});
    }

    public void move(double x, double y){
        oldX=x*BoardScene.TILE_SIZE;
        oldY=y*BoardScene.TILE_SIZE;
        relocate(oldX,oldY);
        System.out.println("piece messo "+(oldX)+" "+(oldY));

    }

}
