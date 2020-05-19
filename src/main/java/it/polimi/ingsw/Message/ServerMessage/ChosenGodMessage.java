package it.polimi.ingsw.Message.ServerMessage;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Controller.MiniController.ChosenMiniController;
import it.polimi.ingsw.Controller.MiniController.MiniController;
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
    public MiniController getMiniController() {
        return new ChosenMiniController(numPlayer);
    }

    @Override
    public String getMessage() {
        String message = "Choose the god you'll use for this match\n\n";
        int i = 0;
        for(God god : ChosenGod)   {
            message = message + i + ") " + god.getGodName() + " - " + god.getGodSubtitle() +"\n" + god.getGodPower() + "\n";
            i++;
        }
        return message + "\nType the corresponding number to choose your god";
    }

    public int getNumPlayer(){
        return numPlayer;
    }

    public int getSize()    {
        return ChosenGod.size();
    }

}
