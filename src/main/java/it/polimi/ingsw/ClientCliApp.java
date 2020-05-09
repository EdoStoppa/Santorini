package it.polimi.ingsw;

import it.polimi.ingsw.Client.*;

import java.io.IOException;

public class ClientCliApp {
    public static void main(String[] args){
        Client client = new ClientCLI("127.0.0.1", 12345);
        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
