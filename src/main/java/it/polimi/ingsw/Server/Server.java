package it.polimi.ingsw.Server;


import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Message.HelpMessage;
import it.polimi.ingsw.Message.ServerMessage.ChosenGodMessage;
import it.polimi.ingsw.Message.ServerMessage.OrderGameMessage;
import it.polimi.ingsw.Message.ServerMessage.PickGodMessage;
import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.View.View;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT=12345;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private Map<String, SocketClientConnection> waitingConnection2P = new HashMap<>();
    private Map<String, SocketClientConnection> waitingConnection3P = new HashMap<>();
    private Map<SocketClientConnection,SocketClientConnection> playingConnection2P = new HashMap<>();
    private Map<SocketClientConnection,SocketClientConnection> playingConnection3P = new HashMap<>();

    public Map<String, SocketClientConnection> getWaitingConnection2P(){
        return waitingConnection2P;
    }

    public Map<String, SocketClientConnection> getWaitingConnection3P(){
        return waitingConnection3P;
    }

    /**
     * when the game is and this function close the connection and deregister ClientConnetion
     * from the hashmap playingconnetion2P
     * @param c is the connection that have to close
     */
    public synchronized void deregisterConnection2P(SocketClientConnection c){
        SocketClientConnection opponent=playingConnection2P.get(c);
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

    /**
     * when the game is ending and this function close the connection and deregister SocketClientConnection
     * from the hashmap playingConnection3P
     * @param c is the connection that have to close
     */
    public synchronized void deregisterConnection3P(SocketClientConnection c){
        SocketClientConnection opponent1 = playingConnection3P.get(c);
        SocketClientConnection opponent2 = playingConnection3P.get(opponent1);
        if (opponent1 != null){
            opponent1.closeConnection();
        }
        if (opponent2 != null){
            opponent2.closeConnection();
        }
        playingConnection3P.remove(c);
        playingConnection3P.remove(opponent1);
        playingConnection3P.remove(opponent2);
        Iterator<String> iterator= waitingConnection3P.keySet().iterator();
        while(iterator.hasNext()){
            if(waitingConnection3P.get(iterator.next())==c){
                iterator.remove();
            }
        }

    }

    /**
     * add a name to the list waitingconnection2p and is the size of this list is 2 start a game
     * @param c SocketClientConnection of the player

     */
    public synchronized void lobby2P(SocketClientConnection c,String name){
        System.out.println("Registering in 2P lobby...");
        if (waitingConnection2P.containsKey(name)){
            System.out.println("Name already taken");
            name=c.enterNewName(waitingConnection2P);
        }
        waitingConnection2P.put(name,c);
        System.out.println("Accepted " + name);
        c.asyncSend("Accepted " + name);
        if (waitingConnection2P.size() == 2 && areOthersAlive(waitingConnection2P, name)) {
            System.out.println("Launching initGame2P");
            initGame2P();
        } else {
            c.asyncSend(HelpMessage.noAnswer + "Please, wait for other players to connect...");
        }
    }

    /**
     * add a name to the list waitingconnection3P and is the size of this list is 3 start a game
     * @param c SocketClientConnection of the player
     * @param name is the name choose from the player
     */
    public synchronized void lobby3P(SocketClientConnection c,String name){
        System.out.println("Registering in 3P lobby...");
        if (waitingConnection3P.containsKey(name)){
            System.out.println("Name already taken");
            name=c.enterNewName(waitingConnection2P);
        }
        waitingConnection3P.put(name,c);
        System.out.println("Accepted " +name);
        c.asyncSend("Accepted " +name);
        if (waitingConnection3P.size() == 3 && areOthersAlive(waitingConnection3P, name)) {
            System.out.println("Launching initGame2P");
            initGame3P();
        } else {
            c.asyncSend(HelpMessage.noAnswer + "Please, wait for other players to connect...");
        }
    }

    private boolean areOthersAlive(Map<String, SocketClientConnection> waitingConnection, String callerName){
        boolean answ = true;
        List<String> names = new ArrayList<>(waitingConnection.keySet());
        for(String name : names){
            if(!name.equals(callerName)){
                SocketClientConnection connection = waitingConnection.get(name);
                if(!connection.ping()){
                    System.out.println("Removing lock on Connection run() to end associated thread");
                    synchronized (connection.lock){
                        connection.lock.notify();
                    }
                    waitingConnection.remove(name);
                    answ = false;
                }
            }

        }

        return answ;
    }

    public Server() throws IOException{
        this.serverSocket=new ServerSocket(PORT);
    }

    public void run(){
        while (true){
            try {
                Socket newSocket =serverSocket.accept();
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket,this);
                executor.submit(socketConnection);
            }catch (IOException e){
                System.out.println("Connection Error!");
                e.printStackTrace();
            }
        }
    }

    private void initGame2P() {
        Map<String, SocketClientConnection> waitingConnection2P = new HashMap<>(this.waitingConnection2P);
        List<SocketClientConnection> connectionList = new ArrayList<>(waitingConnection2P.values());
        this.playingConnection2P.put(connectionList.get(0), connectionList.get(1));
        this.playingConnection2P.put(connectionList.get(1), connectionList.get(0));
        this.waitingConnection2P.clear();

        Thread thread2P= new Thread(() -> {
            ArrayList<God>ChosenGod;
            ArrayList<Player> PlayerList = new ArrayList<>();
            String FirstPlayer;
            Position firstConstructor;
            ArrayList<String> keys = new ArrayList<>(waitingConnection2P.keySet());
            SocketClientConnection Goodlike, opponent;
            Player playerGodLike,playerOpponent;
            Random random = new Random();

            int ChoseGodLike = random.nextInt(2);
            if (ChoseGodLike==0){
                Goodlike = waitingConnection2P.get(keys.get(0));
                opponent= waitingConnection2P.get(keys.get(1));
                playerGodLike = new Player(keys.get(0), 1);
                playerOpponent = new Player(keys.get(1), 2);
            } else {
                Goodlike = waitingConnection2P.get(keys.get(1));
                opponent= waitingConnection2P.get(keys.get(0));
                playerGodLike = new Player(keys.get(1), 1);
                playerOpponent = new Player(keys.get(0), 2);
            }

            ChosenGod=Goodlike.ChooseGod(2,new PickGodMessage(2));
            if(ChosenGod.size() == 0){
                Goodlike.close(2);
                return;
            }
            System.out.println("playerGodLike chose "+ChosenGod.get(0).getGodName() + "  " + ChosenGod.get(1).getGodName());

            God GodChosen = opponent.PickGod(new ChosenGodMessage(ChosenGod, 2));
            if(GodChosen == null){
                opponent.close(2);
                return;
            }
            playerOpponent.setGod(GodChosen);
            System.out.println("player2 chose " + GodChosen.getGodName() );

            if (GodChosen.getGodName().equals(ChosenGod.get(0).getGodName())) {
                playerGodLike.setGod(ChosenGod.get(1));
            }else {
                playerGodLike.setGod(ChosenGod.get(0));
            }
            PlayerList.add(playerGodLike);
            PlayerList.add(playerOpponent);
            FirstPlayer = Goodlike.ChooseFirstPlayer(new OrderGameMessage(keys));
            if(FirstPlayer == null){
                Goodlike.close(2);
                return;
            }
            System.out.println("Starting player: " + FirstPlayer);

            System.out.println("Fixing player list");
            while (!FirstPlayer.equals(PlayerList.get(0).getIdPlayer())) {
                Player notFirst = PlayerList.get(0);
                PlayerList.remove(0);
                PlayerList.add(notFirst);
            }

            System.out.println("Creating model-view-controller");
            View player1View = new View(playerGodLike.getIdPlayer(), 2, true, Goodlike);
            View player2View = new View(playerOpponent.getIdPlayer(), 2,  true, opponent);
            Model model = new Model(PlayerList);
            Controller controller = new Controller(model);
            model.addObserver(player1View);
            model.addObserver(player2View);
            player1View.addObserver(controller);
            player2View.addObserver(controller);

            if (PlayerList.get(0).getIdPlayer().equals(playerGodLike.getIdPlayer())) {
                firstConstructor = Goodlike.FirstPlaceConstructor(true);
                model.serverMove(playerGodLike.getAllConstructors().get(0), firstConstructor, playerGodLike.getIdPlayer());
                System.out.println("Placed on position: " + firstConstructor.getRow() + "," + firstConstructor.getCol());
                firstConstructor = Goodlike.FirstPlaceConstructor(false);
                model.serverMove(playerGodLike.getAllConstructors().get(1), firstConstructor, playerGodLike.getIdPlayer());
                System.out.println("Placed on position: " + firstConstructor.getRow() + "," + firstConstructor.getCol());
                firstConstructor = opponent.FirstPlaceConstructor(false);
                model.serverMove(playerOpponent.getAllConstructors().get(0), firstConstructor, playerOpponent.getIdPlayer());
                System.out.println("Placed on position: " + firstConstructor.getRow() + "," + firstConstructor.getCol());
                firstConstructor = opponent.FirstPlaceConstructor(false);
                model.serverMove(playerOpponent.getAllConstructors().get(1), firstConstructor, playerOpponent.getIdPlayer());
                System.out.println("Placed on position: " + firstConstructor.getRow() + "," + firstConstructor.getCol());
            } else {
                firstConstructor=opponent.FirstPlaceConstructor(true);
                model.serverMove(playerOpponent.getAllConstructors().get(0),firstConstructor,playerOpponent.getIdPlayer());
                System.out.println("Placed on position: " + firstConstructor.getRow() + "," + firstConstructor.getCol());
                firstConstructor=opponent.FirstPlaceConstructor(false);
                model.serverMove(playerOpponent.getAllConstructors().get(1),firstConstructor,playerOpponent.getIdPlayer());
                System.out.println("Placed on position: " + firstConstructor.getRow() + "," + firstConstructor.getCol());
                firstConstructor=Goodlike.FirstPlaceConstructor(false);
                model.serverMove(playerGodLike.getAllConstructors().get(0),firstConstructor,playerGodLike.getIdPlayer());
                System.out.println("Placed on position: " + firstConstructor.getRow() + "," + firstConstructor.getCol());
                firstConstructor=Goodlike.FirstPlaceConstructor(false);
                model.serverMove(playerGodLike.getAllConstructors().get(1),firstConstructor,playerGodLike.getIdPlayer());
                System.out.println("Placed on position: " + firstConstructor.getRow() + "," + firstConstructor.getCol());
            }

            synchronized (Goodlike.lock){
                Goodlike.lock.notify();
            }
            synchronized (opponent.lock){
                opponent.lock.notify();
            }

            model.startGame();
            controller.preparePhase();
        });
        thread2P.start();

    }

    private void initGame3P() {
        Map<String, SocketClientConnection> waitingConnection3P = new HashMap<>(this.waitingConnection3P);
        List<SocketClientConnection> connectionList = new ArrayList<>(waitingConnection3P.values());
        this.playingConnection3P.put(connectionList.get(0), connectionList.get(1));
        this.playingConnection3P.put(connectionList.get(1), connectionList.get(2));
        this.playingConnection3P.put(connectionList.get(2), connectionList.get(0));
        this.waitingConnection3P.clear();

        Thread thread3P = new Thread(() -> {
            ArrayList<God> ChosenGod;
            ArrayList<Player> PlayerList = new ArrayList<>();
            String FirstPlayer;
            Position firstConstructor;
            ArrayList<String> keys = new ArrayList<>(waitingConnection3P.keySet());
            SocketClientConnection Goodlike, opponent1, opponent2;
            Player playerGodLike, playerOpponent1, playerOpponent2;
            Random random = new Random();
            int ChoseGodLike = random.nextInt(3);
            if (ChoseGodLike == 0) {
                Goodlike = waitingConnection3P.get(keys.get(0));
                opponent1 = waitingConnection3P.get(keys.get(1));
                opponent2 = waitingConnection3P.get(keys.get(2));
                playerGodLike = new Player(keys.get(0), 1);
                playerOpponent1 = new Player(keys.get(1), 2);
                playerOpponent2 = new Player(keys.get(2), 3);
            } else if (ChoseGodLike == 1) {
                Goodlike = waitingConnection3P.get(keys.get(1));
                opponent1 = waitingConnection3P.get(keys.get(2));
                opponent2 = waitingConnection3P.get(keys.get(0));
                playerGodLike = new Player(keys.get(1), 1);
                playerOpponent1 = new Player(keys.get(2), 2);
                playerOpponent2 = new Player(keys.get(0), 3);
            } else {
                Goodlike = waitingConnection3P.get(keys.get(2));
                opponent1 = waitingConnection3P.get(keys.get(0));
                opponent2 = waitingConnection3P.get(keys.get(1));
                playerGodLike = new Player(keys.get(2), 1);
                playerOpponent1 = new Player(keys.get(0), 2);
                playerOpponent2 = new Player(keys.get(1), 3);
            }

            ChosenGod = Goodlike.ChooseGod(3, new PickGodMessage(3));
            if(ChosenGod.size() == 0){
                Goodlike.close(3);
                return;
            }
            System.out.println("playerGodLike chose " + ChosenGod.get(0).getGodName() + "  " + ChosenGod.get(1).getGodName() + " " + ChosenGod.get(2).getGodName());

            God GodChosen = opponent1.PickGod(new ChosenGodMessage(ChosenGod, 3));
            if(GodChosen == null){
                opponent1.close(3);
                return;
            }
            playerOpponent1.setGod(GodChosen);
            System.out.println("Opponent chose " + GodChosen.getGodName());
            for (int i = 0; i < 3; i++) {
                if (ChosenGod.get(i).getGodName().equals(GodChosen.getGodName())) {
                    ChosenGod.remove(i);
                    break;
                }
            }

            GodChosen = opponent2.PickGod(new ChosenGodMessage(ChosenGod, 2));
            if(GodChosen == null){
                opponent2.close(3);
                return;
            }
            playerOpponent2.setGod(GodChosen);
            System.out.println("player3 chose: " + GodChosen.getGodName());

            if (GodChosen.getGodName().equals(ChosenGod.get(0).getGodName())) {
                playerGodLike.setGod(ChosenGod.get(1));
            } else {
                playerGodLike.setGod(ChosenGod.get(0));
            }
            PlayerList.add(playerGodLike);
            PlayerList.add(playerOpponent1);
            PlayerList.add(playerOpponent2);
            FirstPlayer = Goodlike.ChooseFirstPlayer(new OrderGameMessage(keys));
            if(FirstPlayer == null){
                Goodlike.close(3);
                return;
            }
            System.out.println("Starting player: " + FirstPlayer);

            System.out.println("Fixing player list");
            while (!FirstPlayer.equals(PlayerList.get(0).getIdPlayer())) {
                Player notFirst = PlayerList.get(0);
                PlayerList.remove(0);
                PlayerList.add(notFirst);
            }

            System.out.println("Creating model-view-controller");
            View player1View = new View(playerGodLike.getIdPlayer(), 3, true, Goodlike);
            View player2View = new View(playerOpponent1.getIdPlayer(), 3, true, opponent1);
            View player3View = new View(playerOpponent2.getIdPlayer(), 3, true, opponent2);
            Model model = new Model(PlayerList);
            Controller controller = new Controller(model);
            model.addObserver(player1View);
            model.addObserver(player2View);
            model.addObserver(player3View);
            player1View.addObserver(controller);
            player2View.addObserver(controller);
            player3View.addObserver(controller);

            if (PlayerList.get(0).getIdPlayer().equals(playerGodLike.getIdPlayer())) {
                firstConstructor = Goodlike.FirstPlaceConstructor(true);
                model.serverMove(playerGodLike.getAllConstructors().get(0), firstConstructor, playerGodLike.getIdPlayer());
                System.out.println("Placed on position: " + firstConstructor.getRow() + "," + firstConstructor.getCol());
                firstConstructor = Goodlike.FirstPlaceConstructor(false);
                model.serverMove(playerGodLike.getAllConstructors().get(1), firstConstructor, playerGodLike.getIdPlayer());
                System.out.println("Placed on position: " + firstConstructor.getRow() + "," + firstConstructor.getCol());
                firstConstructor = opponent1.FirstPlaceConstructor(false);
                model.serverMove(playerOpponent1.getAllConstructors().get(0), firstConstructor, playerOpponent1.getIdPlayer());
                System.out.println("Placed on position: " + firstConstructor.getRow() + "," + firstConstructor.getCol());
                firstConstructor = opponent1.FirstPlaceConstructor(false);
                model.serverMove(playerOpponent1.getAllConstructors().get(1), firstConstructor, playerOpponent1.getIdPlayer());
                System.out.println("Placed on position: " + firstConstructor.getRow() + "," + firstConstructor.getCol());
                firstConstructor = opponent2.FirstPlaceConstructor(false);
                model.serverMove(playerOpponent2.getAllConstructors().get(0), firstConstructor, playerOpponent2.getIdPlayer());
                System.out.println("Placed on position: " + firstConstructor.getRow() + "," + firstConstructor.getCol());
                firstConstructor = opponent2.FirstPlaceConstructor(false);
                model.serverMove(playerOpponent2.getAllConstructors().get(1), firstConstructor, playerOpponent2.getIdPlayer());
                System.out.println("Placed on position: " + firstConstructor.getRow() + "," + firstConstructor.getCol());
            } else if (PlayerList.get(1).getIdPlayer().equals(playerGodLike.getIdPlayer())) {
                firstConstructor = opponent1.FirstPlaceConstructor(true);
                model.serverMove(playerOpponent1.getAllConstructors().get(0), firstConstructor, playerOpponent1.getIdPlayer());
                System.out.println("Placed on position: " + firstConstructor.getRow() + "," + firstConstructor.getCol());
                firstConstructor = opponent1.FirstPlaceConstructor(false);
                model.serverMove(playerOpponent1.getAllConstructors().get(1), firstConstructor, playerOpponent1.getIdPlayer());
                System.out.println("Placed on position: " + firstConstructor.getRow() + "," + firstConstructor.getCol());
                firstConstructor = opponent2.FirstPlaceConstructor(false);
                model.serverMove(playerOpponent2.getAllConstructors().get(0), firstConstructor, playerOpponent2.getIdPlayer());
                System.out.println("Placed on position: " + firstConstructor.getRow() + "," + firstConstructor.getCol());
                firstConstructor = opponent2.FirstPlaceConstructor(false);
                model.serverMove(playerOpponent2.getAllConstructors().get(1), firstConstructor, playerOpponent2.getIdPlayer());
                System.out.println("Placed on position: " + firstConstructor.getRow() + "," + firstConstructor.getCol());
                firstConstructor = Goodlike.FirstPlaceConstructor(false);
                model.serverMove(playerGodLike.getAllConstructors().get(0), firstConstructor, playerGodLike.getIdPlayer());
                System.out.println("Placed on position: " + firstConstructor.getRow() + "," + firstConstructor.getCol());
                firstConstructor = Goodlike.FirstPlaceConstructor(false);
                model.serverMove(playerGodLike.getAllConstructors().get(1), firstConstructor, playerGodLike.getIdPlayer());
                System.out.println("Placed on position: " + firstConstructor.getRow() + "," + firstConstructor.getCol());
            } else {
                firstConstructor = opponent2.FirstPlaceConstructor(true);
                model.serverMove(playerOpponent2.getAllConstructors().get(0), firstConstructor, playerOpponent2.getIdPlayer());
                System.out.println("Placed on position: " + firstConstructor.getRow() + "," + firstConstructor.getCol());
                firstConstructor = opponent2.FirstPlaceConstructor(false);
                model.serverMove(playerOpponent2.getAllConstructors().get(1), firstConstructor, playerOpponent2.getIdPlayer());
                System.out.println("Placed on position: " + firstConstructor.getRow() + "," + firstConstructor.getCol());
                firstConstructor = Goodlike.FirstPlaceConstructor(false);
                model.serverMove(playerGodLike.getAllConstructors().get(0), firstConstructor, playerGodLike.getIdPlayer());
                System.out.println("Placed on position: " + firstConstructor.getRow() + "," + firstConstructor.getCol());
                firstConstructor = Goodlike.FirstPlaceConstructor(false);
                model.serverMove(playerGodLike.getAllConstructors().get(1), firstConstructor, playerGodLike.getIdPlayer());
                System.out.println("Placed on position: " + firstConstructor.getRow() + "," + firstConstructor.getCol());
                firstConstructor = opponent1.FirstPlaceConstructor(false);
                model.serverMove(playerOpponent1.getAllConstructors().get(0), firstConstructor, playerOpponent1.getIdPlayer());
                System.out.println("Placed on position: " + firstConstructor.getRow() + "," + firstConstructor.getCol());
                firstConstructor = opponent1.FirstPlaceConstructor(false);
                model.serverMove(playerOpponent1.getAllConstructors().get(1), firstConstructor, playerOpponent1.getIdPlayer());
                System.out.println("Placed on position: " + firstConstructor.getRow() + "," + firstConstructor.getCol());
            }

            synchronized (Goodlike.lock) {
                Goodlike.lock.notify();
            }
            synchronized (opponent1.lock) {
                opponent1.lock.notify();
            }
            synchronized (opponent2.lock) {
                opponent2.lock.notify();
            }

            model.startGame();
            controller.preparePhase();
        });
        thread3P.start();
    }

}