package it.polimi.ingsw.Model.God;

import it.polimi.ingsw.Controller.GodController.DemeterController;
import it.polimi.ingsw.Model.PossiblePhases;

import java.util.ArrayList;

/**
 * Represents the god card: Demeter
 */
public class Demeter extends God {
    /**
     * It creates the card Demeter, set all the specific data (as godName. godSubtitle and powerDescription)
     * the correct sequence of phases and in the end assign he correct GodController
     */
    public Demeter(){
        this.godName = "Demeter";
        this.godSubtitle = "Goddess of the Harvest";
        this.powerDescription = "Your Build: Your Worker may build one additional time, but not on the same space.";

        this.phasesList = new ArrayList<PossiblePhases>();
        this.phasesList.add(PossiblePhases.CHOOSE_CONSTRUCTOR);
        this.phasesList.add(PossiblePhases.MOVE);
        this.phasesList.add(PossiblePhases.BUILD);
        this.phasesList.add(PossiblePhases.SPECIAL_BUILD);

        this.godController = new DemeterController();
    }
}
