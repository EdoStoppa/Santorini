package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.GodController.DemeterController;

import java.util.ArrayList;

/**
 * Represents the god card: Demeter
 */
public class Demeter extends God {
    protected final static String godName = "Demeter";
    protected final static String godSubtitle = "Goddess of the Harvest";
    protected final static String powerDescription = "Your Build: Your Worker may build one additional time, but not on the same space.";

    /**
     * It creates the card Demeter, set the correct sequence of phases and assign the correct GodController
     */
    public Demeter(){
        this.phasesList = new ArrayList<PossiblePhases>();
        this.phasesList.add(PossiblePhases.CHOOSE_CONSTRUCTOR);
        this.phasesList.add(PossiblePhases.MOVE);
        this.phasesList.add(PossiblePhases.BUILD);
        this.phasesList.add(PossiblePhases.SPECIAL_BUILD);

        this.godController = new DemeterController();
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

    public Position getLastBuild(Model model){
        return model.getCurrentConstructor().getLastBuildPos().clone();
    }
}
