package it.polimi.ingsw.Message.ServerMessage;

import it.polimi.ingsw.Controller.MiniController.MiniController;

import java.io.Serializable;

public abstract class ServerMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    protected MiniController miniController;

    public abstract MiniController getMiniController();

    public abstract String getMessage();

    public abstract void buildScene();
}
