package it.polimi.ingsw.Message;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Model.PossiblePhases;
import it.polimi.ingsw.View.View;

import java.util.List;

public class SpecialActionMessage extends GameMessage {
    private final List<Position> tileToShow;
    private final List<Position> specialTile;
    private final PossiblePhases phase;

    public SpecialActionMessage(String message, Player player, PossiblePhases phase, List<Position> tileToShow, List<Position> specialTile) {
        super(message, player);

        this.phase = phase;
        this.tileToShow = tileToShow;
        this.specialTile = specialTile;
    }

    public PossiblePhases getPhase(){
        return this.phase;
    }
    public List<Position> getTileToShow(){
        return this.tileToShow;
    }
    public List<Position> getSpecialTile(){
        return this.specialTile;
    }

}
