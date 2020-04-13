package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.GodController.HephaestusController;
import it.polimi.ingsw.Message.TileToShowMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the god card: Hephaestus
 */
public class Hephaestus extends God {
    /**
     * It creates the card Hephaestus, set all the specific data (as godName. godSubtitle and powerDescription)
     * the correct sequence of phases and in the end assign he correct GodController
     */
    public Hephaestus(){
        this.godName = "Hephaestus";
        this.godSubtitle = "God of Blacksmiths";
        this.powerDescription = "Your Build: Your Worker may build one additional block (not dome) on top of your first block.";

        this.phasesList = new ArrayList<PossiblePhases>();
        this.phasesList.add(PossiblePhases.CHOOSE_CONSTRUCTOR);
        this.phasesList.add(PossiblePhases.MOVE);
        this.phasesList.add(PossiblePhases.BUILD);
        this.phasesList.add(PossiblePhases.SPECIAL_BUILD);

        this.godController = new HephaestusController();
    }

    public Position getLastBuildPos(Model model){
        return model.getCurrentConstructor().getLastBuildPos().clone();
    }

    public boolean isMaxedOrDome(Model model, Position pos){
        Tile t = model.getBoard().getTile(pos);
        return (t.getDome() || t.getConstructionLevel() == 3);
    }

    public void createPossibleSpecialBuild(Model model){
        Position lastBuildPos = model.getCurrentConstructor().getLastBuildPos().clone();
        List<Position> list = new ArrayList<Position>();

        list.add(lastBuildPos);

        // WAIT FOR "forceNotify" IMPLEMENTATION IN MODEL
        // model.forceNotify(new TileToShowMessage("Something", model.getGameState().getCurrentPlayer(), model.getGameState().getCurrentPhase(), list));
    }
}
