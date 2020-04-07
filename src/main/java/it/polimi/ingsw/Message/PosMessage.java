package it.polimi.ingsw.Message;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.View.View;

public class PosMessage extends FromClientMessage {
    private final Position position;

    public PosMessage(String message,Player player, View view, Position position) {
        super(message,player, view);
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

}
