package it.polimi.ingsw.Client;



import it.polimi.ingsw.Message.ServerMessage.ChosenGodMessage;
import it.polimi.ingsw.Message.ServerMessage.OrderGameMessage;
import it.polimi.ingsw.Message.ServerMessage.PickGodMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public abstract class Client {
    protected String ip;
    protected int port;
    private boolean active= true;
    protected PlaySpace playSpace;

    public Client(String ip, int port){
        this.ip=ip;
        this.port=port;
        this.playSpace = new PlaySpace();
    }

    public synchronized boolean isActive(){
        return active;
    }

    public synchronized void setActive(boolean active){
        this.active=active;
    }

    public abstract Thread asyncReadFromSocket(final ObjectInputStream socketIn);

    public abstract Thread asyncWriteToSocket(final Scanner stdin, final PrintWriter socketOut);

    public abstract void run() throws IOException;
}
