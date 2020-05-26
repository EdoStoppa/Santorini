package it.polimi.ingsw.Message.TileToShowMessages;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Controller.MiniController.StandardMiniController;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Model.PossiblePhases;

import java.util.List;

public class StandardTileMessage extends TileToShowMessage {
    public StandardTileMessage(String player, PossiblePhases phase, List<Position> list) {
        super(player, phase, list);
        miniController = new StandardMiniController();
    }

    @Override
    public void updateGUI(PlaySpace playSpace) {

    }
}
