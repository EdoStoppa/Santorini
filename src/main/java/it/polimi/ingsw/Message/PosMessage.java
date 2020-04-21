package it.polimi.ingsw.Message;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.View.View;

public class PosMessage extends GameMessage {
    private final View view;
    private final Position position;

    public PosMessage(String message,Player player, View view, Position position) {
        super(message,player);
        this.view = view;
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public View getView(){
        return this.view;
    }

}
