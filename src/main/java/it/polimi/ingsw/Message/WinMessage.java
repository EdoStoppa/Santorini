package it.polimi.ingsw.Message;

import it.polimi.ingsw.Client.GraphicElements.SceneBuilder;
import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Model.PossiblePhases;

/**
 * This Message is created when a victory occur.
 *
 * It is also created a second time (with all attributes initialized to null) to tell the Server
 * to close the match and disconnect everyone
 */
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
