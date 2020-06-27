package it.polimi.ingsw.Client.GraphicElements.Board;

import it.polimi.ingsw.Client.ClientGuiApp;
import it.polimi.ingsw.Model.PossiblePhases;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Piece extends StackPane {

    private double posX, posY;


    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }


    /**
     * This method create the piece and set the eventHandler when you click on the piece
     */
    public Piece(PieceType type, int x, int y){
        placePiece(x,y);


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
                System.out.println(BoardScene.toBoard(posY)+","+BoardScene.toBoard(posX));
                ClientGuiApp.getClient().writeToSocketGUI(BoardScene.toBoard(posY)+","+BoardScene.toBoard(posX));
            }
            if(BoardScene.getPhase()==PossiblePhases.SPECIAL_MOVE){
                ClientGuiApp.getClient().writeToSocketGUI(BoardScene.toBoard(posY)+","+BoardScene.toBoard(posX));
            }
        }});
    }

    public void placePiece(double x, double y){
        posX =x*BoardScene.TILE_SIZE;
        posY =y*BoardScene.TILE_SIZE;
        relocate(posX, posY);

    }

}
