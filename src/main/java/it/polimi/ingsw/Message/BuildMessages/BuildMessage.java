package it.polimi.ingsw.Message.BuildMessages;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Model.PossiblePhases;

import java.io.Serializable;

/**
 * This abstract class represents all messages created in respond to a build choice performed by the player.
 *
 * This will carries a new buildingMatrix representing the new state of the board (regarding all construction levels)
 */
public abstract class BuildMessage extends GameMessage implements Serializable{
    private static final long serialVersionUID = 1L;
    private final int[][] buildingMatrix;

    public BuildMessage(String currentPlayer, PossiblePhases currentPhase, int[][] matrix) {
        super(currentPlayer, currentPhase);
        this.buildingMatrix = matrix;
    }

    public int[][] getBuildingMatrix(){
        return this.buildingMatrix;
    }

    @Override
    public void updatePlaySpace(PlaySpace playSpace) {
        playSpace.setBuildingMatrix(getBuildingMatrix());
    }

}
