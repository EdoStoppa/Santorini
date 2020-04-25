package it.polimi.ingsw.Message.TileToShowMessages;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Message.HelpMessage;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Model.PossiblePhases;

import java.util.List;

public class MoreTileToCheckMessage extends TileToShowMessage {
    private final List<Position> specialList;

    public MoreTileToCheckMessage(String player, PossiblePhases phase, List<Position> list, List<Position> specialList) {
        super(player, phase, list);
        this.specialList = specialList;
    }

    public List<Position> getSpecialList() {
        return specialList;
    }

    @Override
    public void autoSetMessage(boolean isMyTurn, boolean isCLI) {
        String text;

        if(isMyTurn){
            text = getPhase().toString(true) + (isCLI? HelpMessage.endWithCLI : HelpMessage.endWithGUI);
        } else {
            text = getIdPlayer() + getPhase().toString(false);
        }

        setMessage(text);
    }

    @Override
    public void updatePlaySpace(PlaySpace playSpace, boolean isMyTurn) {

    }
}
