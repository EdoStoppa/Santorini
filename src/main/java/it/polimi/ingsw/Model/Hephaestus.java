package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.GodController.HephaestusController;
import it.polimi.ingsw.Message.TileToShowMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the god card: Hephaestus
 */
public class Hephaestus extends God {
    protected final static String godName = "Hephaestus";
    protected final static String godSubtitle = "God of Blacksmiths";
    protected final static String powerDescription = "Your Build: Your Worker may build one additional block (not dome) on top of your first block.";

    /**
     * It creates the card Hephaestus, set the correct sequence of phases and assign the correct GodController
     */
    public Hephaestus(){
        this.phasesList = new ArrayList<PossiblePhases>();
        this.phasesList.add(PossiblePhases.CHOOSE_CONSTRUCTOR);
        this.phasesList.add(PossiblePhases.MOVE);
        this.phasesList.add(PossiblePhases.BUILD);
        this.phasesList.add(PossiblePhases.SPECIAL_BUILD);

        this.godController = new HephaestusController();
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

    public Position getLastBuildPos(Model model){
        return model.getCurrentConstructor().getLastBuildPos().clone();
    }

    /**
     * Method that tells if the <em>Player</em> can't do another move
     *
     * @param model the <em>Model</em> of the game
     * @param pos <em>Tile</em>'s <em>Position</em> which will be checked
     *
     * @return if the <em>Tile</em>> has a dome or is at level 3, return true
     */
    public boolean cantDoAnother(Model model, Position pos){
        Tile t = model.getBoard().getTile(pos);
        return (t.getDome() || t.getConstructionLevel() == 3);
    }

    /**
     * Method used to do a Special Build.
     * The only possible <em>Position</em> to build will be the lastBuildPos, so it is used to create a
     * special <em>TileToShowMessage</em> with the code: "canEnd"
     *
     * @param model the <em>Model</em> of the game
     */
    public void createPossibleSpecialBuild(Model model){
        Position lastBuildPos = model.getCurrentConstructor().getLastBuildPos().clone();
        List<Position> list = new ArrayList<Position>();

        list.add(lastBuildPos);
        model.setTileToShow(list);

        model.forceNotify(new TileToShowMessage("canEnd", model.getGameState().getCurrentPlayer().getIdPlayer(), model.getGameState().getCurrentPhase(), list));
    }
}
