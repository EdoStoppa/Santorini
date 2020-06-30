package it.polimi.ingsw.Message;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Model.PossiblePhases;

import java.io.Serializable;

/**
 * This is the abstract class representing all the message that are generated within the MVC core of our project.
 *
 * Every message generate by a change of the Model will have 3 important attributes:
 *  - message: an helper string used to generate the correct message which will be shown to the user
 *  - idPlayer: the id (a String) of the player who generate this message
 *  - currentPhase: the phase within the message was generated
 *
 *  All this message will be used to set the correct MiniController to the correct Client, and to modify their
 *  PlaySpace (and GUI if they use this client mode)
 */
public abstract class GameMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message;
    private final String idPlayer;
    private final PossiblePhases currentPhase;

    public GameMessage(String player, PossiblePhases phase) {
        this.idPlayer = player;
        this.currentPhase = phase;
    }

    public PossiblePhases getPhase(){
        return this.currentPhase;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String s){
        this.message = s;
    }

    public String getIdPlayer() {
        return this.idPlayer;
    }

    /**
     * This method is used to generate automatically the correct message that will be shown to the player.
     *
     * This method will be call only on the Client, because requires information not known by the Server
     */
    public abstract void autoSetMessage(boolean isMyTurn, boolean isCLI);

    /**
     * This method is used to update the PlaySpace on the Client
     *
     * @param playSpace The Client's PlaySpace
     */
    public abstract void updatePlaySpace(PlaySpace playSpace);

    /**
     * This method is used only by the GUI Client, and will used different method in the PlaySpace class to
     * change the visual representation of the Game state
     *
     * @param playSpace Client's PlaySpace that represents the Board and the current state of the Game
     */
    public abstract void updateGUI(PlaySpace playSpace);
}
