package it.polimi.ingsw.Client.GraphicElements.Board;



import it.polimi.ingsw.Client.EventHandler;
import it.polimi.ingsw.Client.GraphicElements.AlertBox;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.ServerMessage.ServerMessage;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
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
    private static TextArea messages= new TextArea();
    private static boolean init=true;
    private static boolean yourTurn=false;

    public static void setInit(boolean init) {
        BoardScene.init=init;
    }

    public static void setYourTurn(boolean yourTurn) {
        BoardScene.yourTurn = yourTurn;
    }

    public static boolean isInit() {
        return init;
    }

    public static boolean isYourTurn() {
        return yourTurn;
    }

    private static final TileGui[][] board= new TileGui[WIDTH][HEIGHT];

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

    private static MoveResult tryMove(Piece piece, int newX, int newY){
        int x0=toBoard(piece.getOldX());
        int y0=toBoard(piece.getOldY());
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
            return new MoveResult(MoveType.NORMAL);
        }
        return new MoveResult(MoveType.NONE);

    }

    static int toBoard(double pixel){
        return (int)(pixel+TILE_SIZE/2)/TILE_SIZE;
    }

    public static Piece makePiece(PieceType type, int x, int y,boolean isYourPiece){
        Piece piece= new Piece(type,x,y, isYourPiece);

        piece.setOnMouseReleased(e->{
            if(isYourPiece){
            int newX=toBoard(piece.getLayoutX()-piece.getTranslationX());
            int newY=toBoard(piece.getLayoutY()-piece.getTranslationY());
            MoveResult result=tryMove(piece,newX,newY);

            int x0= toBoard(piece.getOldX());
            int y0=toBoard(piece.getOldY());

            System.out.println(newX+" "+newY+" "+x0+" "+y0);
            switch (result.getType()) {
                case NONE -> piece.abortMove();
                case NORMAL -> {
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);
                }
                case SWAP -> {
                    Piece swapped;
                    swapped = board[newX][newY].getPiece();
                    animation(swapped, x0 - newX, y0 - newY);
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(swapped);
                    board[newX][newY].setPiece(piece);
                    swapped.setOldX(x0 * TILE_SIZE);
                    swapped.setOldY(y0 * TILE_SIZE);
                    System.out.println("shappato " + swapped.getOldX() + " " + swapped.getOldY());
                }
                case PUSH -> {
                    Piece pushed = board[newX][newY].getPiece();
                    animation(pushed, newX - x0, newY - y0);
                    piece.move(newX, newY);
                    pushed.setOldX((newX + newX - x0) * TILE_SIZE);
                    pushed.setOldY((newY + newY - y0) * TILE_SIZE);
                    System.out.println(pushed.getOldX() + " " + pushed.getOldY());
                    board[2 * newX - x0][2 * newX - x0].setPiece(pushed);
                    board[newX][newY].setPiece(piece);
                    board[x0][y0].setPiece(null);
                    System.out.println(newX - x0);
                }
            }

        }});

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


}
