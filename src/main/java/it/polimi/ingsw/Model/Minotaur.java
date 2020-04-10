package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.GodController.MinotaurController;

import java.util.ArrayList;

/**
 * Represents the god card: Minotaur
 */
public class Minotaur extends God {
    /**
     * It creates the card Minotaur, set all the specific data (as godName. godSubtitle and powerDescription)
     * the correct sequence of phases and in the end assign he correct GodController
     */
    public Minotaur(){
        this.godName = "Minotaur";
        this.godSubtitle = "Bull-headed Monster";
        this.powerDescription = "Your Move: Your Worker may move into an opponent Worker's space, if their Worker can be forced one space straight backwards to and unoccupied space at any level.";

        this.phasesList = new ArrayList<PossiblePhases>();
        this.phasesList.add(PossiblePhases.SPECIAL_CHOOSE_CONSTRUCTOR);
        this.phasesList.add(PossiblePhases.SPECIAL_MOVE);
        this.phasesList.add(PossiblePhases.BUILD);

        this.godController = new MinotaurController();
    }
}
