package it.polimi.ingsw.Message;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Model.PossiblePhases;

import java.util.List;

public class TileToShowMessage extends GameMessage {
    private final PossiblePhases currentPhase;
    private final List<Position> tileToShow;

    public TileToShowMessage(String message, Player player, PossiblePhases phase, List<Position> list) {
        super(message, player);
        this.currentPhase = phase;
        this.tileToShow = list;
    }


    public PossiblePhases getCurrentPhase() {
        return this.currentPhase;
    }

    public List<Position> getTileToShow() {
        return this.tileToShow;
    }
}
