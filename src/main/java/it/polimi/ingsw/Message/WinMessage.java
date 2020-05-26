package it.polimi.ingsw.Message;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Model.PossiblePhases;

public class WinMessage extends GameMessage {

    public WinMessage(String player, PossiblePhases phase)   {
        super(player, null);
    }

    @Override
    public void autoSetMessage(boolean isMyTurn, boolean isCLI) {
        String text;

        if(isMyTurn)    {
            text = "YOU WON!!!";
        }
        else    {
            text = getIdPlayer() + " won :(";
        }

        setMessage(text);
    }

    @Override
    public void updatePlaySpace(PlaySpace playSpace) {

    }

    @Override
    public void updateGUI(PlaySpace playSpace) {

    }
}
