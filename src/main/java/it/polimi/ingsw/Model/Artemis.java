package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.GodController.ArtemisController;

import java.util.ArrayList;

/**
 * Represents the god card: Artemis
 */
public class Artemis extends God {
    /**
     * It creates the card Artemis, set all the specific data (as godName. godSubtitle and powerDescription)
     * the correct sequence of phases and in the end assign he correct GodController
     */
    public Artemis(){
        this.godName = "Artemis";
        this.godSubtitle = "Goddess of the Hunt";
        this.powerDescription = "Your Move: Your Worker may move one additional time, but not back to its initial space.";

        this.phasesList = new ArrayList<PossiblePhases>();
        this.phasesList.add(PossiblePhases.CHOOSE_CONSTRUCTOR);
        this.phasesList.add(PossiblePhases.MOVE);
        this.phasesList.add(PossiblePhases.SPECIAL_MOVE);
        this.phasesList.add(PossiblePhases.BUILD);

        this.godController = new ArtemisController();
    }

    public Position getPrevPosConstructor(Model model){
        return model.getCurrentConstructor().getPrevPos().clone();
    }
}
