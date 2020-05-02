package it.polimi.ingsw.Server;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Message.ChosenGodMessage;
import it.polimi.ingsw.Message.OrderGameMessage;
import it.polimi.ingsw.Model.God;
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

    /**
     * when the game is and this function close the connection and deregister ClientConnetion
     * from the hashmap playingconnetion2P
     * @param c is the connection that have to close
     */
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

    /**
     * when the game is ending and this function close the connection and deregister ClientConnection
     * from the hashmap playingConnection3P
     * @param c is the connection that have to close
     */
    public synchronized void deregisterConnection3P(ClientConnection c){
        ClientConnection opponent1=playingConnection3P.get(c);
        ClientConnection opponent2=playingConnection3P.get(opponent1);
        if(opponent1!=null){
            opponent1.closeConnection();
        }
        if (opponent2!=null){
            opponent2.closeConnection();
        }
        playingConnection3P.remove(c);
        playingConnection3P.remove(opponent1);
        playingConnection3P.remove(opponent2);
        Iterator<String> iterator=waitingConnection3P.keySet().iterator();
        while (iterator.hasNext()){
            if (waitingConnection3P.get(iterator.next())==c){
                iterator.remove();
            }
        }

    }

    /**
     * add a name to the list waitingconnection2p and is the size of this list is 2 start a game
     * @param c clientconnection of the player
     * @param name is the name choose from the player
     */
    public synchronized void lobby2P(ClientConnection c,String name){
        ArrayList<Integer> ChosenGodPool;
        ArrayList<God>ChosenGod= new ArrayList<>();
        List<God> AllGod=God.getAllGod();
        ArrayList<Player> PlayerList = new ArrayList<>();
        if (waitingConnection2P.containsKey(name)){
            c.enterNewName(waitingConnection2P);
        }
        waitingConnection2P.put(name,c);
        if (waitingConnection2P.size() == 2){
            List<String> keys= new ArrayList<>(waitingConnection2P.keySet());
            ClientConnection c1= waitingConnection2P.get(keys.get(0));
            ClientConnection c2= waitingConnection2P.get(keys.get(1));
            Player player1= new Player(keys.get(0),1);
            Player player2= new Player(keys.get(1),2);
            Random random= new Random();
            int ChoseGodLike= random.nextInt(2);
            if (ChoseGodLike==0){
                ChosenGodPool=c1.ChooseGod(2);
                ChosenGod.add(AllGod.get(ChosenGodPool.get(0)));
                ChosenGod.add(AllGod.get(ChosenGodPool.get(1)));
                God GodChosen=c2.PickGod(new ChosenGodMessage(ChosenGod));
                player2.setGod(GodChosen);
                if (GodChosen.getGodName().equals(ChosenGod.get(0).getGodName()))
                player1.setGod(ChosenGod.get(1));
                else {
                    player1.setGod(ChosenGod.get(0));
                }
            } else {
                ChosenGodPool = c2.ChooseGod(2);
                ChosenGod.add(AllGod.get(ChosenGodPool.get(0)));
                ChosenGod.add(AllGod.get(ChosenGodPool.get(1)));
                God GodChosen=c1.PickGod(new ChosenGodMessage(ChosenGod));
                player1.setGod(GodChosen);
                if (GodChosen.getGodName().equals(ChosenGod.get(0).getGodName()))
                    player2.setGod(ChosenGod.get(1));
                else {
                    player2.setGod(ChosenGod.get(0));
                }
            }
            PlayerList.add(player1);
            PlayerList.add(player2);
            if (ChoseGodLike==0){
                PlayerList=c1.ChooseFirstPlayer(new OrderGameMessage(PlayerList));
            }else {
                PlayerList=c2.ChooseFirstPlayer(new OrderGameMessage(PlayerList));
            }
            View player1View= new View(player1.getIdPlayer(),true,c1);
            View player2View= new View(player2.getIdPlayer(),true,c2);
            Model model= new Model(PlayerList);
            Controller controller= new Controller(model);
            model.addObserver(player1View);
            model.addObserver(player2View);
            player1View.addObserver(controller);
            player2View.addObserver(controller);
            playingConnection2P.put(c1,c2);
            playingConnection2P.put(c2,c1);
            waitingConnection2P.clear();
            //first message to player view
        }

    }
    /**
     * add a name to the list waitingconnection3P and is the size of this list is 3 start a game
     * @param c clientconnection of the player
     * @param name is the name choose from the player
     */
    public synchronized void lobby3P(ClientConnection c,String name){
        ArrayList<Integer> ChosenGodPool;
        ArrayList<God>ChosenGod= new ArrayList<>();
        List<God> AllGod=God.getAllGod();
        ArrayList<Player> PlayerList = new ArrayList<>();
        if (waitingConnection3P.containsKey(name)){
            c.enterNewName(waitingConnection3P);
        }
        waitingConnection3P.put(name,c);
        if(waitingConnection3P.size()==3){
            List<String> keys= new ArrayList<>(waitingConnection3P.keySet());
            ClientConnection c1= waitingConnection3P.get(keys.get(0));
            ClientConnection c2= waitingConnection3P.get(keys.get(1));
            ClientConnection c3=waitingConnection3P.get(keys.get(2));
            Player player1= new Player(keys.get(0),1);
            Player player2= new Player(keys.get(1),2);
            Player player3= new Player(keys.get(2),3);
            Random random= new Random();
            int ChoseGodLike= random.nextInt(3);
            if (ChoseGodLike ==0){
                ChosenGodPool=c1.ChooseGod(3);
                ChosenGod.add(AllGod.get(ChosenGodPool.get(0)));
                ChosenGod.add(AllGod.get(ChosenGodPool.get(1)));
                ChosenGod.add(AllGod.get(ChosenGodPool.get(2)));
                God GodChosen=c2.PickGod(new ChosenGodMessage(ChosenGod));
                player2.setGod(GodChosen);
                for (int i=0;i<ChosenGod.size();i++)
                {
                    if (GodChosen.equals(ChosenGod.get(i))){
                        ChosenGod.remove(i);
                    }
                }
                GodChosen=c3.PickGod(new ChosenGodMessage(ChosenGod));
                player3.setGod(GodChosen);
                if (GodChosen.equals(ChosenGod.get(0))){
                    player1.setGod(ChosenGod.get(1));
                }else {
                    player1.setGod(ChosenGod.get(0));
                }
            }else if(ChoseGodLike==1){
                ChosenGodPool=c2.ChooseGod(3);
                ChosenGod.add(AllGod.get(ChosenGodPool.get(0)));
                ChosenGod.add(AllGod.get(ChosenGodPool.get(1)));
                ChosenGod.add(AllGod.get(ChosenGodPool.get(2)));
                God GodChosen=c3.PickGod(new ChosenGodMessage(ChosenGod));
                player3.setGod(GodChosen);
                for (int i=0;i<ChosenGod.size();i++)
                {
                    if (GodChosen.equals(ChosenGod.get(i))){
                        ChosenGod.remove(i);
                    }
                }
                GodChosen=c1.PickGod(new ChosenGodMessage(ChosenGod));
                player1.setGod(GodChosen);
                if (GodChosen.equals(ChosenGod.get(0))){
                    player2.setGod(ChosenGod.get(1));
                }else {
                    player2.setGod(ChosenGod.get(0));
                }
            }else {
                ChosenGodPool=c1.ChooseGod(3);
                ChosenGod.add(AllGod.get(ChosenGodPool.get(0)));
                ChosenGod.add(AllGod.get(ChosenGodPool.get(1)));
                ChosenGod.add(AllGod.get(ChosenGodPool.get(2)));
                God GodChosen=c3.PickGod(new ChosenGodMessage(ChosenGod));
                player3.setGod(GodChosen);
                for (int i=0;i<ChosenGod.size();i++)
                {
                    if (GodChosen.equals(ChosenGod.get(i))){
                        ChosenGod.remove(i);
                    }
                }
                GodChosen=c1.PickGod(new ChosenGodMessage(ChosenGod));
                player1.setGod(GodChosen);
                if (GodChosen.equals(ChosenGod.get(0))){
                    player2.setGod(ChosenGod.get(1));
                }else {
                    player2.setGod(ChosenGod.get(0));
                }
            }
            View player1View= new View(player1.getIdPlayer(),true,c1);
            View player2View= new View(player2.getIdPlayer(),true,c2);
            View player3View = new View(player3.getIdPlayer(),true,c3);
            PlayerList.add(player1);
            PlayerList.add(player2);
            PlayerList.add(player3);
            if (ChoseGodLike==0){
                PlayerList=c1.ChooseFirstPlayer(new OrderGameMessage(PlayerList));
            }else if (ChoseGodLike==1){
                PlayerList=c2.ChooseFirstPlayer(new OrderGameMessage(PlayerList));
            } else {
                PlayerList=c3.ChooseFirstPlayer(new OrderGameMessage(PlayerList));
            }
            Model model= new Model(PlayerList);
            Controller controller= new Controller(model);
            model.addObserver(player1View);
            model.addObserver(player2View);
            model.addObserver(player3View);
            player1View.addObserver(controller);
            player2View.addObserver(controller);
            player3View.addObserver(controller);
            playingConnection3P.put(c1,c2);
            playingConnection3P.put(c2,c3);
            playingConnection3P.put(c3,c1);
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
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket,this);
                executor.submit(socketConnection);
            }catch (IOException e){
                System.out.println("Connection Error!");
            }
        }
    }


}