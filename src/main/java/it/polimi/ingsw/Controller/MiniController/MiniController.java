package it.polimi.ingsw.Controller.MiniController;

import it.polimi.ingsw.Client.PlaySpace;

/**
 * This interface creates every class that allows to parse the input of the user directly on the client,
 * without using the server
 */
public interface MiniController {

    /**
     * This method parses the input
     * @param input a string passed from user
     * @param playSpace the playspace needed to check the position
     * @param stringBuilder a string placeholder to return the correct message to the player
     * @return true or false whether certain conditions are satisfied or not
     */
    public boolean checkPos(String input, PlaySpace playSpace, StringBuilder stringBuilder);

    /**
     * @param input string passed from user
     * @return the input
     */
    public String getMessage(String input);

    /**
     * Same thing as getMessage above, for the GUI
     * @param message string passed from user
     * @return the message passed as parameter
     */
    public String getMessageGui(String message);

    /**
     * Same thing for checkPos above, for GUI
     * @param input a string passed from user
     * @param playSpace the playspace needed to check the position (not always needed)
     * @param stringBuilder a string placeholder to return the correct message to the player
     * @return true or false whether certain conditions are satisfied or not
     */
    public boolean checkPosGui(String input, PlaySpace playSpace, StringBuilder stringBuilder);
}
