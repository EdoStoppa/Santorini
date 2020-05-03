package it.polimi.ingsw.Message.ServerMessage;

import it.polimi.ingsw.Model.God;

import java.util.List;

public class PickGodMessage extends ServerMessage {
    List<God> AllGod = God.getAllGod();
    int size= AllGod.size();

    public God GetGod (int i){
        return AllGod.get(i);
    }

    public int getSize() {
        return size;
    }
}
