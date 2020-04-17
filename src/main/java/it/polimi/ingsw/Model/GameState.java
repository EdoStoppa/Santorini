package it.polimi.ingsw.Model;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents the state of the Game, holding all the players, the player who's playing and the current phase of each turn
 */
public class GameState {
    private List<Player> playerList;
    private Player currentPlayer;
    private PossiblePhases currentPhase;

    /**
     * Creates a <em>GameState</em> in which the playerList is the parameter passed, set currentPlayer as the firs in the list pList
     * and set the currentPhase as the first phase contained in the player's god card
     *
     * @param pList List of playing players
     */
    public GameState(List<Player> pList){
        this.playerList = pList;
        this.currentPlayer = pList.get(0);
        this.currentPhase  = this.currentPlayer.getGod().getPhasesList().get(0);
    }

    public Player getCurrentPlayer(){
        return this.currentPlayer;
    }

    public PossiblePhases getCurrentPhase(){
        return this.currentPhase;
    }

    public List<Player> getPlayerList(){
        return this.playerList;
    }

    /**
     * Set the game at the starting point
     */
    public void startGame(){
        this.currentPlayer = this.playerList.get(0);
        this.currentPhase  = this.currentPlayer.getGod().getPhasesList().get(0);
    }


    /**
     * Method used to change the turn's phase
     * If a next phase exist simply refreshes <em>GameState</em>, otherwise throws an exception
     *
     * @throws IndexOutOfBoundsException to let know the caller that there's no more phases left
     */
    public void nextPhase() throws IndexOutOfBoundsException{
        God g = this.currentPlayer.getGod();
        List<PossiblePhases> phasesList = g.getPhasesList();
        int index = phasesList.indexOf(this.currentPhase);

        if(index == phasesList.size() -1){
            throw new IndexOutOfBoundsException("No more phases, required new turn");
        } else {
            this.currentPhase = phasesList.get(index +1);
        }
    }

    /**
     * Used to change the current turn, so changes the player and set the first phase
     */
    public void nextTurn(){
        this.currentPlayer = nextPlayer();
        this.currentPhase  = this.currentPlayer.getGod().getPhasesList().get(0);
    }

    /**
     * Used to change the playing player
     *
     * @return <em>Player</em> who is the next player
     */
    private Player nextPlayer(){
        int pLen = playerList.size();
        int posP = playerList.indexOf(currentPlayer);

        if(posP == pLen - 1){
            return this.playerList.get(0);
        }  else {
            return this.playerList.get(posP + 1);
        }
    }

    public void removePlayer(Player p){
        if(p == getCurrentPlayer()){
            this.currentPlayer = nextPlayer();
        }
        this.playerList.remove(p);
    }

    protected void setCurrentPhase(PossiblePhases phase){
        this.currentPhase = phase;
    }

//----------------------------------------------------------------------------------------------------------------------
//                                             FOR TESTING PURPOSE
//----------------------------------------------------------------------------------------------------------------------

    protected void setCurrentPlayer(Player p)   {
        this.currentPlayer = p;
    }

    protected void setPlayerList(ArrayList<Player> list)    {
        this.playerList = list;
    }
}
