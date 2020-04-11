package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.GodController.GodController;
import it.polimi.ingsw.Message.BuildMessage;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.MoveMessage;
import it.polimi.ingsw.Message.TileToShowMessage;
import it.polimi.ingsw.Observer.Observable;

import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Model extends Observable<GameMessage> {
    private GameState gameState;
    private Board board;
    private List<Position> tileToShow;
    private Constructor currentConstructor;

    public Model(List<Player> playerList) {
        this.gameState = new GameState(playerList);
        this.board = new Board();
        this.tileToShow = null;
        this.currentConstructor = null;
    }

    /**
     * Method used to know if the <em>Tile</em> corresponding to the <em>Position</em> passed
     * as input is already occupied by someone
     *
     * @param p <em>Position</em> where the <em>Tile</em> is located
     * @return if the <em>Tile</em> is already occupied
     */
    public boolean isOccupied(Position p) {
        Tile t = board.getTile(p);
        return t.getOccupied();
    }

    /**
     * Method used to change the current phase of the game. If there's no more phases for the current
     * <em>Player</em> then the method change turn instead of the phase
     */
    public void nextPhase() {
        try {
            this.gameState.nextPhase();
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            this.gameState.nextTurn();
        }
    }

    public List<Position> getTileToShow() {
        return tileToShow;
    }

    public void setTileToShow(List<Position> tileToShow) {
        this.tileToShow = tileToShow;
    }

    public void setCurrentConstructor(Constructor currentConstructor) {
        this.currentConstructor = currentConstructor;
    }

    /**
     * Method used to know if a <em>Player</em> is losing due to the inability to move
     * any of his/hers <em>Constructor</em>
     *
     * @param p <em>Player</em> to check
     * @return if the <em>Player</em> is losing
     */
    public boolean isLosing(Player p) {
        List<Constructor> constructorList = p.getAllConstructors();
        for (Constructor c : constructorList) {
            if (c.getCanMove()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method used to check if the current <em>Player</em> won after performing any king of Move phase
     *
     * @return if the <em>Player</em> is now located on a level 3 construction
     */
    public boolean checkWin() {
        Tile currentTile = board.getTile(currentConstructor.getPos());
        return (currentTile.getConstructionLevel() == 3);
    }

    /**
     * The method is used to check if it's the turn of the given <em>Player</em>
     *
     * @param p <em>Player</em> to check
     * @return if it's his/hers turn
     */
    public boolean isPlayerTurn(Player p) {
        Player currentP = gameState.getCurrentPlayer();
        return p.getIdPlayer().equals(currentP.getIdPlayer());
    }

    /**
     * It sets currentConstructor to the actual <em>Constructor</em> in the position passed as parameter.
     *
     * @param pos   The <em>Position</em> where there's the constructor the player wants to choose.
     */
    public void performChooseConstructor(Position pos) {
        setCurrentConstructor(board.getTile(pos).getActualConstuctor());
    }

    /**
     * Method used to change the <em>Position</em> of the current <em>Constructor</em> from his old one
     * to the one that's passed as input.
     * After moving the <em>Constructor</em>, the method create a new ConstructorMatrix and send (through a notify())
     * a new MoveMessage to every <em>Observer</em>
     *
     * @param pos <em>Position</em> where to move the currentConstructor
     */
    public void performMove(Position pos) {
        Tile t = board.getTile(pos);
        board.placeConstructor(t, currentConstructor);

        int[][] matrix = board.createConstructorMatrix();
        String message = gameState.getCurrentPlayer().getIdPlayer() + " moved to position: " + pos.toString();

        notify(new MoveMessage(message, gameState.getCurrentPlayer(), gameState.getCurrentPhase(), matrix));
    }

    /**
     * Method used to build on the <em>Tile</em> in the <em>Position</em> that's passed as input.
     * After raising the level of the building, the method create a new BuildingMatrix and send (through a notify())
     * a new BuildMessage to every <em>Observer</em>
     *
     * @param pos <em>Position</em> where to build
     */
    public void performBuild(Position pos) {
        Tile t = board.getTile(pos);
        board.placeBuilding(t);
        currentConstructor.setLastBuildPos(pos.clone());

        int[][] matrix = board.createBuildingMatrix();
        String message = gameState.getCurrentPlayer().getIdPlayer() + " built on position: " + pos.toString();

        notify(new BuildMessage(message, gameState.getCurrentPlayer(), gameState.getCurrentPhase(), matrix));
    }

    public List<Player> getListPlayer() {
        return gameState.getPlayerList();
    }

    public PossiblePhases getCurrentPhase() {
        return gameState.getCurrentPhase();
    }

    /**
     * This method return the <em>GodController</em> of the <em>God</em> the current <em>Player</em> has.
     *
     * @return The <em>GodController</em> of the current player's <em>God</em>
     */
    public GodController getCurrentPlayerController()   {
        return gameState.getCurrentPlayer().getGod().getGodController();
    }

    /**
     * This method checks if the <em>Player</em> is the only one that can perform a move
     *
     * @return true if he is the only one that can perform a move, false if he is not.
     */
    public boolean isLastStanding() {
        return (gameState.getPlayerList().size() == 1 && gameState.getPlayerList().get(0).equals(gameState.getCurrentPlayer()));
    }

    /**
     * This method is used to create an array of <em>Position</em>, which contains every <em>Constructor</em> of the
     * current <em>Player</em> that can actually move. It notify observers by a <em>TileToShowMessage</em>.
     */
    public void createPossibleConstructorPos()  {
        List<Position> list = new ArrayList<>();

        for(Constructor c : gameState.getCurrentPlayer().getAllConstructors())  {
            if(c.getCanMove())  {
                list.add(c.getPos().clone());
            }
        }
        setTileToShow(list);
        String message = gameState.getCurrentPlayer().getIdPlayer() + " can choose between " + list.size() + " constructors";
        notify(new TileToShowMessage(message, gameState.getCurrentPlayer(), gameState.getCurrentPhase(), list));
    }

    /**
     * This method checks if every <em>Constructor</em> of the current <em>Player</em> can perform a move. If it can
     * not, the <em>Constructor</em> is deactivated.
     */
    public void deactivateConstructorIfNeeded() {
        List<Position> list;

        for(Constructor c : gameState.getCurrentPlayer().getAllConstructors())  {
            if(c.getCanMove())  {
                list = board.possibleMoveset(c);
                if(list.size() == 0)    {
                    c.setCanMove(false);
                }
            }
        }
    }

    /**
     * This method creates an ArrayList which contains every possible move the <em>Constructor</em> can perform.
     * It helps to control the special power of certain <em>Gods</em>
     *
     * @param addList   list of <em>Positions</em> that has to be added to list
     * @param deleteList    list of <em>Positions</em> that has to be removed from list
     */
    public void createPossibleMovePos(List<Position> addList, List<Position> deleteList)   {
        List<Position> list;

        list = board.possibleMoveset(currentConstructor);
        list = checkListsParameter(list, addList, deleteList);
        setTileToShow(list);
        String message = gameState.getCurrentPlayer().getIdPlayer() + " can move to any of these tiles";
        notify(new TileToShowMessage(message, gameState.getCurrentPlayer(), gameState.getCurrentPhase(), list));
    }

    /**
     * This method creates an ArrayList which contains every possible <em>Position</em> the <em>Constructor</em> can perform a build.
     * It helps to control the special power of certain <em>Gods</em>
     *
     * @param addList   list of <em>Positions</em> that has to be added to list
     * @param deleteList    list of <em>Positions</em> that has to be removed from list
     */
    public void createPossibleBuildPos(List<Position> addList, List<Position> deleteList)  {
        List<Position> list;

        list = board.possibleBuild(currentConstructor);
        list = checkListsParameter(list, addList, deleteList);
        setTileToShow(list);
        String message = gameState.getCurrentPlayer().getIdPlayer() + " can build on any of these tiles";
        notify(new TileToShowMessage(message, gameState.getCurrentPlayer(), gameState.getCurrentPhase(), list));
    }

    public boolean isLosing()  {
        return isLosing(gameState.getCurrentPlayer());
    }

    /**
     * It destroys every phase of the current player.
     */
    public void destroyRemainingPhases()    {
        List<PossiblePhases> list = getCurrentGod().getPhasesList();

        for(int i = list.size() - 1; i >= 1; i--)   {
            list.remove(i);
        }
    }

    public God getCurrentGod()  {
        return gameState.getCurrentPlayer().getGod();
    }

    protected Board getBoard()  {
        return board;
    }

    protected GameState getGameState()  {
        return gameState;
    }

    protected Constructor getCurrentConstructor()   {return  currentConstructor;}

    private List<Position> checkListsParameter(List<Position> list, List<Position> addList, List<Position> deleteList)  {
        if(addList != null) {
            for(int i = 0; i < addList.size(); i++) {
                boolean flag = true;
                for (int j = 0; j < list.size() && flag; j++) {
                    if (list.get(j).equals(addList.get(i))) {
                        flag = false;
                    }
                }
                if(flag)    {
                    list.add(addList.get(i));
                }
            }
        }
        if(deleteList != null)  {
            for(Position delPos : deleteList)  {
                for(int i = 0; i < list.size(); i++)   {
                    if(list.get(i).equals(delPos)) {
                        list.remove(i);
                        break;
                    }
                }
            }
        }
        return list;
    }
}