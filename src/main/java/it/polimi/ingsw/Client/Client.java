package it.polimi.ingsw.Client;



import java.io.IOException;
import java.io.ObjectInputStream;

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

    public abstract void run() throws IOException;
}
