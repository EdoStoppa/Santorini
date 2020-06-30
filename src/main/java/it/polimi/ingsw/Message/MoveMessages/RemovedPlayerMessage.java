package it.polimi.ingsw.Message.MoveMessages;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Model.PossiblePhases;

/**
 * This message is generated when one of the player loses (no constructor can complete an entire turn).
 * It carries the new constructorMatrix without the loser's constructors
 */
public class RemovedPlayerMessage extends MoveMessage {
    public RemovedPlayerMessage(String currentPlayer, PossiblePhases currentPhase, int[][] matrix) {
        super(currentPlayer, currentPhase, matrix);
    }

    @Override
    public void autoSetMessage(boolean isMyTurn, boolean isCLI) {
        String text;

        if(isMyTurn){
            text = "You lose... :(";
        } else {
            text = getIdPlayer() + " lost";
        }

        setMessage(text);
    }

    @Override
    public void updateGUI(PlaySpace playSpace) {
        playSpace.removeConstructorGUI(getConstructorMatrix());
    }

}
