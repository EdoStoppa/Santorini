package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.GodController.DemeterController;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the god card: Demeter
 */
public class Demeter extends God {
    /**
     * It creates the card Demeter, set the correct sequence of phases and assign the correct GodController
     */
    public Demeter(){
        this.godName = "Demeter";
        this.godSubtitle = "Goddess of the Harvest";
        this.powerDescription = "Your Build: Your Worker may build one additional time, but not on the same space.";

        this.phasesList = new ArrayList<PossiblePhases>();
        this.phasesList.add(PossiblePhases.CHOOSE_CONSTRUCTOR);
        this.phasesList.add(PossiblePhases.MOVE);
        this.phasesList.add(PossiblePhases.BUILD);
        this.phasesList.add(PossiblePhases.SPECIAL_BUILD);

        this.godController = new DemeterController();
    }

    public Position getLastBuild(Model model){
        return model.getCurrentConstructor().getLastBuildPos().clone();
    }

    public boolean canBuildAgain(Model model){
        List<Position> buildList = model.getBoard().possibleBuild(model.getCurrentConstructor());

        //if she can't build anymore -> next turn
        if(buildList.size()==0)
            return false;

        //if she has 2 build positions or has 1 but it's different than the lastBuildPos, it means that she can build again
        return buildList.size() > 1 || !buildList.get(0).equals(model.getCurrentConstructor().getLastBuildPos());
    }
}
