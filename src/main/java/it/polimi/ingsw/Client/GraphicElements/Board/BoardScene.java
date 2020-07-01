package it.polimi.ingsw.Client.GraphicElements.Board;




import it.polimi.ingsw.Client.ClientGuiApp;
import it.polimi.ingsw.Client.GraphicElements.AlertBox;
import it.polimi.ingsw.Client.GraphicElements.SceneBuilder;
import it.polimi.ingsw.Model.PossiblePhases;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.HashMap;

/**
 * this class create a scene with the board and the button God List
 * where every player can read the name and the power of the god in the game.
 * another button "endPhase" is present in the scene and is made visible only when necessary.
 */
public class BoardScene {

    //-------------------------- graphic element of the scene ---------------------------
    public static final int TILE_SIZE= (int) (ClientGuiApp.width*0.1375);
    public static final int WIDTH=5;
    public static final int HEIGHT=5;
    public static TextArea messages= new TextArea();
    private static final Button end = new Button("End\nPhase");


    private static boolean yourTurn=false;
    private static PossiblePhases phase;
    private static  boolean init=true;
    private static boolean endPhase =false;
    private static boolean checkDome=false;


    //------------------------- this HashMap contains all the image that can be loaded into the tile--------------
    public  static final HashMap<Integer, Image> lightTileHashMap= new HashMap<>();
    public  static final HashMap<Integer,Image> darkTileHashMap= new HashMap<>();
    public  static final HashMap<Integer, Image> HighlightedLightTileHashMap= new HashMap<>();
    public  static final HashMap<Integer,Image> HighlightedDarkTileHashMap= new HashMap<>();

    public static final TileGui[][] board= new TileGui[WIDTH][HEIGHT];
    private static final Group tileGroup = new Group();
    public static final Group pieceGroup= new Group();


    //-------------------------- Fundamental methods ---------------------------

    /**
     * this method create the scene
     *
     * @return the parent necessary to build the scene
     */
    public static Parent createContent() {
        Pane root= new Pane();
        root.setPrefSize(WIDTH*TILE_SIZE,HEIGHT*TILE_SIZE);
        root.getChildren().addAll(tileGroup,pieceGroup);
        HBox controller =new HBox(ClientGuiApp.width*0.01);
        controller.setAlignment(Pos.BOTTOM_CENTER);
        controller.prefHeight(ClientGuiApp.height*0.142857);
        Pane message= new Pane();
        messages.setPrefHeight(ClientGuiApp.height*0.142857);
        message.getChildren().add(messages);
        Button godList= new Button("God List");

        godList.setOnAction(e->AlertBox.displayGod());
        end.setPrefSize(50, 50);
        end.setOnAction(e->{
            if(endPhase && yourTurn){
                ClientGuiApp.getClient().writeToSocketGUI("end");
            }
        });
        controller.getChildren().addAll(godList,message, end);
        end.setVisible(false);
        end.setId("endPhase");
        HBox boardLine= new HBox();
        boardLine.setAlignment(Pos.CENTER);
        boardLine.getChildren().add(root);
        VBox layout=new VBox(ClientGuiApp.width*0.025);
        layout.getChildren().addAll(boardLine,controller);
        layout.setBackground(SceneBuilder.getBackground("boardBackground"));

        for (int y=0; y<HEIGHT;y++){
            for (int x=0; x<WIDTH;x++){
                    TileGui tile= new TileGui((x+y)%2==0,x,y);
                    board[x][y]=tile;

                    tileGroup.getChildren().add(tile);

            }
        }
        layout.setPadding(new Insets(10,0,10,0));
        return layout;
    }

    /**
     * this metod cast from the position of the piece to integer
     * @param pixel coordinate of the point
     * @return the value of the related tile
     */
    static int toBoard(double pixel){
        return (int)(pixel+TILE_SIZE/2)/TILE_SIZE;
    }

    /**
     * this method make the piece
     * @param type the type of the piece (color of the piece)
     * @param x coordinate X in the tile
     * @param y coordinate Y in the tile
     * @return the piece made
     */
    public static Piece makePiece(PieceType type, int x, int y){
        System.out.println("piece created and placed in position "+x+" "+y);
        return new Piece(type,x,y);
    }

    /**
     * this method performs the animation of the pieces
     * @param piece the piece to move
     * @param x horizontal transition of X tile
     * @param y vertical transition of Y tile
     */
    public static synchronized void animation(Piece piece, int x, int y){
        TranslateTransition transition= new TranslateTransition(Duration.seconds(0.5),piece);
        transition.setByY(y*TILE_SIZE);
        transition.setByX(x*TILE_SIZE);
        transition.play();

    }

    /**
     * this method append the message in the TextArea messages
     * @param message is the string to append
     */
    public static void newText(String message){
        messages.appendText(message+"\n");

    }

    /**
     * this method clean up the TextArea messages
     */
    public static void clearText(){
        messages.clear();
    }


    //-------------------------- getter and setter methods for variables ---------------------------

    public static TileGui getTile(int x,int y) {
        return board[x][y];
    }

    public static PossiblePhases getPhase() {
        return phase;
    }

    public static void setPhase(PossiblePhases phase) {
        BoardScene.phase = phase;
    }

    public static boolean isInit() {
        return init;
    }

    public static void setInit(boolean init) {
        BoardScene.init = init;
    }

    public static void setEndPhase(boolean endPhase) {
        BoardScene.endPhase = endPhase;
    }

    public static boolean isCheckDome() {
        return checkDome;
    }

    public static void setCheckDome(boolean checkDome) {
        BoardScene.checkDome = checkDome;
    }

    public static void setEndPhaseButton(boolean visible){
        end.setVisible(visible);
    }

    public static void setYourTurn(boolean yourTurn) {
        BoardScene.yourTurn = yourTurn;
    }

    public static boolean isYourTurn() {
        return yourTurn;
    }
}