package it.polimi.ingsw.Client;

import it.polimi.ingsw.Controller.MiniController.BaseMiniController;
import it.polimi.ingsw.Controller.MiniController.MiniController;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.HelpMessage;
import it.polimi.ingsw.Message.ServerMessage.ServerMessage;
import it.polimi.ingsw.Message.TileToShowMessages.TileToShowMessage;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class ClientCLI extends Client{
    private MiniController miniController;

    public ClientCLI(String ip, int port) {
        super(ip, port);
    }

    public void setMiniController(MiniController miniController) {
        this.miniController = miniController;
    }

    @Override
    public Thread asyncWriteToSocket(final Scanner stdin, final PrintWriter socketOut){
        Thread t=new Thread(() -> {
            try{
                StringBuilder sBuilder = new StringBuilder();
                while (isActive()){
                    String inputLine = stdin.nextLine();
                    if(this.miniController != null){
                        sBuilder.delete(0, 100);
                        sBuilder.append("Sorry, your choice is invalid. Please try again");
                        if(this.miniController.checkPos(inputLine, playSpace, sBuilder)){
                            socketOut.println(this.miniController.getMessage(inputLine));
                            socketOut.flush();
                            playSpace.reset();
                            miniController = null;
                            System.out.println("Choice accepted");
                        } else {
                            System.out.println(sBuilder);
                        }
                    } else {
                        System.out.println("Now you can't make a move. Please wait");
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

                    if(inputObject instanceof String){
                        this.miniController = new BaseMiniController();
                        System.out.println((String)inputObject);
                    } else if (inputObject instanceof ServerMessage){
                        manageServerMessage((ServerMessage)inputObject);
                    } else if (inputObject instanceof GameMessage){
                        manageGameMessage((GameMessage)inputObject);
                    }
                }
            }catch (Exception e){
                setActive(false);
            }

        });
        t.start();
        return t;
    }

    private void manageServerMessage(ServerMessage inputObject) {

    }

    private void manageGameMessage(GameMessage inputObject) {
        if(inputObject instanceof TileToShowMessage){
            String[] isYouTurn = inputObject.getMessage().split(" ");

            if(isYouTurn[0].equals("Choose")) {
                this.miniController = ((TileToShowMessage) inputObject).getMiniController();
                //System.out.println("Escape Sequence to wipe everything");
                //inputObject.updatePlaySpace(playSpace);
                playSpace.printPlaySpace();
            }
            System.out.println(inputObject.getMessage());
        } /*else if(inputObject instanceof WinMessage){

        }*/ else {
            //inputObject.updatePlaySpace(playSpace);
            //System.out.println("Escape Sequence to wipe everything");
            playSpace.printPlaySpace();
            if(!inputObject.getMessage().equals(HelpMessage.endedPhase))
                System.out.println(inputObject.getMessage());
        }

    }
}
