package it.polimi.ingsw;

import it.polimi.ingsw.Client.Client;

import it.polimi.ingsw.Client.ClientGUI;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientGuiApp extends Application {
   private static ClientGUI  client = new ClientGUI("127.0.0.1", 12345);

    public static void main(String[] args) {
        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
    Scene layoutIniziale= new Scene(cratefirst(),800,710);
    primaryStage.setScene(layoutIniziale);
    primaryStage.show();
    }

    private Parent cratefirst(){
        VBox layout= new VBox(50);
        Label welcome= new Label("welcome to santorini\n 2 or 3 player mode?");
        HBox Buttons= new HBox(150);
        Button twoPlayer= new Button("2 player");
        Button treePlayer=new Button("3 player");
        twoPlayer.setOnAction(e->System.out.println("2 player"));
        treePlayer.setOnAction(e->System.out.println("3 player"));
        Buttons.getChildren().addAll(twoPlayer,treePlayer);
        Buttons.setAlignment(Pos.CENTER);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(welcome,Buttons);

        return  layout;
    }



}
