package it.polimi.ingsw.Controller.MiniController;

import it.polimi.ingsw.Client.PlaySpace;

import java.io.Serializable;

/**
 * This is ServerMoveMiniController
 */
public class ServerMoveMiniController implements MiniController, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * This method parses the input
     * @param input should be 3 characters long
     * @param playSpace
     * @param stringBuilder
     * @return true if the position parsed from input belongs in the constructorMatrix of the playspace
     */
    @Override
    public boolean checkPos(String input, PlaySpace playSpace, StringBuilder stringBuilder) {
        try{
            if(input.length() != 3)
                return false;

            String[] rowAndCol = input.split(",");
            int row = Integer.parseInt(rowAndCol[0]);
            int col = Integer.parseInt(rowAndCol[1]);

            int[][] m = playSpace.getConstructorMatrix();

            if(0<=row && row<=4 && 0<=col && col<=4){
                return (m[row][col] == 0);
            } else
                return false;

        } catch(Exception e){
            return false;
        }
    }

    /**
     * @param input
     * @return the input
     */
    @Override
    public String getMessage(String input) {
        return input;
    }

    /**
     * This method just calls getMessage above
     * @param message
     * @return
     */
    @Override
    public String getMessageGui(String message) {
        return getMessage(message);
    }

    /**
     * This method just calls checkPos above
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
