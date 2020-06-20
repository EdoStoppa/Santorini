package it.polimi.ingsw.Controller.MiniController;

import it.polimi.ingsw.Client.GraphicElements.AlertBox;
import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Message.HelpMessage;
import it.polimi.ingsw.Model.Position;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

public class MoreCheckMiniController implements MiniController, Serializable {
    private static final long serialVersionUID = 1L;
    List<Position> toCheck;

    public MoreCheckMiniController(List<Position> toCheck){
        this.toCheck = toCheck;
    }

    @Override
    public boolean checkPos(String input, PlaySpace playSpace, StringBuilder stringBuilder) {
        try{
            if(input.length() != 3)
                return false;

            String[] rowAndCol = input.split(",");
            int row = Integer.parseInt(rowAndCol[0]);
            int col = Integer.parseInt(rowAndCol[1]);

            if(0<=row && row<=4 && 0<=col && col<=4){
                if(playSpace.isTileToShow(row, col)){
                    if(moreCheck(new Position(row, col), toCheck)){
                        Scanner in = new Scanner(System.in);
                        System.out.println("If you choose this constructor you won't be able to use your special power, do you want to continue? "+ HelpMessage.yesOrNo);
                        while(true){
                            String ans = in.nextLine();
                            ans = ans.toLowerCase();
                            if(ans.equals("y")){
                                return true;
                            }

                            if(ans.equals("n")){
                                stringBuilder.delete(0, 200);
                                stringBuilder.append("Please, choose another constructor to continue");
                                return false;
                            }

                            System.out.println("Invalid choice, please try again");
                        }
                    } else {
                        return true;
                    }

                } else {
                    return false;
                }
            } else
                return false;

        } catch(Exception e){
            return false;
        }
    }

    @Override
    public String getMessage(String input) {
        String[] p = input.split(",");

        if(moreCheck(new Position(Integer.parseInt(p[0]), Integer.parseInt(p[1])), toCheck))
            return "skipPhase " + input;

        return "standard " + input;
    }

    @Override
    public String getMessageGui(String message) {
        return getMessage(message);
    }

    @Override
    public boolean checkPosGui(String input, PlaySpace playSpace, StringBuilder stringBuilder) {
        try{
            if(input.length() != 3)
                return false;

            String[] rowAndCol = input.split(",");
            int row = Integer.parseInt(rowAndCol[0]);
            int col = Integer.parseInt(rowAndCol[1]);

            if(0<=row && row<=4 && 0<=col && col<=4){
                return playSpace.isTileToShow(row, col);
            } else
                return false;

        } catch(Exception e){
            return false;
        }
    }

    boolean moreCheck(Position pos, List<Position> checkList){
        if (checkList != null){
            for(Position p : checkList){
                if(pos.equals(p)){
                    return true;
                }
            }
        }

        return false;
    }
}
