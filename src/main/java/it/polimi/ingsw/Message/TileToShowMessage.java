package it.polimi.ingsw.Message;

import it.polimi.ingsw.Client.PlaySpace;
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
        String text;

        if(isMyTurn){
            switch(getCode()){
                case "standard":
                case "checkDome":
                case "noPower":
                    text = getPhase().toString(true);
                case "canEnd":
                    text = getPhase().toString(true) + (isCLI? HelpMessage.endWithCLI : HelpMessage.endWithGUI);
                default:
                    text = "You broke the game!!! (Code in tileToShow is unknown...)";
            }

        } else {
            text = getIdPlayer() + getPhase().toString(false);
        }

        setMessage(text);
    }

    @Override
    public void update(PlaySpace playSpace, boolean isMyTurn) {

    }

}
