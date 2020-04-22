package it.polimi.ingsw.Message;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.PossiblePhases;

public class BuildMessage  extends GameMessage {
    private final int[][] buildingMatrix;

    public BuildMessage(String code, Player currentPlayer, PossiblePhases currentPhase, int[][] matrix) {
        super(code, currentPlayer, currentPhase);
        this.buildingMatrix = matrix;
    }

    public int[][] getConstructorMatrix(){
        return this.buildingMatrix;
    }
}
