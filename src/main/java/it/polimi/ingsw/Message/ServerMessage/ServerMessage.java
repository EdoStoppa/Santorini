package it.polimi.ingsw.Message.ServerMessage;

import it.polimi.ingsw.Controller.MiniController.MiniController;

public abstract class ServerMessage {
    protected MiniController miniController;

    public MiniController getMiniController() {
        return miniController;
    }
    
}
