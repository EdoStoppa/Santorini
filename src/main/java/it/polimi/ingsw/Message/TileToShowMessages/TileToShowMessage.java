package it.polimi.ingsw.Message.TileToShowMessages;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Controller.MiniController.MiniController;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Model.PossiblePhases;

import java.util.List;

public abstract class TileToShowMessage extends GameMessage {
    private final List<Position> tileToShow;
    protected MiniController miniController;

    public TileToShowMessage(String player, PossiblePhases phase, List<Position> list) {
        super(player, phase);
        this.tileToShow = list;
    }

    public List<Position> getTileToShow() {
        return this.tileToShow;
    }

    public void setMiniController(MiniController mc){
        this.miniController = mc;
    }
}
