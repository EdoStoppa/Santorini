package it.polimi.ingsw.Message.MoveMessages;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Model.PossiblePhases;

import java.io.Serializable;

public abstract class MoveMessage extends GameMessage{
    private final int[][] constructorMatrix;

    public MoveMessage(String currentPlayer, PossiblePhases currentPhase, int[][] matrix) {
        super(currentPlayer, currentPhase);
        this.constructorMatrix = matrix;
    }

    public int[][] getConstructorMatrix(){
        return this.constructorMatrix;
    }

    /*@Override
    public void updatePlaySpace(PlaySpace playSpace, boolean isMyTurn) {
        playSpace.setConstructorMatrix(getConstructorMatrix());

        playSpace.reset();
    }*/
}
