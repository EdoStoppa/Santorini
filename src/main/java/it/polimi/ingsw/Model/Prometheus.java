package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.GodController.PrometheusController;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.SpecialActionMessage;
import it.polimi.ingsw.Message.TileToShowMessage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the god card: Prometheus
 */
public class Prometheus extends God {
    protected final static String godName = "Prometheus";
    protected final static String godSubtitle = "Titan benefactor of Mankind";
    protected final static String powerDescription = "Your Turn: If your Worker does not move up, it may build both before and after moving.";

    private boolean canGoUp;

    /**
     * It creates the card Prometheus, set the correct sequence of phases and assign the correct GodController
     */
    public Prometheus(){
        this.phasesList = new ArrayList<PossiblePhases>();
        this.phasesList.add(PossiblePhases.SPECIAL_CHOOSE_CONSTRUCTOR);
        this.phasesList.add(PossiblePhases.SPECIAL_BUILD);
        this.phasesList.add(PossiblePhases.SPECIAL_MOVE);
        this.phasesList.add(PossiblePhases.BUILD);

        this.godController = new PrometheusController();
        this.canGoUp = true;
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

    public List<Position> sameOrDownLevel(Model model, Constructor c){
        List<Position> standardPos = model.getBoard().possibleMoveset(c);
        Tile currentT = model.getBoard().getTile(c.getPos());

        List<Position> list = new ArrayList<>();

        for(Position p : standardPos){
            Tile nextT = model.getBoard().getTile(p);
            if((currentT.getConstructionLevel() >= nextT.getConstructionLevel()) && !nextT.getDome()){
                list.add(p.clone());
            }
        }

        return list;
    }

    public List<Position> sameOrDownLevelCurrent(Model model){
        return sameOrDownLevel(model, model.getCurrentConstructor());
    }

    public void createPossibleConstructorPos(Model model){
        List<Constructor> constList = model.getGameState().getCurrentPlayer().getAllConstructors();
        List<Position> normalPos = new ArrayList<>();
        List<Position> noPowerPos = new ArrayList<>();

        for(Constructor c : constList)  {
            if(c.getCanMove())  {
                normalPos.add(c.getPos().clone());
            }
        }

        for(Constructor c : constList)  {
            int num = sameOrDownLevel(model, c).size();

            if(num == 0)  {
                noPowerPos.add(c.getPos().clone());
            }

            if(num == 1 && model.getBoard().possibleBuild(c).size() == 1){
                noPowerPos.add(c.getPos().clone());
            }
        }

        GameMessage message;

        if(noPowerPos.size() > 0){
             message = new SpecialActionMessage("noPower", model.getGameState().getCurrentPlayer(), model.getCurrentPhase(), normalPos, noPowerPos);
        } else {
            message = new TileToShowMessage("standard", model.getGameState().getCurrentPlayer(), model.getCurrentPhase(), normalPos);
        }

        model.forceNotify(message);

    }

    public void setCorrectPhase(Model model){
        model.getGameState().setCurrentPhase(phasesList.get(1));
    }

}
