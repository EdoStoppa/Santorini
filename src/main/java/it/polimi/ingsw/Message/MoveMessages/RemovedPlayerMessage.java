package it.polimi.ingsw.Message.MoveMessages;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Model.PossiblePhases;

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
}
