package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.GraphicElements.Board.BoardScene;
import it.polimi.ingsw.Controller.MiniController.BaseMiniController;
import it.polimi.ingsw.Controller.MiniController.MiniController;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.HelpMessage;
import it.polimi.ingsw.Message.MoveMessages.ServerMoveMessage;
import it.polimi.ingsw.Message.ServerMessage.ServerMessage;
import it.polimi.ingsw.Message.TileToShowMessages.TileToShowMessage;
import it.polimi.ingsw.Message.WinMessage;
import javafx.application.Platform;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;


public class ClientGUI extends Client implements EventHandler{
    private String idPlayer = null;
    private MiniController miniController;
    private PrintWriter socketOut;


    public ClientGUI(String ip, int port){
        super(ip, port);
    }



    @Override
    public Thread asyncReadFromSocket(ObjectInputStream socketIn) {
        Thread t= new Thread(() -> {
            try {
                while(isActive()){
                    Object inputObject = socketIn.readObject();

                    synchronized(this){
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
            BoardScene.newText(inputObject.getMessage());
            System.out.println(inputObject.getMessage());
            playSpace.printPlaySpace();
        }else if(inputObject instanceof WinMessage){
            playSpace.printPlaySpace();
            BoardScene.newText(inputObject.getMessage());
            System.out.println(inputObject.getMessage());
            System.out.println("Thank for playing.\nIf you want to restart the game, close this session and restart the application.");
            setActive(false);
        }else{
            inputObject.updateGUI(playSpace);
            playSpace.printPlaySpace();
        }
    }


    private void manageStringGUI(String input){

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
        this.miniController=inputObject.getMiniController();
        System.out.println(idPlayer);
        update(inputObject);
    }




    private boolean getName(String s){
        String[] splitted = s.split(" ");

        if(splitted[0].equals("Accepted")){
            this.idPlayer = s.substring(splitted[0].length()+1);
            return true;
        }

        return false;
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
            Thread t0 =asyncReadFromSocket(socketIn);
        }catch (NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        }

    }

    public void setMiniController(MiniController miniController) {
        this.miniController = miniController;
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
