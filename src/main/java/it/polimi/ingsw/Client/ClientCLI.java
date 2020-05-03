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
                while (isActive()){
                    String inputLine = stdin.nextLine();
                    if(this.miniController != null){
                        if(this.miniController.checkPos(inputLine, playSpace)){
                            socketOut.println(this.miniController.getMessage(inputLine));
                            socketOut.flush();
                        } else {
                            System.out.println("Sorry, your choice is invalid. Please try again");
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
