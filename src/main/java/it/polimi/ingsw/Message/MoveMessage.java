package it.polimi.ingsw.Message;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.PossiblePhases;

public class MoveMessage extends GameMessage {
    private final int[][] constructorMatrix;

    public MoveMessage(String code, Player currentPlayer, PossiblePhases currentPhase, int[][] matrix) {
        super(code, currentPlayer, currentPhase);
        this.constructorMatrix = matrix;
    }

    public int[][] getConstructorMatrix(){
        return this.constructorMatrix;
    }
}
