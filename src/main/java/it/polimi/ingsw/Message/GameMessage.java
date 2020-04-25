package it.polimi.ingsw.Message;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Model.PossiblePhases;

public abstract class GameMessage {
    private String message;
    private final String idPlayer;
    private final PossiblePhases currentPhase;

    public GameMessage(String player, PossiblePhases phase) {
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

    public String getIdPlayer() {
        return this.idPlayer;
    }

    public abstract void autoSetMessage(boolean isMyTurn, boolean isCLI);

    public abstract void updatePlaySpace(PlaySpace playSpace, boolean isMyTurn);
}
