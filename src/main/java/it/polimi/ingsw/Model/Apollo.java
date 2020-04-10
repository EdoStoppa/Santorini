package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.GodController.ApolloController;

import java.util.ArrayList;

/**
 * Represents the god card: Apollo
 */
public class Apollo extends God {

    /**
     * It creates the card Apollo, set all the specific data (as godName. godSubtitle and powerDescription)
     * the correct sequence of phases and in the end assign he correct GodController
     */
    public Apollo(){
        this.godName = "Apollo";
        this.godSubtitle = "God of Music";
        this.powerDescription = "Your Move: Your Worker may move into an opponent Worker's space by forcing their Worker to the space yours just vacated.";

        this.phasesList = new ArrayList<PossiblePhases>();
        this.phasesList.add(PossiblePhases.SPECIAL_CHOOSE_CONSTRUCTOR);
        this.phasesList.add(PossiblePhases.SPECIAL_MOVE);
        this.phasesList.add(PossiblePhases.BUILD);

        this.godController = new ApolloController();
    }

}
