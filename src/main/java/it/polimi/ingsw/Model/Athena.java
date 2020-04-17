package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.GodController.AthenaController;

import java.util.ArrayList;

/**
 * Represents the god card: Athena
 */
public class Athena extends God {
    protected final static String godName = "Athena";
    protected final static String godSubtitle = "Goddess of Wisdom";
    protected final static String powerDescription = "Opponent's Turn: If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.";

    /**
     * It creates the card Athena, set the correct sequence of phases and assign the correct GodController
     */
    public Athena(){
        this.phasesList = new ArrayList<PossiblePhases>();
        this.phasesList.add(PossiblePhases.SPECIAL_CHOOSE_CONSTRUCTOR);
        this.phasesList.add(PossiblePhases.SPECIAL_MOVE);
        this.phasesList.add(PossiblePhases.BUILD);

        this.godController = new AthenaController();
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

    public boolean shouldActivatePower(Model model){
        Position prev = model.getCurrentConstructor().getPrevPos();
        Position curr = model.getCurrentConstructor().getPos();

        Tile prevT = model.getBoard().getTile(prev);
        Tile currT = model.getBoard().getTile(curr);

        return (currT.getConstructionLevel() > prevT.getConstructionLevel());
    }

    public void changeCanGoUp(Model model, boolean b){
        model.getBoard().setCanGoUp(b);
    }
}
