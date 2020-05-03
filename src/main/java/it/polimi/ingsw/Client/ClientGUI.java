package it.polimi.ingsw.Client;

import it.polimi.ingsw.Controller.MiniController.MiniController;

public class ClientGUI extends Client{
    private MiniController miniController;

    public ClientGUI(String ip, int port) {
        super(ip, port);
    }

    public void setMiniController(MiniController miniController) {
        this.miniController = miniController;
    }
}
