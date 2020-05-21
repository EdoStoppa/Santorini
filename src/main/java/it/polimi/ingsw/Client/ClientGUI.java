package it.polimi.ingsw.Client;

import it.polimi.ingsw.Controller.MiniController.BaseMiniController;
import it.polimi.ingsw.Controller.MiniController.MiniController;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.HelpMessage;
import it.polimi.ingsw.Message.ServerMessage.ServerMessage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientGUI extends Client{
    String idPlayer = null;
    private MiniController miniController;

    public ClientGUI(String ip, int port) {
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




    private boolean getName(String s){
        String[] splitted = s.split(" ");

        if(splitted.length == 2)
            if(splitted[0].equals("Accepted")){
                this.idPlayer = splitted[1];
                return true;
            }

        return false;
    }





    public Thread asyncWriteToSocketGUI(PrintWriter socketOut) {
        Thread t=new Thread(() -> {
            try{
                StringBuilder sBuilder = new StringBuilder();
                while (isActive()) {
                    String message=null;
                    synchronized(this){
                        if (this.miniController != null) {
                            sBuilder.delete(0, 100);
                            sBuilder.append("Sorry, your choice is invalid. Please try again");
                            if (this.miniController.checkPos(message, playSpace, sBuilder)) {
                                socketOut.println(this.miniController.getMessage(message));
                                socketOut.flush();
                                playSpace.reset();
                                miniController = null;
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

        return t;
    }

    @Override
    public void run() throws IOException {
        Socket socket= new Socket(ip,port);
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        Scanner stdin= new Scanner(System.in);

        try {
            Thread t0 =asyncReadFromSocket(socketIn);
            t0.join();
        }catch (InterruptedException | NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }

    }

    public void setMiniController(MiniController miniController) {
        this.miniController = miniController;
    }
}
