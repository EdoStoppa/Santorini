package it.polimi.ingsw.Client;

import it.polimi.ingsw.Controller.MiniController.*;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.HelpMessage;
import it.polimi.ingsw.Message.MoveMessages.RemovedPlayerMessage;
import it.polimi.ingsw.Message.MoveMessages.ServerMoveMessage;
import it.polimi.ingsw.Message.ServerMessage.*;
import it.polimi.ingsw.Message.TileToShowMessages.TileToShowMessage;
import it.polimi.ingsw.Message.WinMessage;
import it.polimi.ingsw.Model.God;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * this class implements client for CLI's version
 */
public class ClientCLI extends Client{
    private PrintWriter socketOut;
    private final Object ipLock = new Object();
    private LocalTime lastPingTime;

    private String idPlayer = null;
    private MiniController miniController;
    private Map<String, God> playerGodMap;


    public ClientCLI(String ip, int port) {
        super(ip, port);
    }

    @Override
    public void run() throws IOException {
        System.out.println("\n\n" + HelpMessage.santorini);
        Scanner stdin = new Scanner(System.in);
        Socket socket;
        String in;

        while(true){
            try {
                System.out.println("\nPlease enter the server ip and connection port separated by a space! (If you want to play locally, just hit enter)");
                in = stdin.nextLine();
                if (in.equals("")) {
                    socket = new Socket(ip, port);
                    break;
                }
                System.out.println("\nPlease wait...\n");

                String[] s = in.split(" ");
                if(s.length == 2){
                    socket = new Socket(s[0], Integer.parseInt(s[1]));
                    this.ip = s[0];
                    this.port =  Integer.parseInt(s[1]);
                    break;
                } else {
                    throw new Exception();
                }
            } catch(Exception e){
                System.out.println("The connection couldn't be established, please try again!\n");
            }
        }

        System.out.println("Connection established!\n");
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        socketOut = new PrintWriter(socket.getOutputStream());

        try {
            Thread t0 = asyncReadFromSocket(socketIn);
            Thread t1 = asyncWriteToSocket(stdin, socketOut);
            Thread t2 = asyncManagePing();
            Thread t3 = asyncManagePong();
            t0.join();
            t1.join();
            t2.join();
            t3.join();
        }catch (InterruptedException | NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }


    //------------------------------- Methods used to manage read/write on socket -------------------------------

    /**
     * This method initialize a new thread that will be used to process the user input, using as a support
     * the current miniController (if present). In general is able to respond to a wrong (o when is not the
     * turn of the player) input without checking the server.
     *
     * @param stdin The Scanner used to process the input from the user
     * @param socketOut The printWriter used to send to the server the user input
     *
     * @return The actual thread
     */
    public Thread asyncWriteToSocket(final Scanner stdin, final PrintWriter socketOut){
        Thread t=new Thread(() -> {
            try{

                StringBuilder sBuilder = new StringBuilder();
                while (isActive()) {
                    String inputLine = stdin.nextLine();
                    synchronized(this){
                        if (this.miniController != null) {
                            sBuilder.delete(0, 100);
                            sBuilder.append("Sorry, your choice is invalid. Please try again");
                            if (this.miniController.checkPos(inputLine, playSpace, sBuilder)) {
                                String out = this.miniController.getMessage(inputLine);
                                miniController = null;
                                playSpace.reset();
                                System.out.println();
                                synchronized (ipLock){
                                    socketOut.println(out);
                                    socketOut.flush();
                                }
                            } else {
                                System.out.println(sBuilder);
                            }
                        } else {
                            System.out.println("Now you can't make a move. Please wait");
                        }
                    }
                }
            }catch (Exception e){
                setActive(false);
            }
        });
        t.start();
        return t;
    }

    @Override
    public  Thread asyncReadFromSocket(final ObjectInputStream socketIn){
        Thread t= new Thread(() -> {
            try {
                while(isActive()){
                    Object inputObject = socketIn.readObject();

                    if(inputObject.equals(true)){
                        synchronized (ipLock){
                            updatePing();
                        }
                    } else {
                        synchronized(this){
                            if (inputObject instanceof ServerMessage){
                                manageServerMessage((ServerMessage)inputObject);
                            } else if (inputObject instanceof GameMessage){
                                manageGameMessage((GameMessage)inputObject);
                            } else if(inputObject instanceof String){
                                manageString((String)inputObject);
                            }
                        }
                    }
                }
            }catch (Exception e){
                setActive(false);
            }

        });
        t.start();
        return t;
    }


    //------------------------------- Methods used to check the connection between client and server -------------------------------

    /**
     * This method creates a thread used to check if the connection is still alive between client and server.
     * Every 7 seconds the thread wakes up and check if while it was sleeping another ping was received.
     * If no ping was received it means that the connection was lost, and proceed to end the game.
     *
     * @return The actual thread
     */
    public Thread asyncManagePing(){
        Thread t = new Thread(() ->{
            LocalTime lastThreadTime = LocalTime.now();
            while(isActive()){
                try{
                    Thread.sleep(7000);
                } catch(InterruptedException e){
                    setActive(false);
                    System.out.println("\n\nSomething went horribly wrong, please restart the game");
                }

                synchronized(ipLock){
                    //System.out.println("Checking Ping, Thread time = " + lastThreadTime.toString() + " and Ping time = " + lastPingTime.toString());
                    if(lastThreadTime.equals(lastPingTime)){
                        if(isActive())
                            System.out.println("The Server connection was lost, please restart the game");
                        setActive(false);
                    } else {
                        lastThreadTime = lastPingTime;
                    }
                }

            }
        });
        t.start();
        return t;
    }

    public Thread asyncManagePong(){
        Thread t = new Thread(() ->{
            while(isActive()){
                pong();

                try{
                    Thread.sleep(5000);
                } catch(InterruptedException e){
                    setActive(false);
                    System.out.println("Game Interrupted");
                }
            }
        });
        t.start();
        return t;
    }

    public void pong(){
        synchronized (ipLock) {
            socketOut.println(HelpMessage.pong);
            socketOut.flush();
        }
    }

    /**
     * Method used to update the last ping received
     */
    private synchronized void updatePing(){
        lastPingTime = LocalTime.now();
    }


    //------------------------------- Methods used to manage incoming messages from server -------------------------------

    @Override
    public void manageString(String input){

        if(input.startsWith(HelpMessage.noAnswer)){
            System.out.println(input.substring(HelpMessage.noAnswer.length()));
            System.out.println();
        } else {
            if(idPlayer == null) {
                if (!getName(input)) {
                    this.miniController = new BaseMiniController();
                    System.out.println(input);
                }
            } else{
                this.miniController = new BaseMiniController();
                System.out.println(input);
            }
        }
    }

    @Override
    public void manageServerMessage(ServerMessage inputObject) {

        if (inputObject instanceof GodRecapMessage) {
            String name = ((GodRecapMessage) inputObject).getFirstPlayer();
            if (!name.equals(idPlayer)) {
                System.out.println("\u001b[2J\u001b[H");
                System.out.println(inputObject.getMessage() + "\nPlease wait while " + name + " is choosing where to place a constructor");
            }
            this.playerGodMap = ((GodRecapMessage) inputObject).getPlayerGodMap();
            return;
        }

        if (inputObject instanceof PlaceFirstConstructorMessage) {
            if (((PlaceFirstConstructorMessage) inputObject).isFirst()) {
                System.out.println("\u001b[2J\u001b[H");
                playSpace.printPlaySpace();
                if(playerGodMap != null)
                    System.out.println((new GodRecapMessage(playerGodMap, null)).getMessage());
            }
        }

        System.out.println(inputObject.getMessage() + (inputObject instanceof PlaceFirstConstructorMessage? " typing the position (ex. 2,3)" : ""));
        this.miniController = inputObject.getMiniController();
    }

    @Override
    public void manageGameMessage(GameMessage inputObject) {
        boolean isMyTurn = idPlayer.equals(inputObject.getIdPlayer());
        inputObject.autoSetMessage(isMyTurn, true);

        if(inputObject instanceof TileToShowMessage){
            if(isMyTurn) {
                this.miniController = ((TileToShowMessage) inputObject).getMiniController();
                System.out.println("\u001b[2J\u001b[H");
                inputObject.updatePlaySpace(playSpace);
                playSpace.printPlaySpace();
            }
            System.out.println(inputObject.getMessage());
        }else if(inputObject instanceof WinMessage){
            System.out.println("\u001b[2J\u001b[H");
            playSpace.printPlaySpace();
            System.out.println(inputObject.getMessage());
            System.out.println("\nThanks for playing!\nIf you want to restart the game, close this session and restart the application.");
            setActive(false);
        } else {
            inputObject.updatePlaySpace(playSpace);
            System.out.println("\u001b[2J\u001b[H");
            playSpace.printPlaySpace();

            if(inputObject instanceof ServerMoveMessage) {
                System.out.println(inputObject.getMessage());
                System.out.println();
                return;
            }

            if(!isMyTurn || inputObject instanceof RemovedPlayerMessage)
                System.out.println(inputObject.getMessage());
        }

    }


    //------------------------------- Miscellaneous -------------------------------

    /**
     * Method used to get the accepted name for the player
     *
     * @param s String returned by the Server
     *
     * @return Boolean that indicates if the name is accepted ora not
     */
    private boolean getName(String s){
        String[] splitted = s.split(" ");

        if(splitted[0].equals("Accepted")){
            this.idPlayer = s.substring(splitted[0].length()+1);
            return true;
        }

        return false;
    }

}
