package it.polimi.ingsw.Message.MoveMessages;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Message.HelpMessage;
import it.polimi.ingsw.Model.PossiblePhases;

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


}
