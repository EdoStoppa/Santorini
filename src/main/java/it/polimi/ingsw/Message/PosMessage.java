package it.polimi.ingsw.Message;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.View.View;

public class PosMessage extends GameMessage {
    private final Position position;
    private final View view;

    public PosMessage(String message,Player player,Position position, View view) {
        super(message,player);
        this.position = position;
        this.view = view;
    }

    public Position getPosition() {
        return position;
    }

    public View getView() {
        return view;
    }
}
