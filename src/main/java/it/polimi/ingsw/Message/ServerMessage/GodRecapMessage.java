package it.polimi.ingsw.Message.ServerMessage;

import it.polimi.ingsw.Controller.MiniController.MiniController;
import it.polimi.ingsw.Model.God;

import java.util.HashMap;
import java.util.Scanner;

public class GodRecapMessage extends ServerMessage{
    private final HashMap<String, God> playerGodMap;

    public GodRecapMessage(HashMap<String, God> map){
        this.playerGodMap = map;
    }

    @Override
    public MiniController getMiniController() {
        //This block is used only to let the player read which god everyone chose.
        //At the end of it a new message from the server will be ready on the socket
        try{
            Thread.sleep(3000);
        } catch(Exception e){
            return null;
        }

        return null;
    }

    @Override
    public String getMessage() {
        String s = "Everyone has chosen a God, here's a recap:";
        for(String idPlayer : playerGodMap.keySet()){
            God god = playerGodMap.get(idPlayer);
            s = s + idPlayer + " chose: " + god.getGodName() + "\n";
        }
        return s;
    }

    @Override
    public void buildScene() {

    }

    public HashMap<String, God> getPlayerGodMap(){
        return this.playerGodMap;
    }
}
