package it.polimi.ingsw.Controller.MiniController;

import it.polimi.ingsw.Client.PlaySpace;

public class StandardMiniController implements MiniController{

    @Override
    public boolean checkPos(String input, PlaySpace playSpace) {
        try{
            String[] rowAndCol = input.split(",");
            int row = Integer.parseInt(rowAndCol[0]);
            int col = Integer.parseInt(rowAndCol[1]);

            if(0<=row && row<=4 && 0<=col && col<=4)
                return playSpace.printTileToShow(row, col);
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
