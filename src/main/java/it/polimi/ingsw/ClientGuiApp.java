package it.polimi.ingsw;

import it.polimi.ingsw.Client.Client;

import it.polimi.ingsw.Client.ClientGUI;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientGuiApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Client client = new ClientGUI("127.0.0.1", 12345);
        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }


    }
}
