package it.polimi.ingsw.Message.ServerMessage;

import it.polimi.ingsw.Model.Player;

import java.util.ArrayList;

public class OrderGameMessage extends ServerMessage{
    ArrayList<String> Playerlist;
    int size;

    public OrderGameMessage(ArrayList<String> Players){
        this.Playerlist=Players;
        this.size=Playerlist.size();
    }

    public ArrayList<String> getPlayerlist() {
        return Playerlist;
    }

    public int getSize() {
        return size;
    }
}
