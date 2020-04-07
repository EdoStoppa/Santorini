package it.polimi.ingsw.Message;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.View.View;

public class SpecialActionMessage extends FromClientMessage {
    private final Object obj;

    public SpecialActionMessage(String message, Player player, View view, Object obj) {
        super(message, player, view);
        this.obj = obj;
    }

    public Object getObj(){
        return this.obj;
    }
}
