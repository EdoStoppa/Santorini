package it.polimi.ingsw.Message;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.View.View;

public class FromClientMessage extends GameMessage{
    private final View view;

    public FromClientMessage(String message, Player player, View view) {
        super(message, player);
        this.view = view;
    }
    public View getView() {
        return view;
    }
}
