package it.polimi.ingsw.Client.GraphicElements;

import it.polimi.ingsw.Client.ClientGUI;
import it.polimi.ingsw.Client.ClientGuiApp;
import it.polimi.ingsw.Model.God;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class AlertBox {
    static  boolean answer;

    public static void displayError(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(message);

        alert.initOwner(ClientGuiApp.getPrimaryStage());
        alert.showAndWait();

    }

    public static boolean checkDome(String message){
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.getButtonTypes().addAll(ButtonType.YES,ButtonType.NO);
        alert.setHeaderText(message);
        alert.initOwner(ClientGuiApp.getPrimaryStage());
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.YES){
            answer=true;
            alert.close();
        } else if(result.get() == ButtonType.NO){
            answer=false;
            alert.close();
        }

        return answer;
    }

    public static void displayGod(){
        Map<String, God> playerGodMap=ClientGUI.getPlayerGodMap();
        ArrayList<String> keys = new ArrayList<>(playerGodMap.keySet());
        Alert alert=new Alert(Alert.AlertType.NONE);
        alert.getButtonTypes().add(ButtonType.CLOSE);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Objects.requireNonNull(SceneBuilder.class.getClassLoader().getResource("backgroundImage.css")).toExternalForm());
        dialogPane.setId("displayGod");
        dialogPane.setPrefWidth(450);
        alert.setTitle("God List");
        String god1=keys.get(0)+" ---> "+playerGodMap.get(keys.get(0)).getGodName()+"\n"+playerGodMap.get(keys.get(0)).getGodPower()+"\n";
        String god2=keys.get(1)+" ---> "+playerGodMap.get(keys.get(1)).getGodName()+"\n"+playerGodMap.get(keys.get(1)).getGodPower()+"\n";
        if(keys.size()==3){
            String god3= keys.get(2)+" ---> "+playerGodMap.get(keys.get(2)).getGodName()+"\n"+playerGodMap.get(keys.get(2)).getGodPower();
            alert.setHeaderText(god1+god2+god3);
        }else {
            alert.setHeaderText(god1 + god2);
        }
        alert.initOwner(ClientGuiApp.getPrimaryStage());
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.CLOSE){
            alert.close();
        }

    }
}
