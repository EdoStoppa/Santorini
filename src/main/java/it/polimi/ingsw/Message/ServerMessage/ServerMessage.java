package it.polimi.ingsw.Message.ServerMessage;

import it.polimi.ingsw.Controller.MiniController.MiniController;

import java.io.Serializable;

public abstract class ServerMessage implements Serializable {
    protected MiniController miniController;

    public MiniController getMiniController() {
        return miniController;
    }

}
