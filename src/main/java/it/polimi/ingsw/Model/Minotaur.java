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

    /**
     * Special version of changeActiveConstructors
     * <p>
     *     This method will deactivate a constructor only if there aren't any standard
     *     moves left and there aren't any enemy's constructors to push nearby
     *     (and obviously it'll activate the constructor if there's at least one move available)
     * </p>
     *
     * @param model the <em>Model</em> of the game
     */
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

            //if there is at least one standard move or at least one position where the enemy's constructor
            //can be pushed, change setCanMove to true
            c.setCanMove(listMove.size() != 0 || numEnemyOK != 0);
        }
    }

    /**
     * Method used to get the list of special <em>Positions</em> for Minotaur
     * The positions are the one occupied by an enemy that can be pushed
     *
     * @param model the <em>Model</em> of the game
     * @return the list of special <em>Positions</em>
     */
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

    /**
     * Helper method used to calculate the <em>Position</em> where the constructor will be pushed
     *
     * @param current <em>Position</em> of the currentConstructor
     * @param other <em>Position</em> of the enemy constructor
     *
     * @return the <em>Position</em> where the currentConstructor will be pushed
     */
    protected Position calculatePushedPos(Position current, Position other){
        int row = (other.getRow() - current.getRow()) + other.getRow();
        int col = (other.getCol() - current.getCol()) + other.getCol();

        return new Position(row, col);
    }

    /**
     * Method used to check if the given <em>Position</em> exists
     *
     * @param pos <em>Position</em> to check
     *
     * @return if the <em>Position</em> is a good one, return true
     */
    protected boolean isGood(Position pos){
        return (0 <= pos.getCol() && pos.getCol() <= 4) && (0 <= pos.getRow() && pos.getRow() <= 4);
    }
}
