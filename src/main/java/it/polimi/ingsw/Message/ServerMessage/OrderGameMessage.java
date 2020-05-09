package it.polimi.ingsw.Message.ServerMessage;

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

}
