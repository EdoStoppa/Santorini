package it.polimi.ingsw.Message.ServerMessage;

import it.polimi.ingsw.Model.God;

import java.util.List;

public class PickGodMessage extends ServerMessage {
    List<God> AllGod = God.getAllGod();

    public God GetGod (int i){
        return AllGod.get(i);
    }

}
