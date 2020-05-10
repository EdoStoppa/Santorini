package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.GodController.ApolloController;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the god card: Apollo
 */
public class Apollo extends God {

    /**
     * It creates the card Apollo, set the correct sequence of phases and assign the correct GodController
     */
    public Apollo(){
        this.godName = "Apollo";
        this.godSubtitle = "God of Music";
        this.powerDescription = "Your Move: Your Worker may move into an opponent Worker's space by forcing their Worker to the space yours just vacated.";

        this.phasesList = new ArrayList<PossiblePhases>();
        this.phasesList.add(PossiblePhases.SPECIAL_CHOOSE_CONSTRUCTOR);
        this.phasesList.add(PossiblePhases.SPECIAL_MOVE);
        this.phasesList.add(PossiblePhases.BUILD);

        this.godController = new ApolloController();
    }

    /**
     * Special version of changeActiveConstructors
     * <p>
     *     This method will deactivate a constructor only if there aren't any standard
     *     moves left and there aren't any tiles occupied by an enemy near the constructor
     *     (and obviously it'll activate the constructor if there's at least one move available)
     * </p>
     *
     * @param model the <em>Model</em> of the game
     */
    public void changeActiveConstructors(Model model){
        Board board = model.getBoard();
        GameState gameState = model.getGameState();

        List<Position> listMove, listOcc;

        for(Constructor c : gameState.getCurrentPlayer().getAllConstructors())  {
            int numEnemyOcc = 0;
            listMove = board.possibleMoveset(c);
            listOcc = board.searchForOccupied(c.getPos());

            for (Position position : listOcc) {
                if (board.getTile(position).getActualConstructor().getPlayerNumber() != gameState.getCurrentPlayer().getPlayerNumber()) {
                    if(board.getTile(c.getPos()).getConstructionLevel()+1 >= board.getTile(position).getConstructionLevel()){
                        Tile occTile = model.getBoard().getTile(position);
                        if(model.getBoard().possibleBuild(occTile.getActualConstructor()).size() >= 1 || occTile.getConstructionLevel() == 3)
                            numEnemyOcc += 1;
                    }
                }
            }

            //if there is at least one standard move or at least one enemy's occupied position where is possible to build
            //or the occupied position is a level 3, then setCanMove to true
            c.setCanMove(listMove.size() != 0 || numEnemyOcc != 0);

        }
    }

    /**
     * Creates the list of special <em>Positions</em> which will be added to the possible moves
     *
     * @param model the <em>Model</em> of the game
     * @return list of special Position
     */
    public List<Position> getMoveAddList(Model model){
        Board board = model.getBoard();
        GameState gameState = model.getGameState();
        List<Position> addList = new ArrayList<Position>();
        List<Position> occList = new ArrayList<Position>();

        occList = model.getBoard().searchForOccupied(model.getCurrentConstructor().getPos());
        for (Position p : occList){
            Tile enemyTile = board.getTile(p);
            if(enemyTile.getActualConstructor().getPlayerNumber() != gameState.getCurrentPlayer().getPlayerNumber()){
                if(board.getTile(model.getCurrentConstructor().getPos()).getConstructionLevel() + (model.getBoard().getCanGoUp()? 1 : 0) >= enemyTile.getConstructionLevel()){
                    if(model.getBoard().possibleBuild(enemyTile.getActualConstructor()).size() >= 1){
                        addList.add(p.clone());
                    }
                }
            }
        }
        
        if(addList.size() ==  0){
            return null;
        }
        return addList;
    }
}
