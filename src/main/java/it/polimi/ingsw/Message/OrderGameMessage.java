package it.polimi.ingsw.Message;

import it.polimi.ingsw.Model.Player;

import java.util.ArrayList;

public class OrderGameMessage {
    ArrayList<Player> Playerlist;
    int size;

    public OrderGameMessage(ArrayList<Player> Players){
        this.Playerlist=Players;
        this.size=Playerlist.size();
    }

    public ArrayList<Player> getPlayerlist() {
        return Playerlist;
    }

    public int getSize() {
        return size;
    }
}
