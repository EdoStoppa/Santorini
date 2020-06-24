package it.polimi.ingsw.Client.GraphicElements;

import it.polimi.ingsw.Client.Client;
import it.polimi.ingsw.Client.ClientGUI;
import it.polimi.ingsw.Client.ClientGuiApp;
import it.polimi.ingsw.Model.God;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class AlertBox {

    public static void displayError(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(message);

        alert.initOwner(ClientGuiApp.getPrimaryStage());
        alert.showAndWait();

    }

    static  boolean answer;

    public static boolean checkDom(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(message);

        alert.initOwner(ClientGuiApp.getPrimaryStage());
        Optional<ButtonType> result = alert.showAndWait();
         if(result.get() == ButtonType.OK){
             answer=true;
             alert.close();
         }
else if(result.get() == ButtonType.CANCEL){
             answer=false;
             alert.close();
             }

        return answer;
    }

    public static boolean CheckDome(String message){
        Stage window= new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initOwner(ClientGuiApp.getPrimaryStage());
        window.isAlwaysOnTop();
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

    public static void displayGod() {
        Stage window= new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initOwner(ClientGuiApp.getPrimaryStage());
        window.isAlwaysOnTop();
        window.setMinWidth(500);
        BorderPane borderPane=new BorderPane();
        Map<String, God> playerGodMap=ClientGUI.getPlayerGodMap();
        ArrayList<String> keys = new ArrayList<>(playerGodMap.keySet());
        VBox layout=new VBox(20);
        Text playerOne= new Text(keys.get(0)+" ---> "+playerGodMap.get(keys.get(0)).getGodName()+"\n"+playerGodMap.get(keys.get(0)).getGodPower());
        playerOne.setWrappingWidth(450);
        playerOne.setFont(Font.font(16));
        Text playerTwo= new Text(keys.get(1)+" ---> "+playerGodMap.get(keys.get(1)).getGodName()+"\n"+playerGodMap.get(keys.get(1)).getGodPower());
        playerTwo.setWrappingWidth(450);
        playerTwo.setFont(Font.font(16));
        layout.getChildren().addAll(playerOne,playerTwo);
        if(keys.size()==3){
            Text playerThree= new Text(keys.get(2)+" ---> "+playerGodMap.get(keys.get(2)).getGodName()+"\n"+playerGodMap.get(keys.get(2)).getGodPower());
            playerThree.setWrappingWidth(450);
            playerThree.setFont(Font.font(16));
            layout.getChildren().add(playerThree);
        }
        HBox fourLine= new HBox();
        Button exit= new Button("exit");
        fourLine.getChildren().add(exit);
        exit.setOnAction(e->window.close());
        fourLine.setAlignment(Pos.CENTER);
        layout.getChildren().add(fourLine);
        borderPane.setCenter(layout);
        Scene scene4= new Scene(borderPane);
        window.setScene(scene4);
        window.showAndWait();
    }
}
