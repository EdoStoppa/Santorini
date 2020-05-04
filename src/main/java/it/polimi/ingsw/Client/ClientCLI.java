package it.polimi.ingsw.Client;

import it.polimi.ingsw.Controller.MiniController.MiniController;

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

}
