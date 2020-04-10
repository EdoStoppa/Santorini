package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.GodController.PrometheusController;

import java.util.ArrayList;

/**
 * Represents the god card: Prometheus
 */
public class Prometheus extends God {
    /**
     * It creates the card Prometheus, set all the specific data (as godName. godSubtitle and powerDescription)
     * the correct sequence of phases and in the end assign he correct GodController
     */
    public Prometheus(){
        this.godName = "Prometheus";
        this.godSubtitle = "Titan benefactor of Mankind";
        this.powerDescription = "Your Turn: If your Worker does not move up, it may build both before and after moving.";

        this.phasesList = new ArrayList<PossiblePhases>();
        this.phasesList.add(PossiblePhases.SPECIAL_CHOOSE_CONSTRUCTOR);
        this.phasesList.add(PossiblePhases.SPECIAL_BUILD);
        this.phasesList.add(PossiblePhases.MOVE);
        this.phasesList.add(PossiblePhases.BUILD);

        this.godController = new PrometheusController();
    }
}
