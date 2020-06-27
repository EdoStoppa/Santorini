package it.polimi.ingsw.Server;


import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Message.HelpMessage;
import it.polimi.ingsw.Message.ServerMessage.*;
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
    private static int PORT = 54321;
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


    public Server() throws IOException{
        this.serverSocket = new ServerSocket(PORT);
    }

    public Server(int port) throws IOException{
        Server.PORT = port;
        this.serverSocket = new ServerSocket(PORT);
    }

    public void run(){
        System.out.println("Server running! Listening on port: " + PORT);
        while (true){
            try {
                Socket newSocket = serverSocket.accept();
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket,this);
                executor.submit(socketConnection);
            }catch (IOException e){
                System.out.println("Connection Error!");
                e.printStackTrace();
            }
        }
    }


    //------------------------------ Methods used to remove an ongoing match ------------------------------

    /**
     * When the game is running this function close the connection and deregister ClientConnection
     * from the hashmap playingConnection2P
     * @param c is the connection that have to be closed/removed
     */
    public synchronized void deregisterConnection2P(SocketClientConnection c){
        SocketClientConnection opponent=playingConnection2P.get(c);
        if (opponent!= null){
            opponent.closeConnection();
        }
        playingConnection2P.remove(c);
        playingConnection2P.remove(opponent);
        waitingConnection2P.keySet().removeIf(s -> waitingConnection2P.get(s) == c);
    }

    /**
     * When the game is running this function close the connection and deregister ClientConnection
     * from the hashmap playingConnection3P
     * @param c is the connection that have to be closed/removed
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
        waitingConnection3P.keySet().removeIf(s -> waitingConnection3P.get(s) == c);
    }


    //------------------------------ Methods used to add player to lobby or start the actual match ------------------------------

    /**
     * add a name to the list waitingConnection2p and is the size of this list is 2 start a game
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
     * add a name to the list waitingConnection3P and is the size of this list is 3 start a game
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
            System.out.println("Launching initGame3P");
            initGame3P();
        } else {
            c.asyncSend(HelpMessage.noAnswer + "Please, wait for other players to connect...");
        }
    }

    private void initGame2P() {
        Map<String, SocketClientConnection> waitingConnection2P = new HashMap<>(this.waitingConnection2P);
        List<SocketClientConnection> connectionList = new ArrayList<>(waitingConnection2P.values());
        this.playingConnection2P.put(connectionList.get(0), connectionList.get(1));
        this.playingConnection2P.put(connectionList.get(1), connectionList.get(0));
        this.waitingConnection2P.clear();

        Thread thread2P= new Thread(() -> {
            ArrayList<String> keys = new ArrayList<>(waitingConnection2P.keySet());
            SocketClientConnection godLike, opponent;
            Player playerGodLike,playerOpponent;
            ArrayList<Player> playerList = new ArrayList<>();
            HashMap<String, God> mapPlayerGod = new HashMap<>();

            int choseGodLike = (new Random()).nextInt(2);
            if (choseGodLike==0){
                godLike = waitingConnection2P.get(keys.get(0));
                opponent= waitingConnection2P.get(keys.get(1));
                playerGodLike = new Player(keys.get(0), 1);
                playerOpponent = new Player(keys.get(1), 2);
            } else {
                godLike = waitingConnection2P.get(keys.get(1));
                opponent= waitingConnection2P.get(keys.get(0));
                playerGodLike = new Player(keys.get(1), 1);
                playerOpponent = new Player(keys.get(0), 2);
            }
            playerList.add(playerGodLike);
            playerList.add(playerOpponent);

            godLike.asyncSend(HelpMessage.noAnswer + HelpMessage.init);
            opponent.asyncSend(HelpMessage.noAnswer + HelpMessage.init + "\n" + HelpMessage.wait);

            ArrayList<God> pickedGod = godLike.chooseGod(2,new PickGodMessage(2));
            if(pickedGod.size() == 0){
                godLike.close(2);
                return;
            }
            System.out.println("playerGodLike chose " + pickedGod.get(0).getGodName() + "  " + pickedGod.get(1).getGodName());

            godLike.asyncSend(HelpMessage.noAnswer + HelpMessage.wait);
            God chosenGod = opponent.pickGod(new ChosenGodMessage(pickedGod, 2));
            if(chosenGod == null){
                opponent.close(2);
                return;
            }
            playerOpponent.setGod(chosenGod);
            mapPlayerGod.put(playerOpponent.getIdPlayer(), chosenGod);
            System.out.println("player2 chose " + chosenGod.getGodName() );

            setLastGod(playerGodLike, mapPlayerGod, pickedGod, chosenGod);

            opponent.asyncSend(HelpMessage.noAnswer + HelpMessage.wait);
            String firstPlayer = godLike.chooseFirstPlayer(new OrderGameMessage(keys));
            if(firstPlayer == null){
                godLike.close(2);
                return;
            }
            System.out.println("Starting player: " + firstPlayer);

            System.out.println("Fixing player list");
            while (!firstPlayer.equals(playerList.get(0).getIdPlayer())) {
                Player notFirst = playerList.get(0);
                playerList.remove(0);
                playerList.add(notFirst);
            }

            System.out.println("Creating model-view-controller");
            View player1View = new View(playerGodLike.getIdPlayer(), 2, true, godLike);
            View player2View = new View(playerOpponent.getIdPlayer(), 2,  true, opponent);
            Model model = new Model(playerList);
            Controller controller = new Controller(model);
            model.addObserver(player1View);
            model.addObserver(player2View);
            player1View.addObserver(controller);
            player2View.addObserver(controller);

            GodRecapMessage message = new GodRecapMessage(mapPlayerGod, playerList.get(0).getIdPlayer());
            godLike.asyncSend(message);
            opponent.asyncSend(message);

            if(!placeOnBoardSequence(model, playerList, waitingConnection2P))
                return;

            synchronized (godLike.lock){
                godLike.lock.notify();
                godLike.setIsInit(false);
            }
            synchronized (opponent.lock){
                opponent.lock.notify();
                opponent.setIsInit(false);
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
            ArrayList<String> keys = new ArrayList<>(waitingConnection3P.keySet());
            SocketClientConnection godLike, opponent1, opponent2;
            Player playerGodLike, playerOpponent1, playerOpponent2;
            ArrayList<Player> playerList = new ArrayList<>();
            HashMap<String, God> mapPlayerGod = new HashMap<>();

            int choseGodLike = (new Random()).nextInt(3);
            if (choseGodLike == 0) {
                godLike = waitingConnection3P.get(keys.get(0));
                opponent1 = waitingConnection3P.get(keys.get(1));
                opponent2 = waitingConnection3P.get(keys.get(2));
                playerGodLike = new Player(keys.get(0), 1);
                playerOpponent1 = new Player(keys.get(1), 2);
                playerOpponent2 = new Player(keys.get(2), 3);
            } else if (choseGodLike == 1) {
                godLike = waitingConnection3P.get(keys.get(1));
                opponent1 = waitingConnection3P.get(keys.get(2));
                opponent2 = waitingConnection3P.get(keys.get(0));
                playerGodLike = new Player(keys.get(1), 1);
                playerOpponent1 = new Player(keys.get(2), 2);
                playerOpponent2 = new Player(keys.get(0), 3);
            } else {
                godLike = waitingConnection3P.get(keys.get(2));
                opponent1 = waitingConnection3P.get(keys.get(0));
                opponent2 = waitingConnection3P.get(keys.get(1));
                playerGodLike = new Player(keys.get(2), 1);
                playerOpponent1 = new Player(keys.get(0), 2);
                playerOpponent2 = new Player(keys.get(1), 3);
            }
            playerList.add(playerGodLike);
            playerList.add(playerOpponent1);
            playerList.add(playerOpponent2);

            godLike.asyncSend(HelpMessage.noAnswer + HelpMessage.init);
            opponent1.asyncSend(HelpMessage.noAnswer + HelpMessage.init + "\n" + HelpMessage.wait);
            opponent2.asyncSend(HelpMessage.noAnswer + HelpMessage.init + "\n" + HelpMessage.wait);

            ArrayList<God> pickedGod = godLike.chooseGod(3, new PickGodMessage(3));
            if(pickedGod.size() == 0){
                godLike.close(3);
                return;
            }
            System.out.println("playerGodLike chose " + pickedGod.get(0).getGodName() + "  " + pickedGod.get(1).getGodName() + " " + pickedGod.get(2).getGodName());

            godLike.asyncSend(HelpMessage.noAnswer + HelpMessage.wait);
            God chosenGod = opponent1.pickGod(new ChosenGodMessage(pickedGod, 3));
            if(chosenGod == null){
                opponent1.close(3);
                return;
            }
            playerOpponent1.setGod(chosenGod);
            mapPlayerGod.put(playerOpponent1.getIdPlayer(), chosenGod);
            System.out.println("Opponent chose " + chosenGod.getGodName());
            for (int i = 0; i < 3; i++) {
                if (pickedGod.get(i).getGodName().equals(chosenGod.getGodName())) {
                    pickedGod.remove(i);
                    break;
                }
            }

            opponent1.asyncSend(HelpMessage.noAnswer + HelpMessage.wait);
            chosenGod = opponent2.pickGod(new ChosenGodMessage(pickedGod, 2));
            if(chosenGod == null){
                opponent2.close(3);
                return;
            }
            playerOpponent2.setGod(chosenGod);
            mapPlayerGod.put(playerOpponent2.getIdPlayer(), chosenGod);
            System.out.println("player3 chose: " + chosenGod.getGodName());
            opponent2.asyncSend(HelpMessage.noAnswer + HelpMessage.wait);

            setLastGod(playerGodLike, mapPlayerGod, pickedGod, chosenGod);

            String firstPlayer = godLike.chooseFirstPlayer(new OrderGameMessage(keys));
            if(firstPlayer == null){
                godLike.close(3);
                return;
            }
            System.out.println("Starting player: " + firstPlayer);

            System.out.println("Fixing player list");
            while (!firstPlayer.equals(playerList.get(0).getIdPlayer())) {
                Player notFirst = playerList.get(0);
                playerList.remove(0);
                playerList.add(notFirst);
            }

            System.out.println("Creating model-view-controller");
            View player1View = new View(playerGodLike.getIdPlayer(), 3, true, godLike);
            View player2View = new View(playerOpponent1.getIdPlayer(), 3, true, opponent1);
            View player3View = new View(playerOpponent2.getIdPlayer(), 3, true, opponent2);
            Model model = new Model(playerList);
            Controller controller = new Controller(model);
            model.addObserver(player1View);
            model.addObserver(player2View);
            model.addObserver(player3View);
            player1View.addObserver(controller);
            player2View.addObserver(controller);
            player3View.addObserver(controller);

            GodRecapMessage message = new GodRecapMessage(mapPlayerGod, playerList.get(0).getIdPlayer());
            godLike.asyncSend(message);
            opponent1.asyncSend(message);
            opponent2.asyncSend(message);

            //launching the sequence to place all constructors on the board, if return false someone disconnected -> close game
            if(!placeOnBoardSequence(model, playerList, waitingConnection3P))
                return;

            //unlocking all socketClientConnection to let them read from socket
            synchronized (godLike.lock) {
                godLike.lock.notify();
                godLike.setIsInit(false);
            }
            synchronized (opponent1.lock) {
                opponent1.lock.notify();
                opponent1.setIsInit(false);
            }
            synchronized (opponent2.lock) {
                opponent2.lock.notify();
                opponent2.setIsInit(false);
            }

            model.startGame();
            controller.preparePhase();
        });
        thread3P.start();
    }


    //--------------- Helper Methods ---------------

    private void setLastGod(Player playerGodLike, HashMap<String, God> mapPlayerGod, ArrayList<God> pickedGod, God chosenGod) {
        if (chosenGod.getGodName().equals(pickedGod.get(0).getGodName())) {
            playerGodLike.setGod(pickedGod.get(1));
            mapPlayerGod.put(playerGodLike.getIdPlayer(), pickedGod.get(1));
        } else {
            playerGodLike.setGod(pickedGod.get(0));
            mapPlayerGod.put(playerGodLike.getIdPlayer(), pickedGod.get(0));
        }
    }

    private boolean placeOnBoardSequence(Model model, List<Player> pList, Map<String, SocketClientConnection> connectionMap){
        boolean isFirst = true;
        SocketClientConnection connection;

        Position pos;
        for(Player p : pList){
            if(!isFirst){
                for(Player opponent : pList){
                    if(!p.getIdPlayer().equals(opponent.getIdPlayer()))
                        connectionMap.get(opponent.getIdPlayer()).send(HelpMessage.noAnswer + "Please wait while " + p.getIdPlayer() + " is choosing where to place a constructor");
                }
            }

            connection = connectionMap.get(p.getIdPlayer());

            pos = connection.firstPlaceConstructor(isFirst);
            if(pos == null){
                connection.close(pList.size());
                return false;
            }

            isFirst = false;

            model.serverMove(p.getAllConstructors().get(0), pos, p.getIdPlayer());
            System.out.println("Placed on position: " + pos.getRow() + "," + pos.getCol());

            pos = connection.firstPlaceConstructor(false);
            if(pos == null){
                connection.close(pList.size());
                return false;
            }

            model.serverMove(p.getAllConstructors().get(1), pos, p.getIdPlayer());
            System.out.println("Placed on position: " + pos.getRow() + "," + pos.getCol());

        }

        return true;

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
}