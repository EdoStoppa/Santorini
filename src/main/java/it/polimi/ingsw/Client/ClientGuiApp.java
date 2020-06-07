package it.polimi.ingsw.Client;



import it.polimi.ingsw.Client.GraphicElements.SceneBuilder;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;


public class ClientGuiApp extends Application implements Serializable {
    private static final long serialVersionUID = 1L;
    private static ClientGUI  client;
    private static Stage primaryStage;
    private static SceneBuilder sceneBuilder;

    public static ClientGUI getClient() {
        return client;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ClientGuiApp.primaryStage =primaryStage;
        sceneBuilder=new SceneBuilder();

        Scene scene1= new Scene(CreateContent("Please enter the server ip and connection port(If you want to play locally, just hit enter)"),810,700);



        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    public static Parent CreateContent (String message){
        VBox layout=new VBox(20);
        Text textName= new Text(message);
        HBox firstLine=new HBox(20);
        HBox secondLine=new HBox(20);
        Text textIp= new Text("insert ip");
        Text textPort= new Text("insert port");
        TextField serverIp= new TextField();
        TextField portField= new TextField();
        firstLine.getChildren().addAll(textIp,serverIp);
        secondLine.getChildren().addAll(textPort,portField);
        Button go= new Button("enter");
        go.setOnAction(e->{
            if(serverIp.getText().equals("") && portField.getText().equals("")){
                client=new ClientGUI("127.0.0.1", 54321);
                try {
                    client.run();
                } catch (IOException ioException) {
                    Scene scene=new Scene(CreateContent("The connection couldn't be established, please try again!\n"),810,700);
                    primaryStage.setScene(scene);
                }
            }else{
                client= new ClientGUI(serverIp.getText(),Integer.parseInt(portField.getText()));
                try {
                    client.run();
                } catch (IOException ioException) {
                    Scene scene=new Scene(CreateContent("The connection couldn't be established, please try again!\n"),810,700);
                    primaryStage.setScene(scene);
                }
            }});
        serverIp.setMaxWidth(150);
        portField.setMaxWidth(150);
        layout.setAlignment(Pos.CENTER);
        firstLine.setAlignment(Pos.CENTER);
        secondLine.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(textName,firstLine,secondLine,go);
        return layout;

    }





}
