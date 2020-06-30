package it.polimi.ingsw.Message.TileToShowMessages;

import it.polimi.ingsw.Client.GraphicElements.Board.BoardScene;
import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Controller.MiniController.CanEndMiniController;
import it.polimi.ingsw.Message.HelpMessage;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Model.PossiblePhases;

import java.util.List;

/**
 * This message is sent during a phase that can be ended by the Player (thanks to his/her God's power), so carries a normal
 * list of positions and a CanEndMiniController
 */
public class CanEndTileMessage extends TileToShowMessage {
    public CanEndTileMessage(String player, PossiblePhases phase, List<Position> list) {
        super(player, phase, list);
        this.miniController = new CanEndMiniController();
    }

    @Override
    public void autoSetMessage(boolean isMyTurn, boolean isCLI) {
        String text;

        if(isMyTurn){
            text = getPhase().toString(true) + (isCLI? HelpMessage.endWithCLI : HelpMessage.endWithGUI);
        } else {
            text = getIdPlayer() + getPhase().toString(false);
        }


        setMessage(text);
    }

    @Override
    public void updatePlaySpace(PlaySpace playSpace)    {
        playSpace.setTileToShow(getTileToShow());
    }

    @Override
    public void updateGUI(PlaySpace playSpace) {
        playSpace.tileToShowGUI(getTileToShow());
        playSpace.setSpecial(true);
        BoardScene.setEndPhaseButton(true);
    }
}
