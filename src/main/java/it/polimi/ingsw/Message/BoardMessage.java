package it.polimi.ingsw.Message;

import it.polimi.ingsw.Model.GameState;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.PossiblePhases;

public class BoardMessage extends GameMessage {
    private final GameState gameState;
    private final PossiblePhases currentphase;

    public BoardMessage(String message, Player player, GameState gameState, PossiblePhases currentphase) {
        super(message, player);
        this.gameState = gameState;
        this.currentphase = currentphase;
    }

    public GameState getGameState() {
        return gameState;
    }

    public PossiblePhases getCurrentphase() {
        return currentphase;
    }
}
