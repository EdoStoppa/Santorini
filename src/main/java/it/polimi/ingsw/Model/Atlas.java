package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.GodController.AtlasController;
import it.polimi.ingsw.Message.BuildMessages.BuildMessage;
import it.polimi.ingsw.Message.BuildMessages.ForcedDomeMessage;
import it.polimi.ingsw.Message.TileToShowMessages.TileToShowMessage;
import it.polimi.ingsw.Message.TileToShowMessages.CheckDomeMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the god card: Atlas
 */
public class Atlas extends God {
    /**
     * It creates the card Atlas, set the correct sequence of phases and assign the correct GodController
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

    /**
     * Method used to force the construction of a dome given where to build.
     * Then it will send a <em>BuildMessage</em> using a special code: "forcedDome"
     *
     * @param model the <em>Model</em> of the game
     * @param pos the position where to build the dome
     */
    public void forceDome(Model model, Position pos){
        Tile t = model.getBoard().getTile(pos);
        t.setDome(true);

        int[][] matrix = model.getBoard().createBuildingMatrix();

        BuildMessage message = new ForcedDomeMessage(model.getGameState().getCurrentPlayer().getIdPlayer(), model.getGameState().getCurrentPhase(), matrix);
        message.setMessage(pos.toString());
        model.forceNotify(message);
    }

    /**
     * Method used to create and set the tiles where could be constructed somthing.
     * After doing this, creates a <em>TileToShowMessage</em> with a special code: "checkDome"
     *
     * @param model
     */
    public void prepareBuild(Model model){
        List<Position> list;

        list = model.getBoard().possibleBuild(model.getCurrentConstructor());
        model.setTileToShow(list);

        TileToShowMessage message = new CheckDomeMessage(model.getGameState().getCurrentPlayer().getIdPlayer(), model.getCurrentPhase(), list);
        model.forceNotify(message);
    }
}
