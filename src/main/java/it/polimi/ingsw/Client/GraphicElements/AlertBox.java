package it.polimi.ingsw.Client.GraphicElements;

import it.polimi.ingsw.Client.ClientGUI;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

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

    public static void displayGod() {
        Stage window= new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(250);
        BorderPane borderPane=new BorderPane();
        ArrayList<String> keys = new ArrayList<>(ClientGUI.getPlayerGodMap().keySet());
        VBox layout=new VBox(20);
        layout.setAlignment(Pos.CENTER);
        HBox firstLine= new HBox(20);
        Label nameOne= new Label(keys.get(0)+" --->");
        nameOne.setFont(Font.font(32));
        ImageView IWFirst = new ImageView(SceneBuilder.imageHashMap.get(ClientGUI.getPlayerGodMap().get(keys.get(0)).getGodName()));
        SceneBuilder.setGodImage(IWFirst);
        Text firstDescription=new Text(ClientGUI.getPlayerGodMap().get(keys.get(0)).getGodPower());
        firstDescription.setWrappingWidth(450);
        firstDescription.setFont(Font.font(18));
        firstLine.getChildren().addAll(nameOne,IWFirst,firstDescription);
        HBox secondLine= new HBox(20);
        Label nameTwo= new Label(keys.get(1)+" --->");
        nameTwo.setFont(Font.font(32));
        ImageView IWSecond= new ImageView(SceneBuilder.imageHashMap.get(ClientGUI.getPlayerGodMap().get(keys.get(1)).getGodName()));
        SceneBuilder.setGodImage(IWSecond);
        Text secondDescription=new Text(ClientGUI.getPlayerGodMap().get(keys.get(1)).getGodPower());
        secondDescription.setWrappingWidth(450);
        secondDescription.setFont(Font.font(18));
        secondLine.getChildren().addAll(nameTwo,IWSecond,secondDescription);
        layout.getChildren().addAll(firstLine,secondLine);
        if(keys.size()==3){
            HBox thirdLine= new HBox(20);
            Label nameThree= new Label(keys.get(2)+" --->");
            nameThree.setFont(Font.font(32));
            ImageView IWThird= new ImageView(SceneBuilder.imageHashMap.get(ClientGUI.getPlayerGodMap().get(keys.get(2)).getGodName()));
            SceneBuilder.setGodImage(IWThird);
            Text thirdDescription=new Text(ClientGUI.getPlayerGodMap().get(keys.get(2)).getGodPower());
            thirdDescription.setWrappingWidth(450);
            thirdDescription.setFont(Font.font(18));
            thirdLine.getChildren().addAll(nameThree,IWThird,thirdDescription);
            layout.getChildren().add(thirdLine);
        }
        HBox fourLine= new HBox();
        Button exit= new Button("exit");
        fourLine.getChildren().add(exit);
        exit.setOnAction(e->window.close());
        fourLine.setAlignment(Pos.CENTER);
        layout.getChildren().add(fourLine);
        borderPane.setCenter(layout);
        Scene scene4= new Scene(borderPane,850,710);
        window.setScene(scene4);
        window.showAndWait();
    }
}
