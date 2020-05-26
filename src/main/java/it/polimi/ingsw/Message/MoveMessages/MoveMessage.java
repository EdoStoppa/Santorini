package it.polimi.ingsw.Message.MoveMessages;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Model.PossiblePhases;

import java.io.Serializable;

public abstract class MoveMessage extends GameMessage implements Serializable{
    private static final long serialVersionUID = 1L;
    private final int[][] constructorMatrix;

    public MoveMessage(String currentPlayer, PossiblePhases currentPhase, int[][] matrix) {
        super(currentPlayer, currentPhase);
        this.constructorMatrix = matrix;
    }

    public int[][] getConstructorMatrix(){
        return this.constructorMatrix;
    }


    public void updatePlaySpace(PlaySpace playSpace) {
        playSpace.setConstructorMatrix(getConstructorMatrix());
    }

    @Override
    public void updateGUI(PlaySpace playSpace){
        updateConstructor(playSpace);
    }

    public void updateConstructor(PlaySpace playSpace){
        playSpace.updateConstructorGUI(getConstructorMatrix());
    }


}
