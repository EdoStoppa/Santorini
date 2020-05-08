package it.polimi.ingsw.Message.ServerMessage;

import it.polimi.ingsw.Controller.MiniController.MiniController;

import java.io.Serializable;

public abstract class ServerMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    protected MiniController miniController;

    public MiniController getMiniController() {
        return miniController;
    }

}
