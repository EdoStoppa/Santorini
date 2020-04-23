package it.polimi.ingsw.Message;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Model.PossiblePhases;
import it.polimi.ingsw.View.View;

import java.util.List;

public class SpecialActionMessage extends GameMessage {
    private final List<Position> tileToShow;
    private final List<Position> specialTile;

    public SpecialActionMessage(String code, String player, PossiblePhases phase, List<Position> tileToShow, List<Position> specialTile) {
        super(code, player, phase);

        this.tileToShow = tileToShow;
        this.specialTile = specialTile;
    }

    public List<Position> getTileToShow(){
        return this.tileToShow;
    }
    public List<Position> getSpecialTile(){
        return this.specialTile;
    }

    @Override
    public void autoSetMessage(boolean isMyTurn, boolean isCLI) {
        String text;

        if(isMyTurn){
            text = text = getPhase().toString(true) + (isCLI? HelpMessage.endWithCLI : HelpMessage.endWithGUI);
        } else {
            text = getIdPlayer() + getPhase().toString(false);
        }

        setMessage(text);
    }
}
