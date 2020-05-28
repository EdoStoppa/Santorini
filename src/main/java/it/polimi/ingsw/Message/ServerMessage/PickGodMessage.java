package it.polimi.ingsw.Message.ServerMessage;


import it.polimi.ingsw.Client.GraphicElements.SceneBuilder;
import it.polimi.ingsw.Controller.MiniController.MiniController;
import it.polimi.ingsw.Controller.MiniController.PickGodMiniController;
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
    public MiniController getMiniController() {
        return new PickGodMiniController(AllGod.size(), numPlayer);
    }

    @Override
    public String getMessage() {
        String message = "Choose " + numPlayer + " gods that will be in the match\n\n";
        int i = 0;
        for(God god : AllGod)   {
            message = message + i + ") " + god.getGodName() + " - " + god.getGodSubtitle() +"\n" + god.getGodPower() + "\n";
            i++;
        }
        return message + "\nType the corresponding number to choose them " + (numPlayer==2? "(ex. 2,4)" : "(ex. 1,3,6)");
    }

    public int getNumPlayer()   {
        return this.numPlayer;
    }


    @Override
    public void buildScene() {
        SceneBuilder.PickGod(this);
    }

}
