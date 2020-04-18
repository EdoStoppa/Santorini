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

    public boolean cantDoAnother(Model model, Position pos){
        Tile t = model.getBoard().getTile(pos);
        return (t.getDome() || t.getConstructionLevel() == 3);
    }

    public void createPossibleSpecialBuild(Model model){
        Position lastBuildPos = model.getCurrentConstructor().getLastBuildPos().clone();
        List<Position> list = new ArrayList<Position>();

        list.add(lastBuildPos);

        model.forceNotify(new TileToShowMessage("canEnd", model.getGameState().getCurrentPlayer(), model.getGameState().getCurrentPhase(), list));
    }
}
