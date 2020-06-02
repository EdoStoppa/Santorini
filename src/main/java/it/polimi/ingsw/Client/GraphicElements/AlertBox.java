package it.polimi.ingsw.Client.GraphicElements;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    public static void displayError(String message){
        Stage window= new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(250);
        Label label=new Label(message);
        Button closeButton= new Button("ok");
        closeButton.setOnAction(e->window.close());
        VBox layout=new VBox(10);
        layout.getChildren().addAll(label,closeButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene=new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    static  boolean answer;

    public static boolean CheckDome(String message){
        Stage window= new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(250);
        Label label=new Label(message);
        Button yes= new Button("yes");
        yes.setOnAction(e->{
           answer=true;
           window.close();
        });
        Button no= new Button("no");
        no.setOnAction(e->{
            answer=false;
            window.close();
        });

        VBox layout=new VBox(10);
        HBox Answers= new HBox(50);
        Answers.getChildren().addAll(yes,no);
        layout.getChildren().addAll(label,Answers);
        layout.setAlignment(Pos.CENTER);
        Scene scene=new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
