package it.polimi.ingsw.Message.MoveMessages;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Model.PossiblePhases;

public class ServerMoveMessage extends MoveMessage {
    public ServerMoveMessage(String player, PossiblePhases phase, int[][] matrix) {
        super(player, phase, matrix);
    }

    @Override
    public void autoSetMessage(boolean isMyTurn, boolean isCLI) {
        String text;

        if(isMyTurn){
            text = getPhase().toString(true) + getMessage();
        } else {
            text = getIdPlayer() + getPhase().toString(false) + getMessage();
        }

        setMessage(text);
    }

    @Override
    public void updateGUI(PlaySpace playSpace) {
        playSpace.updateConstructorGUI(getConstructorMatrix());
    }
}
