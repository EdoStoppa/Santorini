package it.polimi.ingsw.Message.ServerMessage;

import it.polimi.ingsw.Controller.MiniController.ServerMoveMiniController;

public class PlaceFirstConstructorMessage extends ServerMessage {

    public PlaceFirstConstructorMessage(){
        this.miniController= new ServerMoveMiniController();
    }


}
