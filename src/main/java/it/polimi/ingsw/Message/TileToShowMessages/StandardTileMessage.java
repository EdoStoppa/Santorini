package it.polimi.ingsw.Message.TileToShowMessages;


import it.polimi.ingsw.Controller.MiniController.StandardMiniController;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Model.PossiblePhases;

import java.util.List;

/**
 * The standard TileToShow message, it just carries a standard MiniController and a standard list of Positions
 */
public class StandardTileMessage extends TileToShowMessage {
    public StandardTileMessage(String player, PossiblePhases phase, List<Position> list) {
        super(player, phase, list);
        miniController = new StandardMiniController();
    }

}
