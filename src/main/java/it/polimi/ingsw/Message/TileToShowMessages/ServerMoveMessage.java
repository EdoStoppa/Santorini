package it.polimi.ingsw.Message.TileToShowMessages;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Message.HelpMessage;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Model.PossiblePhases;

import java.util.List;

public class ServerMoveMessage extends TileToShowMessage{
    private final int[][] matrix;

    public ServerMoveMessage(String player, PossiblePhases phase, List<Position> list, int[][] matrix) {
        super(player, phase, list);
        this.matrix = matrix;
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
    public void updatePlaySpace(PlaySpace playSpace, boolean isMyTurn) {

    }
}
