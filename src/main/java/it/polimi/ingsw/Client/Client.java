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

public class Client {
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

    public  Thread asyncReadFromSocket(final ObjectInputStream socketIn){
        Thread t= new Thread(() -> {
            try {
                while(isActive()){
                    Object inputObject = socketIn.readObject();
                    if(inputObject instanceof String){
                        System.out.println((String)inputObject);
                    } else if (inputObject instanceof PickGodMessage){
                        System.out.println("choose god for your game format of input [1,2]");
                        for(int i=0;i<((PickGodMessage) inputObject).getSize();i++)
                        System.out.println(i+") "+((PickGodMessage) inputObject).GetGod(i).getGodName()+"  "+
                        ((PickGodMessage) inputObject).GetGod(i).getGodSubtitle()+"\n"+
                        ((PickGodMessage) inputObject).GetGod(i).getGodPower()+"\n\n");
                    } else if (inputObject instanceof ChosenGodMessage){
                        System.out.println("choose your god and enter name of god [apollo]");
                        for(int i=0;i<((ChosenGodMessage) inputObject).getSize();i++){
                            System.out.println(i+") "+((PickGodMessage) inputObject).GetGod(i).getGodName()+"  "+
                                    ((PickGodMessage) inputObject).GetGod(i).getGodSubtitle()+"\n"+
                                    ((PickGodMessage) inputObject).GetGod(i).getGodPower()+"\n\n");
                        }
                    }else if (inputObject instanceof OrderGameMessage){
                        System.out.println("choose the player who starts the game");
                        for (int i=0;i<((OrderGameMessage) inputObject).getSize();i++){
                            System.out.println(((OrderGameMessage) inputObject).getPlayerlist().get(i));
                        }
                    }
                    {
                        throw new  IllegalArgumentException();
                    }
                }
            }catch (Exception e){
                setActive(false);
            }

        });
    t.start();
    return t;
    }

    public Thread asyncWriteToSocket(final Scanner stdin, final PrintWriter socketOut){
        Thread t=new Thread(() -> {
            try{
                while (isActive()){
                    String inputLine = stdin.nextLine();
                    socketOut.println(inputLine);
                    socketOut.flush();
                }
            }catch (Exception e){
                setActive(false);
            }
        });
        t.start();
        return t;
    }

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
