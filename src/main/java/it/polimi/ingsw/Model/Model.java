package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.GodController.GodController;
import it.polimi.ingsw.Message.BuildMessages.BuildMessage;
import it.polimi.ingsw.Message.BuildMessages.StandardBuildMessage;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.MoveMessages.MoveMessage;
import it.polimi.ingsw.Message.MoveMessages.PushMessage;
import it.polimi.ingsw.Message.MoveMessages.RemovedPlayerMessage;
import it.polimi.ingsw.Message.MoveMessages.StandardMoveMessage;
import it.polimi.ingsw.Message.MoveMessages.SwapMessage;
import it.polimi.ingsw.Message.TileToShowMessages.CanEndTileMessage;
import it.polimi.ingsw.Message.TileToShowMessages.StandardTileMessage;
import it.polimi.ingsw.Observer.Observable;

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
    public boolean isPlayerTurn(String p) {
        Player currentP = gameState.getCurrentPlayer();
        return p.equals(currentP.getIdPlayer());
    }

    /**
     * It sets currentConstructor to the actual <em>Constructor</em> in the position passed as parameter.
     *
     * @param pos   The <em>Position</em> where there's the constructor the player wants to choose.
     */
    public void performChooseConstructor(Position pos) {
        setCurrentConstructor(board.getTile(pos).getActualConstructor());
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

        notify(new StandardMoveMessage(gameState.getCurrentPlayer().getIdPlayer(), gameState.getCurrentPhase(), matrix));
    }

    /**
     * This method performs the special movement of the <em>God</em> Apollo: he can swap his <em>Constructor</em>
     * with one of the enemy player, but only if the enemy <em>Constructor</em> is 1 <em>Tile</em> away from him.
     *
     * @param pos   the <em>Position</em> of the enemy <em>Constructor</em>
     */
    public void performSwap(Position pos)   {
        Constructor swappedConstructor = board.getTile(pos).getActualConstructor();
        board.swapConstructors(currentConstructor, swappedConstructor);

        int[][] matrix = board.createConstructorMatrix();

        MoveMessage message = new SwapMessage(gameState.getCurrentPlayer().getIdPlayer(), gameState.getCurrentPhase(), matrix);
        message.setMessage(swappedConstructor.getPos().toString());
        notify(message);
    }

    /**
     * This method performs the special movement of the <em>God</em> Minotaur: he can move his <em>Constructor</em>
     * to the position of the enemy player one, but only if the enemy <em>Constructor</em> is in the same
     * row/column that he is.
     *
     * @param pos   the <em>Position</em> of the enemy <em>Constructor</em>
     */
    public  void performPush(Position pos)  {
        Constructor pushedConstructor = board.getTile(pos).getActualConstructor();

        board.pushConstructor(pushedConstructor, currentConstructor);

        int[][] matrix = board.createConstructorMatrix();

        MoveMessage message = new PushMessage(gameState.getCurrentPlayer().getIdPlayer(), gameState.getCurrentPhase(), matrix);
        message.setMessage(pushedConstructor.getPos().toString());
        notify(message);
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

        BuildMessage message = new StandardBuildMessage(gameState.getCurrentPlayer().getIdPlayer(), gameState.getCurrentPhase(), matrix);
        message.setMessage(pos.toString());
        notify(message);
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

        notify(new StandardTileMessage(gameState.getCurrentPlayer().getIdPlayer(), gameState.getCurrentPhase(), list));
    }

    /**
     * This method checks if every <em>Constructor</em> of the current <em>Player</em> can perform a move. If it can
     * not, the <em>Constructor</em> is deactivated.
     */
    public void changeActiveConstructors() {
        List<Position> list;

        for(Constructor c : gameState.getCurrentPlayer().getAllConstructors())  {

            list = board.possibleMoveset(c);
            c.setCanMove(list.size() != 0);
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

        if(deleteList == null){
            notify(new StandardTileMessage(gameState.getCurrentPlayer().getIdPlayer(), gameState.getCurrentPhase(), list));
        } else {
            notify(new CanEndTileMessage(gameState.getCurrentPlayer().getIdPlayer(), gameState.getCurrentPhase(), list));
        }
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

        if(deleteList == null){
            notify(new StandardTileMessage(gameState.getCurrentPlayer().getIdPlayer(), gameState.getCurrentPhase(), list));
        } else {
            notify(new CanEndTileMessage(gameState.getCurrentPlayer().getIdPlayer(), gameState.getCurrentPhase(), list));
        }

    }

    public boolean isLosing()  {
        return isLosing(gameState.getCurrentPlayer());
    }

    /**
     * It destroys every phase of the current player.
     */
    public void destroyRemainingPhases()    {
        List<PossiblePhases> list = getCurrentGod().getPhasesList();
        list.subList(1, list.size()).clear();
    }

    public God getCurrentGod()  {
        return gameState.getCurrentPlayer().getGod();
    }

    public void forceNotify(GameMessage message)    {
        notify(message);
    }

    public String getCurrentPlayerId(){
        return gameState.getCurrentPlayer().getIdPlayer();
    }

    public void startGame() {
        gameState.startGame();
    }

    /**
     * This method removes a <em>Player</em> from the game
     *
     * @param playerId  id of the <em>Player</em> we want to remove
     */
    public void removePlayer(String playerId)   {
        Player playerR = null;
        List<Player> playerList;
        List<Constructor> constructorList;
        for(int i = 0; i < gameState.getPlayerList().size(); i++)   {
            if(gameState.getPlayerList().get(i).getIdPlayer().equals(playerId)) {
                playerR = gameState.getPlayerList().get(i);
            }
        }
        constructorList = playerR.getAllConstructors();
        for(Constructor c : constructorList)   {
            board.getTile(c.getPos()).setActualConstructor(null);
            board.getTile(c.getPos()).setOccupied(false);
            c.setPos(new Position(-1, -1));
        }
        constructorList.clear();
        playerList = gameState.getPlayerList();
        for(int i = 0; i < playerList.size(); i++)   {
            if(playerId.equals(playerList.get(i).getIdPlayer())) {
                playerList.remove(i);
                break;
            }
        }
        int[][] matrix = board.createConstructorMatrix();

        notify(new RemovedPlayerMessage(playerR.getIdPlayer(), getCurrentPhase(), matrix));
    }

    protected Board getBoard()  {
        return board;
    }

    protected GameState getGameState()  {
        return gameState;
    }

    public Constructor getCurrentConstructor()   {return  currentConstructor;}

    /**
     * Helper method, it checks every parameter that will be add in the tileToShowList;
     *
     * @param list list of <em>Positions</em> where the <em>Player</em> perform a move/build
     * @param addList   other <em>Positions</em> where the <em>Player</em> can move/build
     * @param deleteList    other <em>Positions</em> where the <em>Player</em> can't move
     * @return  final list of <em>Positions</em> where the <em>Player</em> can perform a move/build
     */
    private List<Position> checkListsParameter(List<Position> list, List<Position> addList, List<Position> deleteList)  {
        if(addList != null) {
            for(Position position : addList) {
                boolean flag = true;
                for(int j = 0; j < list.size() && flag; j++) {
                    if (list.get(j).equals(position)) {
                        flag = false;
                    }
                }
                if(flag) {
                    list.add(position);
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