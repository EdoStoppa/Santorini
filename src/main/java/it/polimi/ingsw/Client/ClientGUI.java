package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.GraphicElements.AlertBox;
import it.polimi.ingsw.Client.GraphicElements.Board.BoardScene;
import it.polimi.ingsw.Client.GraphicElements.SceneBuilder;
import it.polimi.ingsw.Controller.MiniController.BaseMiniController;
import it.polimi.ingsw.Controller.MiniController.MiniController;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.HelpMessage;
import it.polimi.ingsw.Message.MoveMessages.RemovedPlayerMessage;
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
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientGUI extends Client implements EventHandler{
    private String idPlayer = null;
    private MiniController miniController;
    private PrintWriter socketOut;
    private static Map<String, God> playerGodMap;
    LocalTime lastPingTime;
    private final Object ipLock = new Object();

    public ClientGUI(String ip, int port){
        super(ip, port);
    }


    @Override
    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        socketOut = new PrintWriter(socket.getOutputStream());
        SceneBuilder.initImages();
        try {
            Thread t0 = asyncReadFromSocket(socketIn);
            Thread t1 = asyncManagePing();
        }catch (NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        }
    }

    public void writeToSocketGUI(String message) {
        StringBuilder sBuilder = new StringBuilder();
        if (this.miniController != null) {
            if (this.miniController.checkPosGui(message, playSpace, sBuilder)) {
                String out = this.miniController.getMessageGui(message);
                miniController = null;
                playSpace.disHighlightsTile();
                playSpace.reset();
                System.out.println();
                socketOut.println(out);
                socketOut.flush();
            } else {
                System.out.println(sBuilder);
            }
        } else {
            System.out.println("Now you can't make a move. Please wait");
        }

    }

    @Override
    public Thread asyncReadFromSocket(ObjectInputStream socketIn) {
        Thread t= new Thread(() -> {
            try {
                while(isActive()){
                    Object inputObject = socketIn.readObject();

                    if(inputObject.equals(true)){
                        synchronized (ipLock){
                            updatePing();
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

    private void manageGameMessageGUI(GameMessage inputObject) {
        boolean isMyTurn = idPlayer.equals(inputObject.getIdPlayer());
        inputObject.autoSetMessage(isMyTurn, false);
        BoardScene.setYourTurn(isMyTurn);
        System.out.println(isMyTurn);

        if(inputObject instanceof TileToShowMessage){
            if(isMyTurn) {
                BoardScene.setPhase(inputObject.getPhase());
                System.out.println(BoardScene.getPhase());
                this.miniController = ((TileToShowMessage) inputObject).getMiniController();
                updateText(inputObject);
                updatePlaySpaceGUI(inputObject);
                BoardScene.setInit(false);
            } else {
                updateText(inputObject);
            }
            playSpace.printPlaySpace();
            System.out.println(inputObject.getMessage());
            return;
        }else if(inputObject instanceof RemovedPlayerMessage) {
            updateText(inputObject);
            updatePlaySpaceGUI(inputObject);
            System.out.println();
            if (isMyTurn && playSpace.CountPlayerRemains(((RemovedPlayerMessage) inputObject).getConstructorMatrix())==4){
                Platform.runLater(()->{
                    boolean answer=AlertBox.CheckDome("do you want continue watch the game");
                    if (!answer){
                        SceneBuilder.endGameTransition(false);
                        setActive(false);
                    }
                }); }
            else if(isMyTurn){
                SceneBuilder.endGameTransition(false);
                setActive(false);
            }
            return;
        } else if(inputObject instanceof WinMessage){
            updateText(inputObject);
            updatePlaySpaceGUI(inputObject);
            SceneBuilder.endGameTransition(isMyTurn);
            setActive(false);
            return;
        }

        /*if(!isMyTurn)
            updateText(inputObject);*/
        updatePlaySpaceGUI(inputObject);
        playSpace.printPlaySpace();
    }

    private void manageStringGUI(String input){
        executeSpecialString(input);

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

    private void manageServerMessageGUI(ServerMessage inputObject){
        if (inputObject instanceof GodRecapMessage) {
            String name = ((GodRecapMessage) inputObject).getFirstPlayer();
            if (!name.equals(idPlayer)) {
                BoardScene.newText("Please wait while " + name + " is choosing where to place a constructor");
            }
            playerGodMap = ((GodRecapMessage) inputObject).getPlayerGodMap();
            Platform.runLater(()-> ClientGuiApp.getPrimaryStage().setScene(new Scene(BoardScene.createContent(),ClientGuiApp.width,ClientGuiApp.height)));
            return;
        }
        if (inputObject instanceof PlaceFirstConstructorMessage){
            BoardScene.newText(inputObject.getMessage());
        }
        this.miniController=inputObject.getMiniController();
        update(inputObject);
    }

    private synchronized void updatePing(){
        lastPingTime = LocalTime.now();
    }

    //---------- Methods to manage all the String messages ----------
    private void executeSpecialString(String input){
        if(input.equals(HelpMessage.forcedClose)){
            Platform.runLater(()->{
                Scene error= new Scene(SceneBuilder.handeScene(input),ClientGuiApp.width,ClientGuiApp.height);
                setActive(false);
                ClientGuiApp.getPrimaryStage().setScene(error);
            });
            return;
        }

        if (input.equals(HelpMessage.enterName)){
            Platform.runLater(()->{
                Scene scene=new Scene(SceneBuilder.ChooseName(HelpMessage.enterName),ClientGuiApp.width,ClientGuiApp.height);
                ClientGuiApp.getPrimaryStage().setScene(scene);
            });
            return;
        }

        if (input.equals(HelpMessage.gameMode)) {
            Platform.runLater(() -> {
                Scene scene = new Scene(SceneBuilder.ChooseGameMode(HelpMessage.gameMode), ClientGuiApp.width, ClientGuiApp.height);
                ClientGuiApp.getPrimaryStage().setScene(scene);
            });
            return;
        }

        if(input.startsWith(HelpMessage.noAnswer + "Please wait while"))
            BoardScene.newText(input.substring(HelpMessage.noAnswer.length()));
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

    public void checkName(boolean check){
        Platform.runLater(()->{
            if(check) {
                Scene wait = new Scene(SceneBuilder.handeScene("wait"), ClientGuiApp.width, ClientGuiApp.height);
                ClientGuiApp.getPrimaryStage().setScene(wait);
            }else{
                Scene newName= new Scene(SceneBuilder.ChooseName("this name is already taken"),ClientGuiApp.width,ClientGuiApp.height);
                ClientGuiApp.getPrimaryStage().setScene(newName);
            }
        });
    }

    //---------- Methods to update and change the client GUI ----------
    @Override
    public void update(ServerMessage message) {
        Platform.runLater(message::buildScene);
    }

    @Override
    public void updatePlaySpaceGUI(GameMessage message) {
        Platform.runLater(()->{ message.updateGUI(playSpace); });
    }

    public void updateText(GameMessage message){
        Platform.runLater(()-> {
            if (message.getPhase() == PossiblePhases.CHOOSE_CONSTRUCTOR || message.getPhase() == PossiblePhases.SPECIAL_CHOOSE_CONSTRUCTOR)
                BoardScene.clearText();
            BoardScene.newText(message.getMessage());
        });
    }

    //---------- Miscellaneous ----------
    public static Map<String, God> getPlayerGodMap() {
        return playerGodMap;
    }


}
