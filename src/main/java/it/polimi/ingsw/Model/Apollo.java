package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.GodController.ApolloController;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the god card: Apollo
 */
public class Apollo extends God {

    /**
     * It creates the card Apollo, set all the specific data (as godName. godSubtitle and powerDescription)
     * the correct sequence of phases and in the end assign he correct GodController
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

    public void changeActiveConstructors(Model model){
        Board board = model.getBoard();
        GameState gameState = model.getGameState();

        List<Position> listMove, listOcc;

        for(Constructor c : gameState.getCurrentPlayer().getAllConstructors())  {
            int numEnemyOcc = 0;
            listMove = board.possibleMoveset(c);
            listOcc = board.searchForOccupied(c.getPos());

            for (Position position : listOcc) {
                if (board.getTile(position).getActualConstuctor().getPlayerNumber() != gameState.getCurrentPlayer().getPlayerNumber()) {
                    if(board.getTile(c.getPos()).getConstructionLevel()+1 >= board.getTile(position).getConstructionLevel()){
                        numEnemyOcc += 1;
                    }
                }
            }

            if(listMove.size() == 0 && numEnemyOcc == 0)    {
                c.setCanMove(false);
            } else {
                c.setCanMove(true);
            }
        }
    }

    public List<Position> getMoveAddList(Model model){
        Board board = model.getBoard();
        GameState gameState = model.getGameState();
        List<Position> addList = new ArrayList<Position>();
        List<Position> occList = new ArrayList<Position>();

        occList = model.getBoard().searchForOccupied(model.getCurrentConstructor().getPos());
        for (Position p : occList){
            if(board.getTile(p).getActualConstuctor().getPlayerNumber() != gameState.getCurrentPlayer().getPlayerNumber()){
                if(board.getTile(model.getCurrentConstructor().getPos()).getConstructionLevel()+1 >= board.getTile(p).getConstructionLevel()){
                    addList.add(p.clone());
                }
            }
        }
        
        if(addList.size() ==  0){
            return null;
        }
        return addList;
    }
}
