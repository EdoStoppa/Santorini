package it.polimi.ingsw.Model.God;

import it.polimi.ingsw.Controller.GodController.PanController;
import it.polimi.ingsw.Model.PossiblePhases;

import java.util.ArrayList;

/**
 * Represents the god card: Pan
 */
public class Pan extends God{
    /**
     * It creates the card Pan, set all the specific data (as godName. godSubtitle and powerDescription)
     * the correct sequence of phases and in the end assign he correct GodController
     */
    public Pan(){
        this.godName = "Pan";
        this.godSubtitle = "God of the Wild";
        this.powerDescription = "Win Condition: You also win if your Worker moves down two or more levels.";

        this.phasesList = new ArrayList<PossiblePhases>();
        this.phasesList.add(PossiblePhases.CHOOSE_CONSTRUCTOR);
        this.phasesList.add(PossiblePhases.SPECIAL_MOVE);
        this.phasesList.add(PossiblePhases.BUILD);
        this.phasesList.add(PossiblePhases.END_TURN);

        this.godController = new PanController();
    }
}
