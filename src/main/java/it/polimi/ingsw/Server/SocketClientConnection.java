package it.polimi.ingsw.Server;

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
    public ArrayList<God> ChooseGod(int player){
        ArrayList<God> pickGod=new ArrayList<>();
        List<God> allGod=God.getAllGod();
        Integer i, pick=0, count;
        try{
            Scanner in= new Scanner(socket.getInputStream());
            this.asyncSend("chose god from this list:\n");
            for (i=0;i<God.getAllGod().size();i++){
                count=i+1;
                this.asyncSend(count+") "+allGod.get(i).getGodName()+" "+allGod.get(i).getGodSubtitle()+
                        "\n"+allGod.get(i).getGodPower()+"\n\n");
            }
            i=0;
            while (i<player){
                pick=in.nextInt();
                pickGod.add(allGod.get(pick));
                i++;
            }
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        return pickGod;
    }

    /**
     * every plaver chose their god in a 2 player game
     * @param opponent ClientConnection of the other player
     * @param pickPool list of god choose in function ChooseGod
     * @return an ordered list that associates each god with its player
     */
    @Override
    public ArrayList<God> PickGod(ClientConnection opponent,ArrayList<God> pickPool){
        int pick;
        ArrayList<God> PlayerChoice= new ArrayList<>();
        try{
            Scanner in= new Scanner(socket.getInputStream());
            opponent.asyncSend("choose your god:\n 0)"+
                    pickPool.get(0).getGodName()+"  "+ pickPool.get(0)+"\n"+ pickPool.get(0).getGodPower()+
                    "\n\n or \n\n 1)"+pickPool.get(1).getGodName()+pickPool.get(1).getGodSubtitle()+
                            "\n"+pickPool.get(1).getGodPower());

            pick=in.nextInt();
            if(pick==0) {
                PlayerChoice.add(pickPool.get(1));
                PlayerChoice.add(pickPool.get(0));
            }else{
                PlayerChoice.add(pickPool.get(0));
                PlayerChoice.add(pickPool.get(1));
            }

        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        return pickPool;
    }

    /**
     * every plaver chose their god in a 3 player game
     * @param opponent1 ClientConnection of the player who chooses first
     * @param opponent2 ClientConnection of the player who chooses second
     * @param pickPool list of god choose in function ChooseGod
     * @return an ordered list that associates each god with its player
     */
    @Override
    public ArrayList<God> PickGod3P(ClientConnection opponent1,ClientConnection opponent2, ArrayList<God> pickPool){
        int pick;
        ArrayList<God> PlayerChoice= new ArrayList<>();
        try{
            Scanner in= new Scanner(socket.getInputStream());
            opponent1.asyncSend("choose your god:\n0)"+
                    pickPool.get(0).getGodName()+"\n"+pickPool.get(0).getGodSubtitle()+"\n"+pickPool.get(0).getGodPower()+
                    "\n\n or \n\n1)"+ pickPool.get(1).getGodName()+"\n"+pickPool.get(1).getGodSubtitle()+"\n"+pickPool.get(1).getGodPower()+
                    "\n\n or \n\n2)"+pickPool.get(2).getGodName()+"\n"+pickPool.get(2).getGodSubtitle()+"\n"+pickPool.get(2).getGodPower());

            pick=in.nextInt();
            PlayerChoice.add(pickPool.get(pick));
            pickPool.remove(pick);
            opponent2.asyncSend("choose your god:\n"+
                            pickPool.get(0).getGodName()+"\n"+pickPool.get(0).getGodSubtitle()+"\n"+pickPool.get(0).getGodPower()+
                            "\n\n or \n\n1)"+ pickPool.get(1).getGodName()+"\n"+pickPool.get(1).getGodSubtitle()+"\n"+pickPool.get(1).getGodPower());
            pick=in.nextInt();
            PlayerChoice.add(pickPool.get(pick));
            pickPool.remove(pick);
            PlayerChoice.add(pickPool.get(0));

        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        return pickPool;
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

}
