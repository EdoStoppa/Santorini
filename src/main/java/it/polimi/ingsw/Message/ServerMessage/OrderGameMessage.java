package it.polimi.ingsw.Message.ServerMessage;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Model.Player;

import java.util.ArrayList;

public class OrderGameMessage extends ServerMessage{
    ArrayList<String> Playerlist;

    public OrderGameMessage(ArrayList<String> Players){
        this.Playerlist=Players;
    }

    public ArrayList<String> getPlayerlist() {
        return Playerlist;
    }

    @Override
    public String getMessage() {
        String message = "Choose the first player typing the corresponding id \n";
        int i = 0;
        for(String string : Playerlist)   {
            message = message + "- " + string + "\n";
        }
        return message + "\n";
    }
}
