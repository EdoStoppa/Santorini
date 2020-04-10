package it.polimi.ingsw.Server;

import it.polimi.ingsw.Client.Client;

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
    private Map<Integer,ClientConnection> playingConnection3P = new HashMap<>();

    public synchronized void deregisterConnection(ClientConnection c){

    }

    public synchronized void lobby2P(ClientConnection c,String name){
        waitingConnection2P.put(name,c);
        if (waitingConnection2P.size() == 2){
            List<String> keys= new ArrayList<>(waitingConnection2P.keySet());
            ClientConnection c1= waitingConnection2P.get(keys.get(0));
            ClientConnection c2= waitingConnection2P.get(keys.get(1));
        }

    }

    public synchronized void lobby3P(ClientConnection c,String name){
        waitingConnection3P.put(name,c);
        if(waitingConnection3P.size()==3){

        }
    }


}
