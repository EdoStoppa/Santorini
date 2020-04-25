package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.GodController.ArtemisController;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the god card: Artemis
 */
public class Artemis extends God {
    /**
     * It creates the card Artemis, set the correct sequence of phases and assign the correct GodController
     */
    public Artemis(){
        this.godName = "Artemis";
        this.godSubtitle = "Goddess of the Hunt";
        this.powerDescription = "Your Move: Your Worker may move one additional time, but not back to its initial space.";

        this.phasesList = new ArrayList<PossiblePhases>();
        this.phasesList.add(PossiblePhases.CHOOSE_CONSTRUCTOR);
        this.phasesList.add(PossiblePhases.MOVE);
        this.phasesList.add(PossiblePhases.SPECIAL_MOVE);
        this.phasesList.add(PossiblePhases.BUILD);

        this.godController = new ArtemisController();
    }

    public Position getPrevPosConstructor(Model model){
        return model.getCurrentConstructor().getPrevPos().clone();
    }

    /**
     * Method that tells if the <em>Player</em> can't do another move
     *
     * @param model the <em>Model</em> of the game
     * @param delList list of Position which won't be considered valid
     *
     * @return if the <em>Player</em> has only one move, and it isn't a valid one, return true
     */
    public boolean cantDoAnother(Model model, List<Position> delList){
        List<Position> possibleMove = model.getBoard().possibleMoveset(model.getCurrentConstructor());

        return (possibleMove.size() == 1 && possibleMove.get(0).equals(delList.get(0)));
    }
}
