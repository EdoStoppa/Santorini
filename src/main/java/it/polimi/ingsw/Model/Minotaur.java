package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.GodController.MinotaurController;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the god card: Minotaur
 */
public class Minotaur extends God {
    protected final static String godName = "Minotaur";
    protected final static String godSubtitle = "Bull-headed Monster";
    protected final static String powerDescription = "Your Move: Your Worker may move into an opponent Worker's space, if their Worker can be forced one space straight backwards to and unoccupied space at any level.";

    /**
     * It creates the card Minotaur, set the correct sequence of phases and assign the correct GodController
     */
    public Minotaur(){
        this.phasesList = new ArrayList<PossiblePhases>();
        this.phasesList.add(PossiblePhases.SPECIAL_CHOOSE_CONSTRUCTOR);
        this.phasesList.add(PossiblePhases.SPECIAL_MOVE);
        this.phasesList.add(PossiblePhases.BUILD);

        this.godController = new MinotaurController();
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

    public void changeActiveConstructors(Model model){
        Board board = model.getBoard();
        GameState gameState = model.getGameState();

        List<Position> listMove, listOcc;

        //for every constructor of the current player
        for(Constructor c : gameState.getCurrentPlayer().getAllConstructors())  {
            int numEnemyOK = 0;
            // get all possible standard move position
            listMove = board.possibleMoveset(c);
            // get all occupied position
            listOcc = board.searchForOccupied(c.getPos());

            // for every occupied position
            for (Position position : listOcc) {
                //if it isn't occupied by the current player
                if (board.getTile(position).getActualConstructor().getPlayerNumber() != gameState.getCurrentPlayer().getPlayerNumber()) {
                    // if the player can actually move on this tile
                    if(board.getTile(c.getPos()).getConstructionLevel()+1 >= board.getTile(position).getConstructionLevel()){
                        Position futurePos = calculatePushedPos(model.getCurrentConstructor().getPos(), position);
                        // if the future position where the enemy constructor will be pushed make sense
                        if(isGood(futurePos)){
                            // only if the future tile is not already occupied or has a dome
                            if(!board.getTile(futurePos).getOccupied() && !board.getTile(futurePos).getDome()){
                                numEnemyOK += 1;
                            }
                        }
                    }
                }
            }

            // if the constructor has no standard move and no special move possible, deactivate it
            if(listMove.size() == 0 && numEnemyOK == 0)    {
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
            // if is occupied by an enemy constructor
            if(board.getTile(p).getActualConstructor().getPlayerNumber() != gameState.getCurrentPlayer().getPlayerNumber()){
                // if is a possible standard move
                if(board.getTile(model.getCurrentConstructor().getPos()).getConstructionLevel()+1 >= board.getTile(p).getConstructionLevel()){
                    Position futurePos = calculatePushedPos(model.getCurrentConstructor().getPos(), p);
                    //if the future position is a real position
                    if(isGood(futurePos)){
                        // if the future tile is not occupied/has a dome
                        if(!model.getBoard().getTile(futurePos).getDome() && !model.getBoard().getTile(futurePos).getOccupied()){
                            addList.add(p.clone());
                        }
                    }
                }
            }
        }

        if(addList.size() ==  0){
            return null;
        }
        return addList;
    }

    protected Position calculatePushedPos(Position current, Position other){
        int row = (other.getRow() - current.getRow()) + other.getRow();
        int col = (other.getCol() - current.getCol()) + other.getCol();

        return new Position(row, col);
    }

    protected boolean isGood(Position pos){
        return (0 <= pos.getCol() && pos.getCol() <= 4) && (0 <= pos.getRow() && pos.getRow() <= 4);
    }
}
