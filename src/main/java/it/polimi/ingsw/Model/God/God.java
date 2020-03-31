package it.polimi.ingsw.Model.God;

import it.polimi.ingsw.Controller.GodController;
import it.polimi.ingsw.Model.PossiblePhases;

import java.util.ArrayList;
import java.util.List;

public class God {
    protected String godName;
    protected String godSubtitle;
    protected String powerDescription;

    protected List<PossiblePhases> phasesList;
    protected GodController godController;

    public List<PossiblePhases> getPhasesList(){
        return new ArrayList<PossiblePhases>(this.phasesList);
    }

    public GodController getGodController(){
        return godController;
    }

}
