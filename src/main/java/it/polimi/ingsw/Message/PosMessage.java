package it.polimi.ingsw.Message;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.View.View;

public class PosMessage {
    private final String code;
    private String message;
    private final String player;
    private final View view;
    private final Position position;

    public PosMessage(String code, String player, View view, Position position) {
        this.code = code;
        this.player = player;
        this.view = view;
        this.position = position;
    }
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String s){
        this.message = s;
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

}
