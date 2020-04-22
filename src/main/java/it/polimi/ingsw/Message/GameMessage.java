package it.polimi.ingsw.Message;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.PossiblePhases;

public abstract class GameMessage {
    private final String code;
    private String message;
    private final Player player;
    private final PossiblePhases currentPhase;

    public GameMessage(String code, Player player, PossiblePhases phase) {
        this.code = code;
        this.player = player;
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

    public Player getPlayer() {
        return this.player;
    }

    public String getCode(){
        return this.code;
    }
}
