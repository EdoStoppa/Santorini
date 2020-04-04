package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.God.God;

import java.util.List;


/**
 * Represents the state of the Game, holding all the player, the Player who's playing and the current phase of each turn
 */
public class GameState {
    private List<Player> playerList;
    private Player currentPlayer;
    private PossiblePhases currentPhase;

    /**
     * Creates a GameState in which the playerList is the parameter passed,  set currentPlayer as the firs in the list pList
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

    /**
     * Set the game at the starting point
     */
    public void startGame(){
        this.currentPlayer = this.playerList.get(0);
        this.currentPhase  = this.currentPlayer.getGod().getPhasesList().get(0);
    }


    /**
     * Used to change the phase during a turn. If the game is on END_TURN then nothing is done and simply return false, otherwise return true
     *
     * @return boolean used to know if the change of phase ended correctly or there's the need of a new turn
     */
    public boolean nextPhase(){
        if(this.currentPhase == PossiblePhases.END_TURN){
            return false;
        } else {
            God g = this.currentPlayer.getGod();
            List<PossiblePhases> phasesList = g.getPhasesList();
            this.currentPhase = phasesList.get(phasesList.indexOf(this.currentPhase)+1);
            return true;
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
     * Used to change the playng player
     *
     * @return Player who is the next player
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
        this.playerList.remove(p);
    }

}
