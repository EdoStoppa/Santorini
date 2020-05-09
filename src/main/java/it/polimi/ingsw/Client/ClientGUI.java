package it.polimi.ingsw.Client;

import it.polimi.ingsw.Controller.MiniController.MiniController;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class ClientGUI extends Client{
    private MiniController miniController;

    public ClientGUI(String ip, int port) {
        super(ip, port);
    }

    @Override
    public Thread asyncReadFromSocket(ObjectInputStream socketIn) {
        return null;
    }

    @Override
    public Thread asyncWriteToSocket(Scanner stdin, PrintWriter socketOut) {
        return null;
    }

    public void setMiniController(MiniController miniController) {
        this.miniController = miniController;
    }
}
