package it.polimi.ingsw.Model.God;

import it.polimi.ingsw.Model.PossiblePhases;

import java.util.ArrayList;

/**
 * Represents the god card: Hephaestus
 */
public class Hephaestus extends God {
    /**
     * It creates the card Hephaestus, set all the specific data (as godName. godSubtitle and powerDescription)
     * the correct sequence of phases and in the end assign he correct GodController
     */
    public Hephaestus(){
        this.godName = "Hephaestus";
        this.godSubtitle = "God of Blacksmiths";
        this.powerDescription = "Your Build: Your Worker may build one additional block (not dome) on top of your first block.";

        this.phasesList = new ArrayList<PossiblePhases>();
        this.phasesList.add(PossiblePhases.CHOOSE_CONSTRUCTOR);
        this.phasesList.add(PossiblePhases.MOVE);
        this.phasesList.add(PossiblePhases.BUILD);
        this.phasesList.add(PossiblePhases.SPECIAL_BUILD);
        this.phasesList.add(PossiblePhases.END_TURN);

        this.godController = new HephaestusController();
    }
}
