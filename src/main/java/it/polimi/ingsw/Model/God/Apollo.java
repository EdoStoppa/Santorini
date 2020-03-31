package it.polimi.ingsw.Model.God;

import it.polimi.ingsw.Model.PossiblePhases;

import java.util.ArrayList;

public class Apollo extends God {

    public Apollo(){
        this.godName = "Apollo";
        this.godSubtitle = "God of Music";
        this.powerDescription = "Your Move: Your Worker may move into an opponent Worker's space by forcing their Worker to the space yours just vacated.";

        this.phasesList = new ArrayList<PossiblePhases>();
        this.phasesList.add(PossiblePhases.CHOOSE_CONSTRUCTOR);
        this.phasesList.add(PossiblePhases.SPECIAL_MOVE);
        this.phasesList.add(PossiblePhases.BUILD);
        this.phasesList.add(PossiblePhases.END_TURN);

        this.godController = new ApolloController();
    }
}
