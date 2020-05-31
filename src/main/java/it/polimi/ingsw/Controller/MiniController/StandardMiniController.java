package it.polimi.ingsw.Controller.MiniController;

import it.polimi.ingsw.Client.PlaySpace;

import java.io.Serializable;

public class StandardMiniController implements MiniController, Serializable {
    private static final long serialVersionUID = 1L;
    @Override
    public boolean checkPos(String input, PlaySpace playSpace, StringBuilder stringBuilder) {
        try{
            if(input.length() != 3)
                return false;

            String[] rowAndCol = input.split(",");
            int row = Integer.parseInt(rowAndCol[0]);
            int col = Integer.parseInt(rowAndCol[1]);


            if(0<=row && row<=4 && 0<=col && col<=4){
                return playSpace.printTileToShow(row, col);
            }

            else
                return false;

        } catch(Exception e){
            return false;
        }
    }

    @Override
    public String getMessage(String input) {
        return "standard "+input;
    }
}
