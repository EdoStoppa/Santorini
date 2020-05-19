package it.polimi.ingsw.Message.ServerMessage;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Controller.MiniController.MiniController;
import it.polimi.ingsw.Controller.MiniController.OrderMiniController;
import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Model.Player;

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
}
