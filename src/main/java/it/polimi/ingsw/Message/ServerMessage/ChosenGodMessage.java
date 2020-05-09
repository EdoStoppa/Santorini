package it.polimi.ingsw.Message.ServerMessage;

import it.polimi.ingsw.Model.God;

import java.util.ArrayList;


public class ChosenGodMessage extends ServerMessage {
    ArrayList<God> ChosenGod;
    int numPlayer;

    public ChosenGodMessage(ArrayList<God> ChosenGod, int numPlayer){
        this.ChosenGod = ChosenGod;
        this.numPlayer = numPlayer;
    }

    public God getChosenGod(int i) {

        return ChosenGod.get(i);
    }

    @Override
    public String getMessage() {
        String message = "Choose your god by typing the corresponding number";
        int i = 0;
        for(God god : ChosenGod)   {
            message = message + i + ") " + god.getGodName() + " - " + god.getGodSubtitle() +"\n" + god.getGodPower() + "\n\n";
            i++;
        }
        return message;
    }

    public int getNumPlayer(){
        return numPlayer;
    }

    public int getSize()    {
        return ChosenGod.size();
    }

}
