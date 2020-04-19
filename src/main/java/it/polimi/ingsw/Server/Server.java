package it.polimi.ingsw.Server;

import it.polimi.ingsw.Client.Client;
import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.View.View;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT=12333;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private Map<String, ClientConnection> waitingConnection2P = new HashMap<>();
    private Map<String, ClientConnection> waitingConnection3P = new HashMap<>();
    private Map<ClientConnection,ClientConnection> playingConnection2P = new HashMap<>();
    private Map<ClientConnection,ClientConnection> playingConnection3P = new HashMap<>();

    public synchronized void deregisterConnection2P(ClientConnection c){
        ClientConnection opponent=playingConnection2P.get(c);
        if (opponent!= null){
            opponent.closeConnection();
        }
        playingConnection2P.remove(c);
        playingConnection2P.remove(opponent);
        Iterator<String> iterator= waitingConnection2P.keySet().iterator();
        while(iterator.hasNext()){
            if(waitingConnection2P.get(iterator.next())==c){
                iterator.remove();
            }
        }
    }

    public synchronized void lobby2P(ClientConnection c,String name){
        ArrayList<Integer> ChosenGodPool;
        ArrayList<Integer> ChosenGod;
        waitingConnection2P.put(name,c);
        if (waitingConnection2P.size() == 2){
            List<String> keys= new ArrayList<>(waitingConnection2P.keySet());
            ClientConnection c1= waitingConnection2P.get(keys.get(0));
            ClientConnection c2= waitingConnection2P.get(keys.get(1));
            Random random= new Random();
            int ChoseGodLike= random.nextInt(2)+1;
            if (ChoseGodLike==1){
                ChosenGodPool=c1.ChooseGod(2);
                ChosenGod=c1.PickGod(c2,ChosenGodPool);
            } else {
                ChosenGodPool=c2.ChooseGod(2);
                ChosenGod=c2.PickGod(c1,ChosenGodPool);
            }

            //create player, view model e controller
            //add observer alle view
            playingConnection2P.put(c1,c2);
            playingConnection2P.put(c2,c1);
            waitingConnection2P.clear();
            //first message to player view
        }

    }

    public synchronized void lobby3P(ClientConnection c,String name){
        ArrayList<Integer> ChosenGodPool;
        ArrayList<Integer> ChosenGod;
        waitingConnection3P.put(name,c);
        if(waitingConnection3P.size()==3){
            List<String> keys= new ArrayList<>(waitingConnection3P.keySet());
            ClientConnection c1= waitingConnection3P.get(keys.get(0));
            ClientConnection c2= waitingConnection3P.get(keys.get(1));
            ClientConnection c3=waitingConnection3P.get(keys.get(2));
            Random random= new Random();
            int ChoseGodLike= random.nextInt(3)+1;
            if (ChoseGodLike ==1){
                ChosenGodPool=c1.ChooseGod(3);
                ChosenGod=c1.PickGod3P(c2,c3,ChosenGodPool);
            }else if(ChoseGodLike==2){
                ChosenGodPool=c2.ChooseGod(3);
                ChosenGod= c2.PickGod3P(c3,c1,ChosenGodPool);
            }else {
                ChosenGodPool=c3.ChooseGod(3);
                ChosenGod= c3.PickGod3P(c1,c2,ChosenGodPool);
            }
            //create player, view model e controller
            //add observer alle view
            playingConnection3P.put(c1,c2);
            playingConnection3P.put(c1,c3);
            playingConnection3P.put(c2,c1);
            playingConnection3P.put(c2,c3);
            playingConnection3P.put(c3,c1);
            playingConnection3P.put(c3,c2);
            waitingConnection3P.clear();
            //first message to player view
        }
    }

    public Server() throws IOException{
        this.serverSocket=new ServerSocket(PORT);
    }

    public void run(){
        while (true){
            try {
                Socket newSocket =serverSocket.accept();
                SocketClientConnection socketConnetion = new SocketClientConnection(newSocket,this);
                executor.submit(socketConnetion);
            }catch (IOException e){
                System.out.println("Connection Error!");
            }
        }
    }


}