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
    private String ip;
    private int port;
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

    public void run() throws IOException{
        Socket socket= new Socket(ip,port);
        System.out.println("connection established");
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        Scanner stdin= new Scanner(System.in);

        try {
            Thread t0 =asyncReadFromSocket(socketIn);
            Thread t1=asyncWriteToSocket(stdin, socketOut);
            t0.join();
            t1.join();
        }catch (InterruptedException | NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }
}
