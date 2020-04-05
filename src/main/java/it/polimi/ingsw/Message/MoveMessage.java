package it.polimi.ingsw.Message;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.PossiblePhases;

public class MoveMessage extends GameMessage {
    private final PossiblePhases currentPhase;
    private final int[][] constructorMatrix;

    public MoveMessage(String message, Player currentPlayer, PossiblePhases currentPhase, int[][] matrix) {
        super(message, currentPlayer);
        this.currentPhase = currentPhase;
        this.constructorMatrix = matrix;
    }

    public int[][] getConstructorMatrix(){
        return this.constructorMatrix;
    }

    public PossiblePhases getCurrentPhase(){
        return this.currentPhase;
    }
}
