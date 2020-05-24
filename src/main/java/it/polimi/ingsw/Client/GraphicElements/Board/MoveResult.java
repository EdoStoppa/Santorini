package it.polimi.ingsw.Client.GraphicElements.Board;

public class MoveResult {

    private MoveType type;
    private Piece piece;

    public Piece getPiece() {
        return piece;
    }

    public MoveType getType() {
        return type;
    }

    public MoveResult(MoveType type){
        this.type=type;
    }
}
