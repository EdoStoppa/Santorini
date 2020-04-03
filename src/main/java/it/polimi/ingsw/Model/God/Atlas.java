package it.polimi.ingsw.Model.God;

import it.polimi.ingsw.Controller.GodController.AtlasController;
import it.polimi.ingsw.Model.PossiblePhases;

import java.util.ArrayList;

/**
 * Represents the god card: Atlas
 */
public class Atlas extends God {
    /**
     * It creates the card Atlas, set all the specific data (as godName. godSubtitle and powerDescription)
     * the correct sequence of phases and in the end assign he correct GodController
     */
    public Atlas(){
        this.godName = "Atlas";
        this.godSubtitle = "Titan shouldering the Heavens";
        this.powerDescription = "Your Build: Your Worker may build a dome at any level.";

        this.phasesList = new ArrayList<PossiblePhases>();
        this.phasesList.add(PossiblePhases.CHOOSE_CONSTRUCTOR);
        this.phasesList.add(PossiblePhases.MOVE);
        this.phasesList.add(PossiblePhases.SPECIAL_BUILD);
        this.phasesList.add(PossiblePhases.END_TURN);

        this.godController = new AtlasController();
    }
}
