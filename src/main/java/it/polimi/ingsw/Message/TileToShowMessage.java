package it.polimi.ingsw.Message;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Model.PossiblePhases;

import java.util.List;

public class TileToShowMessage extends GameMessage {
    private final List<Position> tileToShow;

    public TileToShowMessage(String code, String player, PossiblePhases phase, List<Position> list) {
        super(code, player, phase);
        this.tileToShow = list;
    }

    public List<Position> getTileToShow() {
        return this.tileToShow;
    }

    @Override
    public void autoSetMessage(boolean isMyTurn, boolean isCLI) {

    }
}
