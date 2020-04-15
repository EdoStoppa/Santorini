package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.GodController.PrometheusController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the god card: Prometheus
 */
public class Prometheus extends God {
    private boolean canGoUp;
    /**
     * It creates the card Prometheus, set all the specific data (as godName. godSubtitle and powerDescription)
     * the correct sequence of phases and in the end assign he correct GodController
     */
    public Prometheus(){
        this.godName = "Prometheus";
        this.godSubtitle = "Titan benefactor of Mankind";
        this.powerDescription = "Your Turn: If your Worker does not move up, it may build both before and after moving.";

        this.phasesList = new ArrayList<PossiblePhases>();
        this.phasesList.add(PossiblePhases.SPECIAL_CHOOSE_CONSTRUCTOR);
        this.phasesList.add(PossiblePhases.SPECIAL_BUILD);
        this.phasesList.add(PossiblePhases.SPECIAL_MOVE);
        this.phasesList.add(PossiblePhases.BUILD);

        this.godController = new PrometheusController();
        this.canGoUp = true;
    }

    public boolean getCanGoUp(){
        return this.canGoUp;
    }

    public void setCanGoUp(boolean b){
        this.canGoUp = b;
    }

    public List<Position> getWrongPos(Model model){
        List<Position> standardPos = model.getBoard().possibleMoveset(model.getCurrentConstructor());
        Tile currentT = model.getBoard().getTile(model.getCurrentConstructor().getPos());

        List<Position> wrongPos = new ArrayList<Position>();

        for(Position p : standardPos){
            if((currentT.getConstructionLevel() < model.getBoard().getTile(p).getConstructionLevel()) && !getCanGoUp()){
                wrongPos.add(p.clone());
            }
        }

        if(wrongPos.size() == 0){
            return null;
        }
        return wrongPos;
    }

    public void createPossibleConstructorPos(Model model){}

}
