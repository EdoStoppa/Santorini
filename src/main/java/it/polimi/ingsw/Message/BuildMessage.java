package it.polimi.ingsw.Message;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.PossiblePhases;

public class BuildMessage  extends GameMessage {
    private final PossiblePhases currentPhase;
    private final int[][] buildingMatrix;

    public BuildMessage(String message, Player currentPlayer, PossiblePhases currentPhase, int[][] matrix) {
        super(message, currentPlayer);
        this.currentPhase = currentPhase;
        this.buildingMatrix = matrix;
    }

    public int[][] getConstructorMatrix(){
        return this.buildingMatrix;
    }

    public PossiblePhases getCurrentPhase(){
        return this.currentPhase;
    }
}
