package it.polimi.ingsw.Message.ServerMessage;

import it.polimi.ingsw.Model.God;

import java.util.List;

public class PickGodMessage extends ServerMessage {
    List<God> AllGod = God.getAllGod();
    int numPlayer;

    public PickGodMessage(int numPlayer) {
        this.numPlayer = numPlayer;
    }

    public God GetGod (int i){
        return AllGod.get(i);
    }

    @Override
    public String getMessage() {
        String message = "Choose " + numPlayer + " gods that will be in the match by typing the corresponding number\n";
        int i = 0;
        for(God god : AllGod)   {
            message = message + i + ") " + god.getGodName() + " - " + god.getGodSubtitle() +"\n" + god.getGodPower() + "\n\n";
            i++;
        }
        return message;
    }

    public int getNumPlayer()   {
        return this.numPlayer;
    }
}
