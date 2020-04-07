package it.polimi.ingsw.Model;

import it.polimi.ingsw.Message.GameMessage;
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

    public boolean isOccupied(Position p){
        Tile t = board.getTile(p);
        return t.getOccupied();
    }

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

    public boolean isLosing(Player p){
        List<Constructor> constructorList = p.getAllConstructors();
        for(Constructor c : constructorList){
            if(c.getCanMove()){
                return false;
            }
        }
        return true;
    }

    // TO DO: change from void to Player and implement
    public void checkWinner(){};

    public boolean isPlayerTurn(Player p){
        return p.getIdPlayer().equals(gameState.getCurrentPlayer().getIdPlayer());
    }

    public List<Player> getListPlayer(){
        return gameState.getPlayerList();
    }

}
