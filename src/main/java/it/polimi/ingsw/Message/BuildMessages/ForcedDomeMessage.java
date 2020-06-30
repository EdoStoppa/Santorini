package it.polimi.ingsw.Message.BuildMessages;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Message.HelpMessage;
import it.polimi.ingsw.Model.PossiblePhases;

/**
 * This message is created when a Dome is placed on the board forcefully.
 */
public class ForcedDomeMessage extends BuildMessage {
    public ForcedDomeMessage(String currentPlayer, PossiblePhases currentPhase, int[][] matrix) {
        super(currentPlayer, currentPhase, matrix);
    }

    @Override
    public void autoSetMessage(boolean isMyTurn, boolean isCLI) {
        String text;

        if(isMyTurn){
            text = HelpMessage.endedPhase;
        } else {
            text = getIdPlayer() + ", thanks to his power, built a dome on position " + getMessage();
        }

        setMessage(text);
    }

    @Override
    public void updateGUI(PlaySpace playSpace) {
        playSpace.updateBuildingGUI(getBuildingMatrix(),true);
    }


}
