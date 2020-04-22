package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.GodController.AtlasController;
import it.polimi.ingsw.Message.BuildMessage;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.TileToShowMessage;

import java.util.ArrayList;
import java.util.List;

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
        String code = "forcedDome";

        model.forceNotify(new BuildMessage(code, model.getGameState().getCurrentPlayer().getIdPlayer(), model.getGameState().getCurrentPhase(), matrix));
    }

    public void prepareBuild(Model model){
        List<Position> list;

        list = model.getBoard().possibleBuild(model.getCurrentConstructor());
        model.setTileToShow(list);

        TileToShowMessage message = new TileToShowMessage("checkDome", model.getGameState().getCurrentPlayer().getIdPlayer(), model.getCurrentPhase(), list);
        model.forceNotify(message);
    }
}
