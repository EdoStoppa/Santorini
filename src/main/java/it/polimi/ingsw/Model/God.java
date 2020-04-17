package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.GodController.GodController;
import it.polimi.ingsw.Model.PossiblePhases;

import java.util.ArrayList;
import java.util.List;

/**Represents the god card that every player choose at the start of the game.
 * It's an abstract class because every god card is extremely different in its initialization
 */

public abstract class God {
    /**Represents the sequence of phases during a player turn that have this card
     */
    protected List<PossiblePhases> phasesList;
    /**Holds an helper class used by the controller to manage special move/build
     */
    protected GodController godController;

    public List<PossiblePhases> getPhasesList(){
        return this.phasesList;
    }

    public GodController getGodController(){
        return godController;
    }

    public static List<String> getAllGodName(){
        List<String> list = new ArrayList<>();

        list.add(Apollo.getGodName());
        list.add(Artemis.getGodName());
        list.add(Athena.getGodName());
        list.add(Atlas.getGodName());
        list.add(Demeter.getGodName());
        list.add(Hephaestus.getGodName());
        list.add(Minotaur.getGodName());
        list.add(Pan.getGodName());
        list.add(Prometheus.getGodName());

        return list;
    }

    public static List<String> getAllGodSubtitle(){
        List<String> list = new ArrayList<>();

        list.add(Apollo.getGodSubtitle());
        list.add(Artemis.getGodSubtitle());
        list.add(Athena.getGodSubtitle());
        list.add(Atlas.getGodSubtitle());
        list.add(Demeter.getGodSubtitle());
        list.add(Hephaestus.getGodSubtitle());
        list.add(Minotaur.getGodSubtitle());
        list.add(Pan.getGodSubtitle());
        list.add(Prometheus.getGodSubtitle());

        return list;
    }

    public static List<String> getAllGodPower(){
        List<String> list = new ArrayList<>();

        list.add(Apollo.getGodPower());
        list.add(Artemis.getGodPower());
        list.add(Athena.getGodPower());
        list.add(Atlas.getGodPower());
        list.add(Demeter.getGodPower());
        list.add(Hephaestus.getGodPower());
        list.add(Minotaur.getGodPower());
        list.add(Pan.getGodPower());
        list.add(Prometheus.getGodPower());

        return list;
    }

}
