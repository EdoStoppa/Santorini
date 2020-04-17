package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.GodController.PanController;

import java.util.ArrayList;

/**
 * Represents the god card: Pan
 */
public class Pan extends God{
    protected final static String godName = "Pan";
    protected final static String godSubtitle = "God of the Wild";
    protected final static String powerDescription = "Win Condition: You also win if your Worker moves down two or more levels.";

    /**
     * It creates the card Pan, set the correct sequence of phases and assign the correct GodController
     */
    public Pan(){
        this.phasesList = new ArrayList<PossiblePhases>();
        this.phasesList.add(PossiblePhases.CHOOSE_CONSTRUCTOR);
        this.phasesList.add(PossiblePhases.SPECIAL_MOVE);
        this.phasesList.add(PossiblePhases.BUILD);

        this.godController = new PanController();
    }

    protected static String getGodName(){
        return (godName);
    }

    protected static String getGodSubtitle(){
        return (godSubtitle);
    }

    protected static String getGodPower(){
        return (powerDescription);
    }

    public boolean checkWin(Model model){
        Constructor c = (model.getCurrentConstructor());
        Tile currentT = model.getBoard().getTile(c.getPos());
        Tile prevT = model.getBoard().getTile(c.getPos());

        return ((prevT.getConstructionLevel() - currentT.getConstructionLevel()) >= 2);
    }
}
