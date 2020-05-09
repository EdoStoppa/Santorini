package it.polimi.ingsw.Message.TileToShowMessages;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Controller.MiniController.CheckDomeMiniController;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Model.PossiblePhases;

import java.util.List;

public class CheckDomeMessage  extends TileToShowMessage {
    public CheckDomeMessage(String player, PossiblePhases phase, List<Position> list) {
        super(player, phase, list);
        this.miniController = new CheckDomeMiniController();
    }
}
