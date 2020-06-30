package it.polimi.ingsw.Message.BuildMessages;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Message.HelpMessage;
import it.polimi.ingsw.Model.PossiblePhases;

/**
 * This message is the standard message created every time the player place a construction
 */
public class StandardBuildMessage extends BuildMessage {
    public StandardBuildMessage(String currentPlayer, PossiblePhases currentPhase, int[][] matrix) {
        super(currentPlayer, currentPhase, matrix);
    }

    @Override
    public void autoSetMessage(boolean isMyTurn, boolean isCLI) {
        String text;

        if(isMyTurn){
            text = HelpMessage.endedPhase;
        } else {
            text = getIdPlayer() + " built on position " + getMessage();
        }

        setMessage(text);
    }

    @Override
    public void updateGUI(PlaySpace playSpace) {
        playSpace.updateBuildingGUI(getBuildingMatrix(),false);
        playSpace.setSpecial(false);
    }


}
