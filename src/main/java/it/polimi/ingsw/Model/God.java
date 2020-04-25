package it.polimi.ingsw.Model;

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

    public String getGodName(){
        return (this.godName);
    }

    public String getGodSubtitle(){
        return (this.godSubtitle);
    }

    public String getGodPower(){
        return (this.powerDescription);
    }

    public List<PossiblePhases> getPhasesList(){
        return this.phasesList;
    }

    public GodController getGodController(){
        return godController;
    }

    public static List<God> getAllGod(){
        List<God> list = new ArrayList<>();

        list.add(new Apollo());
        list.add(new Artemis());
        list.add(new Athena());
        list.add(new Atlas());
        list.add(new Demeter());
        list.add(new Hephaestus());
        list.add(new Minotaur());
        list.add(new Pan());
        list.add(new Prometheus());

        return list;
    }

}
