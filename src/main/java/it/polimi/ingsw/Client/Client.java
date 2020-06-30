package it.polimi.ingsw.Client;



import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.ServerMessage.ServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * this abstract client class has been organized to made transparent all messages between client and server,
 * independently which type of implementation is used in the client (CLI, GUI)
 */
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

    /**
     * Method used to check if the Client is still active
     *
     * @return boolean for active
     */
    public synchronized boolean isActive(){
        return active;
    }

    public synchronized void setActive(boolean active){
        this.active=active;
    }

    /**
     * This method initialize a new thread that will be used to process all messages from the Server using all
     * the other methods called manage*something*
     *
     * @param socketIn the input stream from the socket
     *
     * @return The actual thread
     */
    public abstract Thread asyncReadFromSocket(final ObjectInputStream socketIn);

    /**
     * Method used to process every string sent by the Server
     *
     * @param input String from the Sever
     */
    public abstract void manageString(String input);

    /**
     * Method used to process every ServerMessage sent by the Server
     *
     * @param inputObject ServerMessage from the Sever
     */
    public abstract void manageServerMessage(ServerMessage inputObject);

    /**
     * Method used to process every GameMessage sent by the Server
     *
     * @param inputObject GameMessage from the Sever
     */
    public abstract void manageGameMessage(GameMessage inputObject);

    public abstract void run() throws IOException;
}
