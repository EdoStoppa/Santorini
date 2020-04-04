package it.polimi.ingsw.Model.God;

import it.polimi.ingsw.Controller.GodController.GodController;
import it.polimi.ingsw.Model.PossiblePhases;

import java.util.ArrayList;
import java.util.List;

/**Represents the god card that every player choose at the start of the game.
 * It's an abstract class because every god card is extremely different in its initialization
 */

public abstract class God {
    protected String godName;
    protected String godSubtitle;
    protected String powerDescription;

    /**Represents the sequence of phases during a player turn that have this card
     */
    protected List<PossiblePhases> phasesList;
    /**Holds an helper class used by the controller to manage special move/build
     */
    protected GodController godController;

    public List<PossiblePhases> getPhasesList(){
        return new ArrayList<PossiblePhases>(this.phasesList);
    }

    public GodController getGodController(){
        return godController;
    }

}
