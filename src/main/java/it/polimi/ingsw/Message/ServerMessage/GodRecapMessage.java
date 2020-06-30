package it.polimi.ingsw.Message.ServerMessage;

import it.polimi.ingsw.Controller.MiniController.MiniController;
import it.polimi.ingsw.Model.God;

import java.util.Map;

/**
 * This is a special message. For the GUI is used to create a special button that reminds every time the player click it which player
 * has which god card, instead in the CLI is used to show a sort of recap about which player has which god card before the end of the
 * match initialization.
 */
public class GodRecapMessage extends ServerMessage{
    private final Map<String, God> playerGodMap;
    private final String firstPlayer;

    public GodRecapMessage(Map<String, God> playerGodMap, String first){
        this.playerGodMap = playerGodMap;
        this.firstPlayer = first;
    }

    @Override
    public MiniController getMiniController() {
        return null;
    }

    @Override
    public String getMessage() {
        String s = "Here's a recap on who has which god:\n";
        for(String p : playerGodMap.keySet()){
            s += p + " -> " + playerGodMap.get(p).getGodName() + "\n";
        }

        return s;
    }

    @Override
    public void buildScene() {

    }

    public Map<String, God> getPlayerGodMap(){
        return this.playerGodMap;
    }

    public String getFirstPlayer(){
        return this.firstPlayer;
    }
}
