package it.polimi.ingsw.Message.TileToShowMessages;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Controller.MiniController.CheckDomeMiniController;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Model.PossiblePhases;

import java.util.List;

/**
 * This message is created when the Player have to decide if the Building that will be positioned is going to be a Dome
 * or a normal Construction. It carries a CheckDomeMiniController
 */
public class CheckDomeMessage  extends TileToShowMessage {
    public CheckDomeMessage(String player, PossiblePhases phase, List<Position> list) {
        super(player, phase, list);
        this.miniController = new CheckDomeMiniController();
    }

    @Override
    public void updatePlaySpace(PlaySpace playSpace)    {
        playSpace.setTileToShow(getTileToShow());
    }

    @Override
    public void updateGUI(PlaySpace playSpace) {
        playSpace.tileToShowGUI(getTileToShow());
        playSpace.setCheckDome(true);
    }
}
