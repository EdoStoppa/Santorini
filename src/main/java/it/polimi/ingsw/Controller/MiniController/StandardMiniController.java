package it.polimi.ingsw.Controller.MiniController;

import it.polimi.ingsw.Client.PlaySpace;

import java.io.Serializable;

/**
 * This MiniController is used for every standard action
 */
public class StandardMiniController implements MiniController, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * This method parses the input.
     * @param input should be 3 characters long
     * @param playSpace the playspace where to search for the position
     * @param stringBuilder a string placeholder to return the correct message to the player
     * @return true if the position parsed belongs to the tileToShow list of the playspace,
     * false elsewhere.
     */
    @Override
    public boolean checkPos(String input, PlaySpace playSpace, StringBuilder stringBuilder) {
        try{
            if(input.length() != 3)
                return false;

            String[] rowAndCol = input.split(",");
            int row = Integer.parseInt(rowAndCol[0]);
            int col = Integer.parseInt(rowAndCol[1]);


            if(0<=row && row<=4 && 0<=col && col<=4){
                return playSpace.isTileToShow(row, col);
            }

            else
                return false;

        } catch(Exception e){
            return false;
        }
    }

    /**
     * @param input a string passed from user
     * @return "standard" + input
     */
    @Override
    public String getMessage(String input) {
        return "standard "+input;
    }

    /**
     * This method just calls getMessage above
     * @param message a string passed from user
     * @return same as getMessage(String)
     */
    @Override
    public String getMessageGui(String message) {
        return getMessage(message);
    }

    /**
     * This method just calls checkPos above
     * @param input a string passed from user
     * @param playSpace the playspace where to search for the position
     * @param stringBuilder a string placeholder to return the correct message to the player
     * @return same as checkPos(..)
     */
    @Override
    public boolean checkPosGui(String input, PlaySpace playSpace, StringBuilder stringBuilder) {
        return checkPos(input,playSpace,stringBuilder);
    }
}
