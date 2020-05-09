package it.polimi.ingsw.Message.ServerMessage;

import it.polimi.ingsw.Controller.MiniController.ServerMoveMiniController;

public class PlaceFirstConstructorMessage extends ServerMessage {

    public PlaceFirstConstructorMessage(){
        this.miniController= new ServerMoveMiniController();
    }

    @Override
    public String getMessage() {
        return "Choose where to place your constructor typing the position (ex. 2,2)\n";
    }
}
