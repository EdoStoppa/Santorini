package it.polimi.ingsw.Server;

import it.polimi.ingsw.Message.ServerMessage.ChosenGodMessage;
import it.polimi.ingsw.Message.ServerMessage.OrderGameMessage;
import it.polimi.ingsw.Message.ServerMessage.PickGodMessage;
import it.polimi.ingsw.Message.ServerMessage.PlaceFirstConstructorMessage;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Model.God;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

public class SocketClientConnection extends Observable<String> implements Runnable {

    private final Server server;
    private final Socket socket;
    private ObjectOutputStream out;
    private int gameMode;
    public final Object lock = new Object();
    private boolean active=true;
    private boolean playing = true;
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
            if(playing && active){
                System.err.println(e.getMessage() + " while playing == true");
                //e.printStackTrace();
                playing = false;
                close(gameMode);
            } else {
                System.out.println("Connection already closed");
            }
            active = false;
        }
    }

    public void close(int gameMode) {
        closeConnection();
        System.out.println("Deregistering client...");
        if (gameMode==2) {
            server.deregisterConnection2P(this);
        }
        if(gameMode==3){
            server.deregisterConnection3P(this);
        }
        System.out.println("done");
    }

    @Override
    public void run() {
        Scanner in;
        String name;
        gameMode = 0;
        String read;

        try {
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            send("Welcome to Santorini!\n2 or 3 player mode?");
            while(active){
                try{
                    read = in.nextLine();
                    gameMode = Integer.parseInt(read);
                    if(gameMode != 2 && gameMode != 3)
                        throw new NumberFormatException();
                    break;
                } catch (NumberFormatException e){
                    send("Please enter a possible game mode");
                } catch (NoSuchElementException e){
                    System.out.println("Connection closed by Client while choosing game mode, proceeding closing SocketClientConnection");
                    return;
                } catch (Exception e){
                    System.out.println("Unknown exception, closing SocketClientConnection");
                    return;
                }
            }

            send("Enter your name:");
            name = in.nextLine();
            if(gameMode == 2){
                if(server.getWaitingConnection2P().containsKey(name)){
                    name = enterNewName(server.getWaitingConnection2P());
                }
            } else {
                if(server.getWaitingConnection3P().containsKey(name)){
                    name = enterNewName(server.getWaitingConnection3P());
                }
            }
            if(name == null){
                System.out.println("Failed new player authentication, closing SocketClientConnection");
                return;
            }

            synchronized (lock){
                if (gameMode == 2) {
                    System.out.println("Starting lobby2P...");
                    server.lobby2P(this,name);
                } else {
                    System.out.println("Starting lobby3P...");
                    server.lobby3P(this, name);
                }

                try{
                    lock.wait();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            while (isActive()) {
                read = in.nextLine();
                notify(read);
            }

        }catch (IOException | NoSuchElementException e){
            if(active){
                if(playing){
                    close(gameMode);
                }
                System.out.println(e.getMessage() + " closed SocketClientConnection");
            } else {
                System.out.println("SocketClientConnection already closed");
            }
        }
    }

    public void closeConnection() {
        if(!socket.isClosed()){
            send("Some problem occurred, connection closed!\nIf you want to play again, please restart the game!");
            try {
                socket.close();
            } catch (IOException e){
                System.err.println("Error when closing socket");
            }
        }
        active=false;
    }

    /**
     * send of the message only to the client selected
     * @param message message to send
     */
    public void asyncSend(final Object message) {
        Thread thread = new Thread(() -> {
            send(message);
        });
        thread.start();
    }
    /**
     * the GodlikePlayer choose the God for this game
     * @param player number of player of the game
     * @return list of god chosen
     */
    public ArrayList<God> ChooseGod(int player,PickGodMessage pickGodMessage){
        ArrayList<God> pickGod=new ArrayList<>();
        int i=0;
        try{Scanner in = new Scanner(socket.getInputStream());
            this.asyncSend(new PickGodMessage(player));
            System.out.println("Getting God numbers...");
            String pickPool=in.nextLine();
            String[] pick= pickPool.split(",");
            System.out.println("Getting God numbers...");
            while (i<player){
                pickGod.add(pickGodMessage.GetGod(Integer.parseInt(pick[i])));
                i++;
            }
        }catch (IOException | NoSuchElementException e){
            System.err.println(e.getMessage() + " while choosing all Gods");
            //e.printStackTrace();
        }
        return pickGod;
    }


    public God PickGod(ChosenGodMessage chosenGodMessage){
        String pick=null;
        try{
            Scanner in= new Scanner(socket.getInputStream());
            this.asyncSend(chosenGodMessage);
            pick=in.nextLine();
            while (Integer.parseInt(pick)>chosenGodMessage.getSize()){
                this.asyncSend("Please enter a correct god's number");
                pick=in.nextLine();
            }
            return chosenGodMessage.getChosenGod(Integer.parseInt(pick));
        }catch (IOException | NoSuchElementException e){
            System.err.println(e.getMessage());
            //e.printStackTrace();
            return null;
        }
    }

    /**
     * check that the name has already been chosen
     * @param NameOpponent map that contains all the name used in the server
     * @return a name not used in the server
     */
    public String enterNewName( Map <String, SocketClientConnection> NameOpponent) {
        String name = null;
        try {Scanner in= new Scanner(socket.getInputStream());
            this.asyncSend("This name is already taken. Please enter a new one");
            name= in.nextLine();
            while (NameOpponent.containsKey(name)){
                this.asyncSend("This name is already taken. Please enter a new one");
                name= in.nextLine();
            }
        }catch (IOException | NoSuchElementException e){
            System.err.println(e.getMessage() + ", failed to retrieve a new correct id");
            //e.printStackTrace();
            name = null;
        }
        return name;
    }

    public String ChooseFirstPlayer(OrderGameMessage orderGameMessage){
        String firstPlayer=null;
        try {
            Scanner in= new Scanner(socket.getInputStream());
            this.asyncSend(orderGameMessage);
            firstPlayer = in.nextLine();
        }catch (IOException | NoSuchElementException e){
            System.err.println(e.getMessage());
            //e.printStackTrace();
        }

        return firstPlayer;
    }

    public Position FirstPlaceConstructor(boolean isFirstMessage){
        String Pos="";
        String[] coordinates;
        try {Scanner in= new Scanner(socket.getInputStream());
            this.asyncSend(new PlaceFirstConstructorMessage(isFirstMessage));
            while(Pos.equals("")){
                Pos=in.nextLine();
            }
            coordinates=Pos.split(",");
        }catch (IOException | NoSuchElementException e){
            System.err.println(e.getMessage());
            //e.printStackTrace();
            return null;
        }
        return new Position(Integer.parseInt(coordinates[0]),Integer.parseInt(coordinates[1]));
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
}
