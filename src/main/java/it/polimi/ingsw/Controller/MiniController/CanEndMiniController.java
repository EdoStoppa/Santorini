package it.polimi.ingsw.Controller.MiniController;

import it.polimi.ingsw.Client.PlaySpace;

import java.io.Serializable;

public class CanEndMiniController implements MiniController, Serializable {
    private static final long serialVersionUID = 1L;
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

    @Override
    public String getMessage(String input) {
        if(input.toLowerCase().equals("end"))
            return input.toLowerCase() + " -1,-1";

        return "standard " + input;
    }

    @Override
    public String getMessageGui(String message) {
        return  getMessage(message);
    }

    @Override
    public boolean checkPosGui(String input, PlaySpace playSpace, StringBuilder stringBuilder) {
        return checkPos(input,playSpace,stringBuilder);
    }
}
