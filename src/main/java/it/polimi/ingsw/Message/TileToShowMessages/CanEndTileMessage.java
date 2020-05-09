package it.polimi.ingsw.Message.TileToShowMessages;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Controller.MiniController.CanEndMiniController;
import it.polimi.ingsw.Message.HelpMessage;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Model.PossiblePhases;

import java.util.List;

public class CanEndTileMessage extends TileToShowMessage {
    public CanEndTileMessage(String player, PossiblePhases phase, List<Position> list) {
        super(player, phase, list);
        this.miniController = new CanEndMiniController();
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
}
