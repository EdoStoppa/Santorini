package it.polimi.ingsw.Server;

import it.polimi.ingsw.Message.ChosenGodMessage;
import it.polimi.ingsw.Message.HelpMessage;
import it.polimi.ingsw.Message.OrderGameMessage;
import it.polimi.ingsw.Message.PickGodMessage;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.View.View;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

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

    private void close(int gameMode) {
        closeConnection();
        System.out.println("Deregistering client...");
        if (gameMode==2) {
            server.deregisterConnection2P(this);
        }
        else{
            server.deregisterConnection3P(this);
        }
        System.out.println("done");
    }



    @Override
    public void run() {
        Scanner in;
        String name;
        Integer gameMode=0;
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
            close(gameMode);
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

    /**
     * send of the message only to the client selected
     * @param message message to send
     */
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

    /**
     * the GodlikePlayer choose the God for this game
     * @param player number of player of the game
     * @return list of god chosen
     */
    @Override
    public ArrayList<Integer> ChooseGod(int player){
        ArrayList<Integer> pickGod=new ArrayList<>();
        Integer i=0;
        String pickPool;
        try{
            Scanner in= new Scanner(socket.getInputStream());
            this.asyncSend(new PickGodMessage());
            pickPool=in.nextLine();
            String[] pick= pickPool.split(",");
            while (i<player){
                pickGod.add(Integer.parseInt(pick[i]));
            }

        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        return pickGod;
    }


    @Override
    public God PickGod(ChosenGodMessage chosenGodMessage){
        int pick=0;
        try{
            Scanner in= new Scanner(socket.getInputStream());
            this.asyncSend(chosenGodMessage);
            pick=in.nextInt();
            while (pick>=chosenGodMessage.getSize()){
             this.asyncSend("please enter a correct god's number");
                pick=in.nextInt();
            }
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        return chosenGodMessage.getChosenGod().get(pick);
    }

    /**
     * check that the name has already been chosen
     * @param NameOpponent map that contains all the name used in the server
     * @return a name not used in the server
     */
    @Override
    public String enterNewName( Map <String, ClientConnection> NameOpponent) {
        String name = null;
        try {Scanner in= new Scanner(socket.getInputStream());
            this.asyncSend("this name is already. Enter a new one");
            name= in.nextLine();
            while (NameOpponent.containsKey(name)){
                this.asyncSend("this name is already. Enter a new one");
                name= in.nextLine();
            }
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        return name;
    }

    @Override
    public ArrayList<Player> ChooseFirstPlayer(OrderGameMessage orderGameMessage){
        String firstPlayer="";
        Boolean check= true;
        ArrayList<Player> players= orderGameMessage.getPlayerlist();
        Player notFirst;
        try {
            Scanner in= new Scanner(socket.getInputStream());
            this.asyncSend(orderGameMessage);
            firstPlayer=in.nextLine();
            while(check){
                for (int i=0;i<orderGameMessage.getSize();i++){
                    if (firstPlayer.equals(orderGameMessage.getPlayerlist().get(i))){
                       check= false;
                    }
                    }
                if (check=true){
                    asyncSend("enter a correct name player");
                    firstPlayer=in.nextLine();
                }
            }
            while (firstPlayer.equals(players.get(0))){
                notFirst=players.get(0);
                players.remove(0);
                players.add(notFirst);
            }

        }catch (IOException e){
            System.err.println(e.getMessage());
        }

    return players;
    }

}
