package it.polimi.ingsw.Message.TileToShowMessages;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Model.PossiblePhases;

import java.util.List;

public abstract class TileToShowMessage extends GameMessage {
    private final List<Position> tileToShow;

    public TileToShowMessage(String player, PossiblePhases phase, List<Position> list) {
        super(player, phase);
        this.tileToShow = list;
    }

    public List<Position> getTileToShow() {
        return this.tileToShow;
    }

    /*@Override
    public void autoSetMessage(boolean isMyTurn, boolean isCLI) {
        String text;
        text = getPhase().toString(isMyTurn);

        if(getCode().equals("canEnd") && isMyTurn){
            text = text + (isCLI? HelpMessage.endWithCLI : HelpMessage.endWithGUI);
        }

        setMessage(text);
    }

    @Override
    public void updatePlaySpace(PlaySpace playSpace, boolean isMyTurn) {

    }*/
}
