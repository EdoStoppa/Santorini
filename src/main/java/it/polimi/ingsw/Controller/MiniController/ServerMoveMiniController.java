package it.polimi.ingsw.Controller.MiniController;

import it.polimi.ingsw.Client.PlaySpace;

public class ServerMoveMiniController implements MiniController{
    @Override
    public boolean checkPos(String input, PlaySpace playSpace) {
        try{
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

    @Override
    public String getMessage(String input) {
        return null;
    }
}
