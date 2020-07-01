package it.polimi.ingsw.Controller.MiniController;

import it.polimi.ingsw.Client.PlaySpace;

import java.io.Serializable;

/**
 * Basic MiniController
 */
public class BaseMiniController implements MiniController, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * This method parses the user's input just by checking if input.length() >= 1
     * @param input The input received from the user
     * @param playSpace The playspace where to search for the position(not needed)
     * @param stringBuilder A string placeholder to return the correct message to the player
     * @return True if input.length() >= 1, false elsewhere
     */
    @Override
    public boolean checkPos(String input, PlaySpace playSpace, StringBuilder stringBuilder) {
        return (input.length() >= 1);
    }

    /**
     * This method return the input
     * @param input String passed from user
     * @return The same input that was passed
     */
    @Override
    public String getMessage(String input) {
        return input;
    }

    /**
     * This method just calls the one above
     * @param message String passed from user
     * @return The result of getMessage(String)
     */
    @Override
    public String getMessageGui(String message) {
         return getMessage(message);
    }

    /**
     * This method just calls the method checkPos
     * @param input Same as checkPos(String, PlaySpace, StringBuilder)
     * @param playSpace Same as checkPos(String, PlaySpace, StringBuilder)
     * @param stringBuilder Same as checkPos(String, PlaySpace, StringBuilder)
     * @return true if input.length() >= 1, false elsewhere
     */
    @Override
    public boolean checkPosGui(String input, PlaySpace playSpace, StringBuilder stringBuilder) {
        return checkPos(input,playSpace,stringBuilder);
    }
}
