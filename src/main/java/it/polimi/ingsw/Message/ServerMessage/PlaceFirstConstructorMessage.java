package it.polimi.ingsw.Message.ServerMessage;

import it.polimi.ingsw.Controller.MiniController.MiniController;
import it.polimi.ingsw.Controller.MiniController.ServerMoveMiniController;

public class PlaceFirstConstructorMessage extends ServerMessage {
    boolean isFirst;

    public PlaceFirstConstructorMessage(boolean isFirst){
        this.miniController= new ServerMoveMiniController();
        this.isFirst = isFirst;
    }

    public boolean isFirst(){
        return isFirst;
    }

    @Override
    public MiniController getMiniController() {
        return new ServerMoveMiniController();
    }

    @Override
    public String getMessage() {
        return "Choose where to place your constructor typing the position (ex. 2,3)";
    }

    @Override
    public void buildScene() {

    }
}
