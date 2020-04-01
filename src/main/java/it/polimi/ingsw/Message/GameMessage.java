package it.polimi.ingsw.Message;

import it.polimi.ingsw.Model.Player;

public class GameMessage {
    private final String message;
    private final Player player;

    public GameMessage(String message, Player player) {
        this.message = message;
        this.player = player;
    }


    public String getMessage() {
        return message;
    }

    public Player getPlayer() {
        return player;
    }
}
