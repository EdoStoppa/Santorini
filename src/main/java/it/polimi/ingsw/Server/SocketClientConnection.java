package it.polimi.ingsw.Server;

import it.polimi.ingsw.Observer.Observable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SocketClientConnection extends Observable<String> implements ClientConnection,Runnable {


    private Socket socket;
    private ObjectOutputStream out;
    private Server server;
    private boolean active=true;

    public SocketClientConnection(Socket socket,Server server){
        this.server=server;
        this.socket=socket;
    }

    private synchronized boolean isActive(){
        return active;
    }

    private synchronized void send(Object message){
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    private void close() {
        closeConnection();
        System.out.println("Deregistering client...");
        server.deregisterConnection2P(this);
        System.out.println("done");
    }



    @Override
    public void run() {
        Scanner in;
        String name;
        Integer gameMode;
        try {
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            send("welcome!\n 2 or 3 player mode");
            gameMode=in.nextInt();
            send(" Enter your name:");
            String read = in.nextLine();
            name = read;
            if (gameMode == 2) {
                server.lobby2P(this, name);
            } else {
                server.lobby3P(this, name);
            }
            while (isActive()) {
                read = in.nextLine();
                notify(read);
            }

        }catch (IOException | NoSuchElementException e){
            System.err.println("Error!" + e.getMessage());
        }finally {
            close();
        }
    }

    @Override
    public void closeConnection() {
        send("Connection closed!");
        try {
            socket.close();
        } catch (IOException e){
            System.err.println("Error when closing socket");
        }
        active=false;
    }

    @Override
    public void asyncSend(final Object message) {
        Thread thread = new Thread() {
            public void run() {
                try {
                    out.reset();
                    out.writeObject(message);
                    out.flush();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        };
        thread.start();
    }

    @Override
    public ArrayList<Integer> ChooseGod(int player){
        ArrayList<Integer> pickGod=new ArrayList<>();
        Integer i=0, pick=0;
        try{
            Scanner in= new Scanner(socket.getInputStream());
            this.asyncSend("chose god from this list:\n");
            while (i<player){
                pick=in.nextInt();
                pickGod.add(pick);
                i++;
            }
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        return pickGod;
    }

    @Override
    public ArrayList<Integer> PickGod(ClientConnection opponent,ArrayList<Integer> pickPool){
        int pick;
        try{
            Scanner in= new Scanner(socket.getInputStream());
            opponent.asyncSend("choose your god:"+ pickPool.get(0)+"or"+ pickPool.get(1));
            pick=in.nextInt();
            if(pick==pickPool.get(0)) {
                pickPool.remove(0);
                pickPool.add(pick);
            }

        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        return pickPool;
    }

    @Override
    public ArrayList<Integer> PickGod3P(ClientConnection opponent1,ClientConnection opponent2, ArrayList<Integer> pickPool){
        int pick1,pick2;
        try{
            Scanner in= new Scanner(socket.getInputStream());
            opponent1.asyncSend("choose your god:"+ pickPool.get(0)+"or"+ pickPool.get(1)+"or"+pickPool.get(2));
            pick1=in.nextInt();
            if(pick1==pickPool.get(0)) {
                pickPool.remove(0);
            } else if(pick1==pickPool.get(1)){
                pickPool.remove(1);
            }else {
                pickPool.remove(2);
            }
            opponent2.asyncSend("choose your god"+pickPool.get(0)+"or"+pickPool.get(1));
            pick2=in.nextInt();
            if(pick2==pickPool.get(0)){
                pickPool.remove(0);
            }else{
                pickPool.remove(1);
            }
            pickPool.add(pick1);
            pickPool.add(pick2);
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        return pickPool;
    }




}
