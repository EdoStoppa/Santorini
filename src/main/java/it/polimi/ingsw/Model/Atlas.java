package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.GodController.AtlasController;
import it.polimi.ingsw.Message.BuildMessage;
import it.polimi.ingsw.Message.GameMessage;

import java.util.ArrayList;

/**
 * Represents the god card: Atlas
 */
public class Atlas extends God {
    protected final static String godName = "Atlas";
    protected final static String godSubtitle = "Titan shouldering the Heavens";
    protected final static String powerDescription = "Your Build: Your Worker may build a dome at any level.";

    /**
     * It creates the card Atlas, set the correct sequence of phases and assign the correct GodController
     */
    public Atlas(){
        this.phasesList = new ArrayList<PossiblePhases>();
        this.phasesList.add(PossiblePhases.CHOOSE_CONSTRUCTOR);
        this.phasesList.add(PossiblePhases.MOVE);
        this.phasesList.add(PossiblePhases.SPECIAL_BUILD);

        this.godController = new AtlasController();
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

    public void forceDome(Model model, Position pos){
        Tile t = model.getBoard().getTile(pos);
        t.setDome(true);

        int[][] matrix = model.getBoard().createBuildingMatrix();
        String message = model.getGameState().getCurrentPlayer().getIdPlayer() + " built a dome on position: " + pos.toString();

        model.forceNotify(new BuildMessage(message, model.getGameState().getCurrentPlayer(), model.getGameState().getCurrentPhase(), matrix));
    }
}
