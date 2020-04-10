package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.GodController.AtlasController;
import it.polimi.ingsw.Message.BuildMessage;
import it.polimi.ingsw.Message.GameMessage;

import java.util.ArrayList;

/**
 * Represents the god card: Atlas
 */
public class Atlas extends God {
    /**
     * It creates the card Atlas, set all the specific data (as godName. godSubtitle and powerDescription)
     * the correct sequence of phases and in the end assign he correct GodController
     */
    public Atlas(){
        this.godName = "Atlas";
        this.godSubtitle = "Titan shouldering the Heavens";
        this.powerDescription = "Your Build: Your Worker may build a dome at any level.";

        this.phasesList = new ArrayList<PossiblePhases>();
        this.phasesList.add(PossiblePhases.CHOOSE_CONSTRUCTOR);
        this.phasesList.add(PossiblePhases.MOVE);
        this.phasesList.add(PossiblePhases.SPECIAL_BUILD);

        this.godController = new AtlasController();
    }

    public void forceDome(Model model, Position pos){
        Tile t = model.getBoard().getTile(pos);
        t.setDome(true);

        int[][] matrix = model.getBoard().createBuildingMatrix();
        String message = model.getGameState().getCurrentPlayer().getIdPlayer() + " built a dome on position: " + pos.toString();

        // WAIT FOR "forceNotify" IMPLEMENTATION IN MODEL
        //model.forceNotify(new BuildMessage(message, model.getGameState().getCurrentPlayer(), model.getGameState().getCurrentPhase(), matrix));
    }
}
