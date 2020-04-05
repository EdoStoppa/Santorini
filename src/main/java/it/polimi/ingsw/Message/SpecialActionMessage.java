package it.polimi.ingsw.Message;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.View.View;

public class SpecialActionMessage extends GameMessage {
    private final View view;

    public SpecialActionMessage(String message, Player player, View senderView) {
        super(message, player);
        this.view = senderView;
    }

    public View getView(){
        return this.view;
    }
}
