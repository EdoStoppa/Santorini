package it.polimi.ingsw.Client.GraphicElements.Board;




import it.polimi.ingsw.Client.ClientGuiApp;
import it.polimi.ingsw.Client.GraphicElements.AlertBox;
import it.polimi.ingsw.Model.PossiblePhases;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class BoardScene {
    public static final int TILE_SIZE=120;
    public static final int WIDTH=5;
    public static final int HEIGHT=5;
    public static TextArea messages= new TextArea();
    private static boolean yourTurn=false;
    private static PossiblePhases phase;
    private static  boolean init=true;
    private static Piece pieceToMove;
    private static Piece specialPieceToMove;



    public static void setYourTurn(boolean yourTurn) {
        BoardScene.yourTurn = yourTurn;
    }


    public static boolean isYourTurn() {
        return yourTurn;
    }

    public static final TileGui[][] board= new TileGui[WIDTH][HEIGHT];

    private static final Group tileGroup = new Group();
    public static final Group pieceGroup= new Group();


    public static TileGui getTile(int x,int y) {
        return board[x][y];
    }

    public static Parent createContent(){
        Pane root= new Pane();
        root.setPrefSize(WIDTH*TILE_SIZE,HEIGHT*TILE_SIZE);
        root.getChildren().addAll(tileGroup,pieceGroup);
        HBox controller =new HBox(200);
        controller.setAlignment(Pos.BOTTOM_CENTER);
        controller.prefHeight(50);
        Pane message= new Pane();
        message.getChildren().add(messages);
        Button cosi= new Button("god list");
        cosi.setOnAction(e-> AlertBox.displayError("divinita"));
        controller.getChildren().addAll(message,cosi);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(root);
        borderPane.setBottom(controller);



        for (int y=0; y<HEIGHT;y++){
            for (int x=0; x<WIDTH;x++){
                TileGui tile= new TileGui((x+y)%2==0,x,y);
                board[x][y]=tile;

                tileGroup.getChildren().add(tile);

            }
        }

        return borderPane;
    }

    public static MoveResult tryMove(int newX, int newY){
        int x0=toBoard(pieceToMove.getOldX());
        int y0=toBoard(pieceToMove.getOldY());
        if (Math.abs(x0-newX)>1 || Math.abs(y0-newY)>1){
            return new MoveResult(MoveType.NONE);
        }
        if (board[newX][newY].hasPiece() && board[newX][newY].getPiece().getType()==PieceType.RED){
            return  new MoveResult(MoveType.SWAP);
        }
        if (board[newX][newY].hasPiece() && board[newX][newY].getPiece().getType()==PieceType.WHITE){
            return  new MoveResult(MoveType.PUSH);
        }
        if (board[newX][newY].hasPiece()){
            return new MoveResult(MoveType.NONE);
        }
        if (Math.abs(x0-newX)<=1 || Math.abs(y0-newY)<=1){
            ClientGuiApp.getClient().asyncWriteToSocketGUI(newY+","+newX);
            return new MoveResult(MoveType.NORMAL);
        }
        return new MoveResult(MoveType.NONE);

    }

    static int toBoard(double pixel){
        return (int)(pixel+TILE_SIZE/2)/TILE_SIZE;
    }

    public static Piece makePiece(PieceType type, int x, int y,boolean isYourPiece){
        Piece piece= new Piece(type,x,y, isYourPiece);
                return piece;
    }

    public static synchronized void animation(Piece piece, int x, int y){
        TranslateTransition transition= new TranslateTransition(Duration.seconds(0.5),piece);
        transition.setByY(y*TILE_SIZE);
        transition.setByX(x*TILE_SIZE);
        piece.setTranslationY(-y*TILE_SIZE);
        piece.setTranslationX(-x*TILE_SIZE);
        System.out.println("translate: "+piece.getTranslationX()+"  "+piece.getTranslationY());
        transition.play();

    }

    public static void newText(String message){
        messages.appendText(message+"\n");

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

    public static Piece getPieceToMove() {
        return pieceToMove;
    }

    public static void setPieceToMove(Piece pieceToMove) {
        BoardScene.pieceToMove = pieceToMove;
    }

    public static Piece getSpecialPieceToMove() {
        return specialPieceToMove;
    }

    public static void setSpecialPieceToMove(Piece specialPieceToMove) {
        BoardScene.specialPieceToMove = specialPieceToMove;
    }
}
