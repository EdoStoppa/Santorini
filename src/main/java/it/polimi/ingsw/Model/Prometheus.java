package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.GodController.PrometheusController;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.TileToShowMessages.MoreTileToCheckMessage;
import it.polimi.ingsw.Message.TileToShowMessages.StandardTileMessage;

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

    /**
     * Method used to get all the <em>Positions</em> that will need to be eliminated
     * from the normal moveset (only if due to his own power Prometheus can't go up)
     *
     * @param model the <em>Model</em> of the game
     *
     * @return  the list of wrong Position to eliminate (or null if there aren't any)
     */
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

    /**
     * Given a <em>Constructor</em> the method returns the list of tile's <em>Positions</em>
     * which are at the same level (or below) of the tile where is the Constructor
     *
     * @param model the <em>Model</em> of the game
     * @param c the <em>Constructor</em> to check
     *
     * @return the list (even with 0 elements) of <em>Positions</em>
     */
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

    /**
     * Modified version of the standard sameOrDownLevel using the currentConstructor
     *
     * @param model the <em>Model</em> of the game
     *
     * @return  a list of <em>Positions</em>
     */
    public List<Position> sameOrDownLevelCurrent(Model model){
        return sameOrDownLevel(model, model.getCurrentConstructor());
    }

    /**
     * The method creates all the possible ConstructorPosition, then check if there are some of these
     * that can't be chosen if the player want to use his god power.
     * If at the end there are no noPowerPos (= no Power Positions) the method forces a notify using a TileToShowMessage
     * with a standard code, otherwise forces a notify using a SpecialActionMessage with the code "noPower"
     *
     * @param model the <em>Model</em> of the game
     */
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

        model.setTileToShow(normalPos);

        GameMessage message;

        if(noPowerPos.size() > 0){
             message = new MoreTileToCheckMessage(model.getGameState().getCurrentPlayer().getIdPlayer(), model.getCurrentPhase(), normalPos, noPowerPos);
        } else {
            message = new StandardTileMessage(model.getGameState().getCurrentPlayer().getIdPlayer(), model.getCurrentPhase(), normalPos);
        }

        model.forceNotify(message);

    }

    /**
     * Creates a standard PossibleMovePos list, then delete every position in the wrongList from the
     * main list, then set the list as the TileToShow and end with sending a notify with code: "standard"
     *
     * @param model the <em>Model</em> of the list
     * @param wrongList the list of wrong <em>Positions</em>
     */
    public void createPossibleMovePos(Model model, List<Position> wrongList){
        List<Position> list;

        list = model.getBoard().possibleMoveset(model.getCurrentConstructor());

        if(wrongList != null){
            for(Position delPos : wrongList)  {
                for(int i = 0; i < list.size(); i++)   {
                    if(list.get(i).equals(delPos)) {
                        list.remove(i);
                        break;
                    }
                }
            }
        }

        model.setTileToShow(list);

        model.forceNotify(new StandardTileMessage(model.getGameState().getCurrentPlayer().getIdPlayer(), model.getGameState().getCurrentPhase(), list));
    }

    public void setCorrectPhase(Model model){
        model.getGameState().setCurrentPhase(phasesList.get(1));
    }

}
