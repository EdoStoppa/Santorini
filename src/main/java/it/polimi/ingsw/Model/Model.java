package it.polimi.ingsw.Model;

import it.polimi.ingsw.Message.BuildMessage;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.MoveMessage;
import it.polimi.ingsw.Observer.Observable;

import java.util.List;

public class Model extends Observable<GameMessage> {
    private GameState gameState;
    private Board board;
    private List<Position> tileToShow;
    private Constructor currentConstructor;

    public Model(List<Player> playerList){
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
    public boolean isOccupied(Position p){
        Tile t = board.getTile(p);
        return t.getOccupied();
    }

    /**
     * Method used to change the current phase of the game. If there's no more phases for the current
     * <em>Player</em> then the method change turn instead of the phase
     */
    public void nextPhase(){
        try{
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
    public boolean isLosing(Player p){
        List<Constructor> constructorList = p.getAllConstructors();
        for(Constructor c : constructorList){
            if(c.getCanMove()){
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
    public boolean checkWin(){
        Tile currentTile = board.getTile(currentConstructor.getPos());
        return (currentTile.getConstructionLevel() == 3);
    };

    /**
     * Mtehod used to check if it's the turn of the given <em>Player</em>
     *
     * @param p <em>Player</em> to check
     * @return if it's his/hers turn
     */
    public boolean isPlayerTurn(Player p){
        Player currentP = gameState.getCurrentPlayer();
        return p.getIdPlayer().equals(currentP.getIdPlayer());
    }

    /**
     * Method used to change the <em>Position</em> of the current <em>Constructor</em> from his old one
     * to the one that's passed as input.
     * After moving the <em>Constructor</em>, the method create a new ConstructorMatrix and send (through a notify())
     * a new MoveMessage to every <em>Observer</em>
     *
     * @param pos <em>Position</em> where to move the currentConstructor
     */
    public void performMove(Position pos){
        Tile t = board.getTile(pos);
        board.placeConstructor(t, currentConstructor);

        int[][] matrix = board.createConstructorMatrix();
        String message = gameState.getCurrentPlayer().getIdPlayer() + " moved to position: " + pos.toString();

        notify(new MoveMessage(message , gameState.getCurrentPlayer(), gameState.getCurrentPhase(), matrix));
    }

    /**
     * Method used to build on the <em>Tile</em> in the <em>Position</em> that's passed as input.
     * After raising the level of the building, the method create a new BuildingMatrix and send (through a notify())
     * a new BuildMessage to every <em>Observer</em>
     *
     * @param pos <em>Position</em> where to build
     */
    public void performBuild(Position pos){
        Tile t = board.getTile(pos);
        board.placeBuilding(t);

        int[][] matrix = board.createBuildingMatrix();
        String message = gameState.getCurrentPlayer().getIdPlayer() + " built on position: " + pos.toString();

        notify(new BuildMessage(message , gameState.getCurrentPlayer(), gameState.getCurrentPhase(), matrix));
    }

    public List<Player> getListPlayer(){
        return gameState.getPlayerList();
    }

    public PossiblePhases getCurrentPhase(){
        return gameState.getCurrentPhase();
    }

}
