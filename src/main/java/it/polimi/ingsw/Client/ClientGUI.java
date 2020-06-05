package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.GraphicElements.Board.BoardScene;
import it.polimi.ingsw.Client.GraphicElements.SceneBuilder;
import it.polimi.ingsw.Controller.MiniController.BaseMiniController;
import it.polimi.ingsw.Controller.MiniController.MiniController;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.HelpMessage;
import it.polimi.ingsw.Message.MoveMessages.RemovedPlayerMessage;
import it.polimi.ingsw.Message.MoveMessages.ServerMoveMessage;
import it.polimi.ingsw.Message.ServerMessage.GodRecapMessage;
import it.polimi.ingsw.Message.ServerMessage.PlaceFirstConstructorMessage;
import it.polimi.ingsw.Message.ServerMessage.ServerMessage;
import it.polimi.ingsw.Message.TileToShowMessages.TileToShowMessage;
import it.polimi.ingsw.Message.WinMessage;
import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Model.PossiblePhases;
import javafx.application.Platform;
import javafx.scene.Scene;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Map;
import java.util.NoSuchElementException;


public class ClientGUI extends Client implements EventHandler{
    private String idPlayer = null;
    private MiniController miniController;
    private PrintWriter socketOut;
    public Map<String, God> playerGodMap;
    LocalTime lastPingTime;
    private final Object ipLock = new Object();

    public ClientGUI(String ip, int port){
        super(ip, port);
    }

    @Override
    public Thread asyncReadFromSocket(ObjectInputStream socketIn) {
        Thread t= new Thread(() -> {
            try {
                while(isActive()){
                    Object inputObject = socketIn.readObject();

                    if(inputObject.equals(true)){
                        synchronized (ipLock){
                            managePing();
                        }
                    } synchronized(this){
                        if(inputObject instanceof String) {
                            manageStringGUI((String) inputObject);
                        }else if (inputObject instanceof ServerMessage){
                            manageServerMessageGUI((ServerMessage)inputObject);
                        }else if (inputObject instanceof GameMessage){
                            manageGameMessageGUI((GameMessage)inputObject);
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

    private void manageGameMessageGUI(GameMessage inputObject) {
        boolean isMyTurn = idPlayer.equals(inputObject.getIdPlayer());
        inputObject.autoSetMessage(isMyTurn, false);
        BoardScene.setYourTurn(isMyTurn);
        if(inputObject instanceof ServerMoveMessage) {
            updatePlaySpaceGUI(inputObject);
            BoardScene.newText(inputObject.getMessage());
            System.out.println(inputObject.getMessage());
            System.out.println();
            return;
        }
        if(inputObject instanceof TileToShowMessage){
            if(isMyTurn) {
                BoardScene.setPhase(inputObject.getPhase());
                this.miniController = ((TileToShowMessage) inputObject).getMiniController();
                updatePlaySpaceGUI(inputObject);
            }
            BoardScene.setInit(false);
            if (inputObject.getPhase() == PossiblePhases.CHOOSE_CONSTRUCTOR || inputObject.getPhase()==PossiblePhases.SPECIAL_CHOOSE_CONSTRUCTOR){
                BoardScene.newText(" ");}
            BoardScene.newText(inputObject.getMessage());
            System.out.println(inputObject.getMessage());
            playSpace.printPlaySpace();
        }else if(inputObject instanceof RemovedPlayerMessage) {
            updatePlaySpaceGUI(inputObject);
            if (isMyTurn)
            winScene(false);
            playSpace.printPlaySpace();
        } else if(inputObject instanceof WinMessage){
            updatePlaySpaceGUI(inputObject);
            winScene(isMyTurn);
            setActive(false);
        }
        inputObject.updateGUI(playSpace);
        playSpace.printPlaySpace();
    }

    private synchronized void managePing(){
        lastPingTime = LocalTime.now();
    }

    private void manageStringGUI(String input){

        if(input.startsWith(HelpMessage.noAnswer)){
            System.out.println(input.substring(HelpMessage.noAnswer.length()));
            System.out.println();
        } else {
            //This statement is useless, but somehow prevent a hideous bug
            //(we seriously don't know how or why, but this fixes the problem)
            System.out.print("");
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

    private void manageServerMessageGUI(ServerMessage inputObject){
        if (inputObject instanceof GodRecapMessage) {
            if (!((GodRecapMessage) inputObject).getFirstPlayer().equals(idPlayer)) {
                System.out.println(inputObject.getMessage());
            }
            this.playerGodMap = ((GodRecapMessage) inputObject).getPlayerGodMap();
            return;
        }
        if (inputObject instanceof PlaceFirstConstructorMessage){
            BoardScene.newText(inputObject.getMessage());
        }
        this.miniController=inputObject.getMiniController();
        update(inputObject);
    }

    private boolean getName(String s){
        String[] splitted = s.split(" ");

        if(splitted[0].equals("Accepted")){
            this.idPlayer = s.substring(splitted[0].length()+1);
            checkName(true);
            return true;
        }else if(s.equals(HelpMessage.taken)) {
            checkName(false);
        }

        return false;
    }

    public Thread asyncCheckConnection(){
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

    public void asyncWriteToSocketGUI(String message) {
        StringBuilder sBuilder = new StringBuilder();
        if (this.miniController != null) {
            if (this.miniController.checkPosGui(message, playSpace, sBuilder)) {
                socketOut.println(this.miniController.getMessageGui(message));
                socketOut.flush();
                System.out.println();
                playSpace.reset();
                miniController = null;
            } else {
                System.out.println(sBuilder);
            }
        } else {
            System.out.println("Now you can't make a move. Please wait");
        }

    }

    @Override
    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        socketOut = new PrintWriter(socket.getOutputStream());
        try {
            Thread t0 = asyncReadFromSocket(socketIn);
            Thread t1 = asyncCheckConnection();
        }catch (NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        }
    }

    public void checkName(boolean check){
        Platform.runLater(()->{
            if(check) {
                Scene wait = new Scene(SceneBuilder.handeScene("wait"), 800, 710);
                ClientGuiApp.getPrimaryStage().setScene(wait);
            }else{
                Scene newName= new Scene(SceneBuilder.ChooseName("this name is already taken"),800,710);
                ClientGuiApp.getPrimaryStage().setScene(newName);
            }
        });
    }

    public  static void winScene(boolean win){
        Platform.runLater(()->{
            if (win){
                Scene SceneWin =new Scene(SceneBuilder.handeScene("you win"),800,710);
                ClientGuiApp.getPrimaryStage().setScene(SceneWin);
            }else{
                Scene SceneLose =new Scene(SceneBuilder.handeScene("you lose"),800,710);
                ClientGuiApp.getPrimaryStage().setScene(SceneLose);
            }
        });

    }

    @Override
    public void update(ServerMessage message) {
        Platform.runLater(message::buildScene);
    }

    @Override
    public void updatePlaySpaceGUI(GameMessage message) {
        Platform.runLater(()->message.updateGUI(playSpace));
    }

}
