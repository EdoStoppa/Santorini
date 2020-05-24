package it.polimi.ingsw.Message.ServerMessage;

import it.polimi.ingsw.Client.GraphicElements.SceneBuilder;
import it.polimi.ingsw.Controller.MiniController.MiniController;
import it.polimi.ingsw.Controller.MiniController.OrderMiniController;

import java.util.ArrayList;

public class OrderGameMessage extends ServerMessage{
    ArrayList<String> playerlist;

    public OrderGameMessage(ArrayList<String> Players){
        this.playerlist=Players;
    }

    public ArrayList<String> getPlayerlist() {
        return playerlist;
    }

    @Override
    public MiniController getMiniController() {
        return new OrderMiniController(playerlist);
    }

    @Override
    public String getMessage() {
        String message = "Choose the player which'll move first\n";
        int i = 0;
        for(String string : playerlist)   {
            message = message + "- " + string + "\n";
        }
        return message + "(Please type the corresponding id)";
    }

    @Override
    public void buildScene() {
        SceneBuilder.orderGame(this);

    }
}
