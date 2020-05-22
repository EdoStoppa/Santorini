package it.polimi.ingsw.Client;

import it.polimi.ingsw.ClientGuiApp;
import it.polimi.ingsw.Controller.MiniController.BaseMiniController;
import it.polimi.ingsw.Controller.MiniController.MiniController;
import it.polimi.ingsw.Message.HelpMessage;
import it.polimi.ingsw.Message.ServerMessage.PickGodMessage;
import it.polimi.ingsw.Message.ServerMessage.ServerMessage;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;


public class ClientGUI extends Client{
    String idPlayer = null;
    private MiniController miniController;
    private PrintWriter socketOut;
    ClientGuiApp view;

    public ClientGUI(String ip, int port,ClientGuiApp view){
        super(ip, port);
        this.view=view;
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
        int i;
        if(inputObject instanceof PickGodMessage){
            i=3;
            System.out.println("1");
            ClientGuiApp.switchScene(i);
            System.out.println("djdsaasji");

        }


    }




    private boolean getName(String s){
        String[] splitted = s.split(" ");

        if(splitted.length == 2)
            if(splitted[0].equals("Accepted")){
                this.idPlayer = splitted[1];
                return true;
            }

        return false;
    }







    public void asyncWriteToSocketGUI(String message) {
                                socketOut.println(message);
                                socketOut.flush();

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
}
