package it.polimi.ingsw.Message;

import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.View.View;

/**
 * This is a special class created by the view only. After receiving a message during the game from the Client,
 * the view parses it and than creates one of this message that will be consequently used by the rest of the Application
 * to process the Client choice.
 */
public class PosMessage {
    private final String code;
    private final String player;
    private final View view;
    private final Position position;

    public PosMessage(String code, String player, View view, Position position) {
        this.code = code;
        this.player = player;
        this.view = view;
        this.position = position;
    }

    public String getIdPlayer() {
        return this.player;
    }

    public Position getPosition() {
        return position;
    }

    public View getView(){
        return this.view;
    }

    public String getCode() {
        return code;
    }
}
