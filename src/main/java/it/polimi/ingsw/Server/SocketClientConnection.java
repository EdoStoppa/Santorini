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
import java.util.*;

public class SocketClientConnection extends Observable<String> implements ClientConnection,Runnable {


    private Socket socket;
    private ObjectOutputStream out;
    private Server server;
    private boolean active=true;
    private boolean creation=true;



    public SocketClientConnection(Socket socket,Server server){
        this.server=server;
        this.socket=socket;
    }

    private synchronized boolean isActive(){
        return active;
    }

    @Override
    public void EndCreation() {
        this.creation=false;
    }

    public synchronized boolean isCreation(){
        return creation;
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
        String gameMode = null;
        String read;

        try {in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            send("welcome!\n 2 or 3 player mode");
            read=in.nextLine();
            gameMode=read;
            while(Integer.parseInt(gameMode)!=2 && Integer.parseInt(gameMode)!=3 ) {
                send("please enter a correct number of player");
                read=in.nextLine();
                gameMode=read;
            }
            send(" Enter your name:");
            name = in.nextLine();
            if (Integer.parseInt(gameMode) == 2) {
                System.out.println("facciamo partire la lobby");
                server.lobby2P(this,name);
            } else {
                server.lobby3P(this, name);
            }
            while (isCreation()){

            }
            while (isActive()) {
                read = in.nextLine();
                notify(read);
            }

        }catch (IOException | NoSuchElementException e){
            System.err.println("Error!" + e.getMessage());
        }finally {
            assert gameMode != null;
            close(Integer.parseInt(gameMode));
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
        Thread thread = new Thread(() -> {
            try {
                out.reset();
                out.writeObject(message);
                out.flush();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        });
        thread.start();
    }
    /**
     * the GodlikePlayer choose the God for this game
     * @param player number of player of the game
     * @return list of god chosen
     */
    @Override
    public ArrayList<God> ChooseGod(int player,PickGodMessage pickGodMessage){
        ArrayList<God> pickGod=new ArrayList<>();
        int i=0;
        try{Scanner in = new Scanner(socket.getInputStream());
            this.asyncSend(new PickGodMessage());
            System.out.println("sto prendendo i numeri");
            String pickPool=in.nextLine();
            String[] pick= pickPool.split(",");
            System.out.println("sto prendendo i numeri");
            while (i<player){
                pickGod.add(pickGodMessage.GetGod(Integer.parseInt(pick[i])));
                i++;
            }
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        return pickGod;
    }


    @Override
    public God PickGod(ChosenGodMessage chosenGodMessage){
        String pick=null;
        try{
            Scanner in= new Scanner(socket.getInputStream());
            this.asyncSend(chosenGodMessage);
            pick=in.nextLine();
            while (Integer.parseInt(pick)>chosenGodMessage.getSize()){
                this.asyncSend("please enter a correct god's number");
                pick=in.nextLine();
            }
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        assert pick != null;
        return chosenGodMessage.getChosenGod(Integer.parseInt(pick));
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
    public String ChooseFirstPlayer(OrderGameMessage orderGameMessage){
        String firstPlayer=null;
        boolean check= true;
        try {
            Scanner in= new Scanner(socket.getInputStream());
            this.asyncSend(orderGameMessage);
            firstPlayer = in.nextLine();
            while(check){
                for (int i=0;i<orderGameMessage.getPlayerlist().size();i++){
                    if (firstPlayer.equals(orderGameMessage.getPlayerlist().get(i))) {
                        check = false;
                        break;
                    }
                }
                if (check){
                    asyncSend("enter a correct name player");
                    firstPlayer = in.nextLine();
                }
            }

        }catch (IOException e){
            System.err.println(e.getMessage());
        }

        return firstPlayer;
    }

    @Override
    public Position FirstPlaceConstructor(){
        String Pos="";
        String[] coordinates= new String[2];
        try {Scanner in= new Scanner(socket.getInputStream());
            this.asyncSend(new PlaceFirstConstructorMessage());
            while(Pos.equals("")){
                Pos=in.nextLine();
            }
            coordinates=Pos.split(",");
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        return new Position(Integer.parseInt(coordinates[0]),Integer.parseInt(coordinates[1]));
    }

}
