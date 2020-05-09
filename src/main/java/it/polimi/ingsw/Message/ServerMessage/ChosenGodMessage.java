package it.polimi.ingsw.Message.ServerMessage;

import it.polimi.ingsw.Model.God;

import java.util.ArrayList;


public class ChosenGodMessage extends ServerMessage {
    ArrayList<God> ChosenGod;
    int size;

    public ChosenGodMessage(ArrayList<God> ChosenGod){
        this.ChosenGod=ChosenGod;
        size=ChosenGod.size();
            }

    public God getChosenGod(int i) {

        return ChosenGod.get(i);
    }

    public int getSize(){
        return size;
    }

}
