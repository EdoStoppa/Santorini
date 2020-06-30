package it.polimi.ingsw.Controller.MiniController;

import it.polimi.ingsw.Client.PlaySpace;

/**
 * This interface creates every class that allows to parse the input of the user directly on the client,
 * without using the server
 */
public interface MiniController {

    /**
     * This method parses the input
     * @param input
     * @param playSpace
     * @param stringBuilder
     * @return true or false whether certain conditions are satisfied or not
     */
    public boolean checkPos(String input, PlaySpace playSpace, StringBuilder stringBuilder);

    /**
     * @param input
     * @return the input
     */
    public String getMessage(String input);

    /**
     * Same thing as getMessage above, for the GUI
     * @param message
     * @return
     */
    public String getMessageGui(String message);

    /**
     * Same thing for checkPos above, for GUI
     * @param input
     * @param playSpace
     * @param stringBuilder
     * @return
     */
    public boolean checkPosGui(String input, PlaySpace playSpace, StringBuilder stringBuilder);
}
