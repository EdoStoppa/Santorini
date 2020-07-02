package it.polimi.ingsw.Client.GraphicElements.Board;

import it.polimi.ingsw.Client.ClientGuiApp;
import it.polimi.ingsw.Model.PossiblePhases;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 * this class manages the piece during the game
 */
public class Piece extends StackPane {

    private double posX, posY;

    //-------------------------- Fundamental methods ---------------------------


    /**
     * This method create the piece and set the eventHandler when you click on the piece
     *
     * @param type specifies piece's color
     * @param x    coordinate x
     * @param y    coordinate y
     */
    public Piece(PieceType type, int x, int y) {
        placePiece(x, y);



        Ellipse ellipse = new Ellipse(BoardScene.TILE_SIZE * 0.3, BoardScene.TILE_SIZE * 0.3);
        ellipse.setFill(type == PieceType.RED ? Color.valueOf("#c40003") : type == PieceType.WHITE ? Color.valueOf("fff9f4") : Color.valueOf("#45ffff"));
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(BoardScene.TILE_SIZE * 0.03);
        if (ClientGuiApp.width==800){
            ellipse.setTranslateX(BoardScene.TILE_SIZE*0.19);
            ellipse.setTranslateY(BoardScene.TILE_SIZE*0.195);
        }else if (ClientGuiApp.width==640){
            ellipse.setTranslateX(BoardScene.TILE_SIZE*0.19);
            ellipse.setTranslateY(BoardScene.TILE_SIZE*0.23);
        }else{
            ellipse.setTranslateX(BoardScene.TILE_SIZE*0.20);
            ellipse.setTranslateY(BoardScene.TILE_SIZE*0.20);
        }

        getChildren().add(ellipse);

        setOnMouseClicked(e -> {
            if (BoardScene.isYourTurn()) {
                if (BoardScene.getPhase() == PossiblePhases.CHOOSE_CONSTRUCTOR || BoardScene.getPhase() == PossiblePhases.SPECIAL_CHOOSE_CONSTRUCTOR) {
                    ClientGuiApp.getClient().writeToSocketGUI(BoardScene.toBoard(posY) + "," + BoardScene.toBoard(posX));
                }
                if (BoardScene.getPhase() == PossiblePhases.SPECIAL_MOVE) {
                    ClientGuiApp.getClient().writeToSocketGUI(BoardScene.toBoard(posY) + "," + BoardScene.toBoard(posX));
                }
            }
        });
    }

    /**
     * method use to set posX and posY during the initialization
     * @param x    coordinate x
     * @param y    coordinate y
     */
    public void placePiece(double x, double y){
        posX =x*BoardScene.TILE_SIZE;
        posY =y*BoardScene.TILE_SIZE;
        relocate(posX, posY);

    }

    //-------------------------- getter and setter methods for variables ---------------------------

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }


}
