package it.polimi.ingsw.Message.MoveMessages;

import it.polimi.ingsw.Client.GraphicElements.Board.BoardScene;
import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Message.HelpMessage;
import it.polimi.ingsw.Model.PossiblePhases;

/**
 * This message is the standard Move message, generated after a move from any player.
 */
public class StandardMoveMessage extends MoveMessage {

    public StandardMoveMessage(String currentPlayer, PossiblePhases currentPhase, int[][] matrix) {
        super(currentPlayer, currentPhase, matrix);
    }

    @Override
    public void autoSetMessage(boolean isMyTurn, boolean isCLI) {
        String text;

        if(isMyTurn){
            text = HelpMessage.endedPhase;
        } else {
            text = getIdPlayer() + " moved to " + getMessage();
        }

        setMessage(text);
    }

    @Override
    public void updateGUI(PlaySpace playSpace) {
        BoardScene.setInit(false);
        playSpace.updateConstructorGUI(getConstructorMatrix());
    }
}
