package it.polimi.ingsw.Client.GraphicElements;

import it.polimi.ingsw.Client.ClientGUI;
import it.polimi.ingsw.Client.ClientGuiApp;
import it.polimi.ingsw.Model.God;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * this class create all the AlertBox during initialization and game
 */
public class AlertBox {
    static  boolean answer;

    /**
     * this method create an Alert when the client insert a wrong input
     * @param message string to display
     */
    public static void displayError(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(message);

        alert.initOwner(ClientGuiApp.getPrimaryStage());
        alert.showAndWait();

    }


    /**
     *  <em>CheckDomeMessage</em> used this method to create an AlertBox to check
     * if the player wants to place a dome or a normal level
     * @param message string to display
     * @return true if the player true if the player wants to place dome else false
     */
    public static boolean checkDome(String message){
        Alert alert = new Alert(Alert.AlertType.NONE);
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().addAll(yes,no);
        alert.setHeaderText(message);
        alert.initOwner(ClientGuiApp.getPrimaryStage());

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == yes){
            answer=true;
            alert.close();
        } else if(result.get() == no){
            answer=false;
            alert.close();
        }

        return answer;
    }

    /**
     * this method create an alertBox to summarize the gods of the game
     */
    public static void displayGod(){
        Map<String, God> playerGodMap=ClientGUI.getPlayerGodMap();
        ArrayList<String> keys = new ArrayList<>(playerGodMap.keySet());
        Alert alert=new Alert(Alert.AlertType.NONE);

        ButtonType close = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().add(close);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Objects.requireNonNull(SceneBuilder.class.getClassLoader().getResource("backgroundImage.css")).toExternalForm());
        dialogPane.setId("displayGod");
        dialogPane.setPrefWidth(450);
        alert.setTitle("God List");
        String god1=keys.get(0)+" ---> "+playerGodMap.get(keys.get(0)).getGodName()+"\n"+playerGodMap.get(keys.get(0)).getGodPower()+"\n\n";
        String god2=keys.get(1)+" ---> "+playerGodMap.get(keys.get(1)).getGodName()+"\n"+playerGodMap.get(keys.get(1)).getGodPower()+"\n";
        if(keys.size()==3){
            String god3= "\n" + keys.get(2)+" ---> "+playerGodMap.get(keys.get(2)).getGodName()+"\n"+playerGodMap.get(keys.get(2)).getGodPower();
            alert.setHeaderText(god1+god2+god3);
        }else {
            alert.setHeaderText(god1 + god2);
        }
        alert.initOwner(ClientGuiApp.getPrimaryStage());

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == close){
            alert.close();
        }

    }
}
