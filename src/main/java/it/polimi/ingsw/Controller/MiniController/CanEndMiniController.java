package it.polimi.ingsw.Controller.MiniController;

import it.polimi.ingsw.Client.PlaySpace;

import java.io.Serializable;

/**
 * This MiniController is used when the user can choose to end his phase.
 */
public class CanEndMiniController implements MiniController, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Checks that the input passed is correct.
     * @param input
     * @param playSpace
     * @param stringBuilder
     * @return true if the input = "end" or if it represents a position which belongs to the tileToShow
     * of the playspace, false if it doesn't belong or if its length() is != 3
     */
    @Override
    public boolean checkPos(String input, PlaySpace playSpace, StringBuilder stringBuilder) {
        try{
            if(input.length() != 3)
                return false;

            if(input.toLowerCase().equals("end")){
                return true;
            }

            String[] rowAndCol = input.split(",");
            int row = Integer.parseInt(rowAndCol[0]);
            int col = Integer.parseInt(rowAndCol[1]);

            if(0<=row && row<=4 && 0<=col && col<=4)
                return playSpace.isTileToShow(row, col);
            else
                return false;

        } catch(Exception e){
            return false;
        }
    }

    /**
     * Check if the input equals to "end"
     * @param input
     * @return "end -1,-1 if the input equals to "end", "standard" + input elsewhere.
     */
    @Override
    public String getMessage(String input) {
        if(input.toLowerCase().equals("end"))
            return input.toLowerCase() + " -1,-1";

        return "standard " + input;
    }

    /**
     * This method just calls the one above
     * @param message
     * @return
     */
    @Override
    public String getMessageGui(String message) {
        return  getMessage(message);
    }

    /**
     * This method just calls checkPos above.
     * @param input
     * @param playSpace
     * @param stringBuilder
     * @return
     */
    @Override
    public boolean checkPosGui(String input, PlaySpace playSpace, StringBuilder stringBuilder) {
        return checkPos(input,playSpace,stringBuilder);
    }
}
