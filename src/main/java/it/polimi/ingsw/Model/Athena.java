package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.GodController.AthenaController;

import java.util.ArrayList;

/**
 * Represents the god card: Athena
 */
public class Athena extends God {
    /**
     * It creates the card Athena, set all the specific data (as godName. godSubtitle and powerDescription)
     * the correct sequence of phases and in the end assign he correct GodController
     */
    public Athena(){
        this.godName = "Athena";
        this.godSubtitle = "Goddess of Wisdom";
        this.powerDescription = "Opponent's Turn: If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.";

        this.phasesList = new ArrayList<PossiblePhases>();
        this.phasesList.add(PossiblePhases.CHOOSE_CONSTRUCTOR);
        this.phasesList.add(PossiblePhases.SPECIAL_MOVE);
        this.phasesList.add(PossiblePhases.BUILD);

        this.godController = new AthenaController();
    }

}
