package it.polimi.ingsw.Message;

import it.polimi.ingsw.Model.God;

import java.util.ArrayList;


public class ChosenGodMessage {
    ArrayList<God> ChosenGod;
    Integer size;

    public ChosenGodMessage(ArrayList<God> ChosenGod){
        this.ChosenGod=ChosenGod;
        this.size=ChosenGod.size();
    }

    public ArrayList<God> getChosenGod() {
        return ChosenGod;
    }

    public Integer getSize() {
        return size;
    }
}
