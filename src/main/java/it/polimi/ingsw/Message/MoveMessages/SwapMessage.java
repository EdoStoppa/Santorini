package it.polimi.ingsw.Message.MoveMessages;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Message.HelpMessage;
import it.polimi.ingsw.Model.PossiblePhases;

/**
 * This message is generated when a constructor swap with another one (generally done by Apollo)
 */
public class SwapMessage extends MoveMessage {

    public SwapMessage(String currentPlayer, PossiblePhases currentPhase, int[][] matrix) {
        super(currentPlayer, currentPhase, matrix);
    }

    @Override
    public void autoSetMessage(boolean isMyTurn, boolean isCLI) {
        String text;

        if(isMyTurn){
            text = HelpMessage.endedPhase;
        } else {
            text = getIdPlayer() + " swapped his constructor with constructor on position: " + getMessage();
        }

        setMessage(text);
    }

    @Override
    public void updateGUI(PlaySpace playSpace) {
        playSpace.swapConstructorGUI(getConstructorMatrix());
    }
}
