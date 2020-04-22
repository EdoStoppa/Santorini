package it.polimi.ingsw.Message;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.PossiblePhases;

public abstract class GameMessage {
    private final String code;
    private String message;
    private final String idPlayer;
    private final PossiblePhases currentPhase;

    public GameMessage(String code, String player, PossiblePhases phase) {
        this.code = code;
        this.idPlayer = player;
        this.currentPhase = phase;
    }

    public PossiblePhases getPhase(){
        return this.currentPhase;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String s){
        this.message = s;
    }

    public abstract void autoSetMessage(boolean isMyTurn, boolean isCLI);

    public String getIdPlayer() {
        return this.idPlayer;
    }

    public String getCode(){
        return this.code;
    }
}
