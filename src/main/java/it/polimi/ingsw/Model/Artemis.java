package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.GodController.ArtemisController;

import java.util.ArrayList;

/**
 * Represents the god card: Artemis
 */
public class Artemis extends God {
    protected final static String godName = "Artemis";
    protected final static String godSubtitle = "Goddess of the Hunt";
    protected final static String powerDescription = "Your Move: Your Worker may move one additional time, but not back to its initial space.";

    /**
     * It creates the card Artemis, set the correct sequence of phases and assign the correct GodController
     */
    public Artemis(){
        this.phasesList = new ArrayList<PossiblePhases>();
        this.phasesList.add(PossiblePhases.CHOOSE_CONSTRUCTOR);
        this.phasesList.add(PossiblePhases.MOVE);
        this.phasesList.add(PossiblePhases.SPECIAL_MOVE);
        this.phasesList.add(PossiblePhases.BUILD);

        this.godController = new ArtemisController();
    }

    protected static String getGodName(){
        return (godName);
    }

    protected static String getGodSubtitle(){
        return (godSubtitle);
    }

    protected static String getGodPower(){
        return (powerDescription);
    }

    public Position getPrevPosConstructor(Model model){
        return model.getCurrentConstructor().getPrevPos().clone();
    }
}
