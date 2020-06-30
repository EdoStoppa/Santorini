package it.polimi.ingsw.Message.ServerMessage;


import it.polimi.ingsw.Client.GraphicElements.Board.BoardScene;

import it.polimi.ingsw.Controller.MiniController.MiniController;
import it.polimi.ingsw.Controller.MiniController.ServerMoveMiniController;

/**
 * This message is generated whenever the player has to place one of his/hers constructors on the board.
 */
public class PlaceFirstConstructorMessage extends ServerMessage {
    boolean isFirst;

    public PlaceFirstConstructorMessage(boolean isFirst){
        this.miniController= new ServerMoveMiniController();
        this.isFirst = isFirst;
    }

    /**
     * Method used to know if the player is the first to actually place a constructor on the board (it's used only in CLI)
     *
     * @return
     */
    public boolean isFirst(){
        return isFirst;
    }

    @Override
    public MiniController getMiniController() {
        return new ServerMoveMiniController();
    }

    @Override
    public String getMessage() {
        return "Choose where to place your constructor";
    }

    @Override
    public void buildScene() {
        BoardScene.setInit(true);
        BoardScene.setYourTurn(true);
    }
}
