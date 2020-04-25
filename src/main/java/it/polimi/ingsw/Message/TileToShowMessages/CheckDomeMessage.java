package it.polimi.ingsw.Message.TileToShowMessages;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Model.PossiblePhases;

import java.util.List;

public class CheckDomeMessage  extends TileToShowMessage {
    public CheckDomeMessage(String player, PossiblePhases phase, List<Position> list) {
        super(player, phase, list);
    }

    @Override
    public void autoSetMessage(boolean isMyTurn, boolean isCLI) {
        String text;

        if(isMyTurn){
            text = getPhase().toString(true);
        } else {
            text = getIdPlayer() + getPhase().toString(false);
        }

        setMessage(text);
    }

    @Override
    public void updatePlaySpace(PlaySpace playSpace, boolean isMyTurn) {

    }
}
