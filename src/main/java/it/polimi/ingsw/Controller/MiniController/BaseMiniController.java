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
     * @param input
     * @param playSpace
     * @param stringBuilder
     * @return true if input.length() >= 1, false elsewhere
     */
    @Override
    public boolean checkPos(String input, PlaySpace playSpace, StringBuilder stringBuilder) {
        return (input.length() >= 1);
    }

    /**
     * This method return the input
     * @param input String passed
     * @return
     */
    @Override
    public String getMessage(String input) {
        return input;
    }

    /**
     * This method just calls the one above
     * @param message
     * @return
     */
    @Override
    public String getMessageGui(String message) {
         return getMessage(message);
    }

    /**
     * This method just call the method checkPos
     * @param input
     * @param playSpace
     * @param stringBuilder
     * @return true if input.length() >= 1, false elsewhere
     */
    @Override
    public boolean checkPosGui(String input, PlaySpace playSpace, StringBuilder stringBuilder) {
        return checkPos(input,playSpace,stringBuilder);
    }
}
