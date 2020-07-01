package it.polimi.ingsw.Controller.MiniController;

import it.polimi.ingsw.Client.GraphicElements.AlertBox;
import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Message.HelpMessage;
import it.polimi.ingsw.Model.Position;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

/**
 * This MiniController checks if the constructor chosen by the player can use the spacial power of his god
 */
public class MoreCheckMiniController implements MiniController, Serializable {
    private static final long serialVersionUID = 1L;
    List<Position> toCheck;

    public MoreCheckMiniController(List<Position> toCheck){
        this.toCheck = toCheck;
    }

    /**
     * This method parses the input. If the constructor can't use his special power, the player
     * is asked if he wants to pursue with his choice.
     * @param input should be 3 characters long
     * @param playSpace The playspace where to search for the position
     * @param stringBuilder a string placeholder to return the correct message to the player
     * @return true if the constructor can use his special power or if the constructor can't use
     * his special power, but the player wants to pursue with his choice, false elsewhere
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

    /**
     * @param input string passed from user
     * @return "skipPhase" + input if the return of the moreCheck method is true, elsewhere
     * "standard" + input
     */
    @Override
    public String getMessage(String input) {
        String[] p = input.split(",");

        if(moreCheck(new Position(Integer.parseInt(p[0]), Integer.parseInt(p[1])), toCheck))
            return "skipPhase " + input;

        return "standard " + input;
    }

    /**
     *
     * @param message string passed from user
     * @return the return of getMessage(String)
     */
    @Override
    public String getMessageGui(String message) {
        return getMessage(message);
    }

    /**
     *
     * @param input a string passed from user
     * @param playSpace The playspace where to search for the position
     * @param stringBuilder a string placeholder to return the correct message to the player
     * @return true if the position parsed from input belongs to the tileToShow of the playspace,
     * false elsewhere
     */
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

    /**
     * Helper method that checks if the position belongs to the checkList
     * @param pos position we want to check
     * @param checkList list of position where to search pos
     * @return true if pos belongs to checkList, false elsewhere
     */
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
