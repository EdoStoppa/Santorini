package it.polimi.ingsw.Controller.MiniController;

import it.polimi.ingsw.Client.ClientGuiApp;
import it.polimi.ingsw.Client.GraphicElements.AlertBox;
import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Message.HelpMessage;

import java.io.Serializable;
import java.util.Scanner;

/**
 * This MiniController is used when a player has ATLAS as god
 */
public class CheckDomeMiniController implements MiniController, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * This method checks if the input is correct and it parses the input.
     * @param input a string passed from user
     * @param playSpace The playspace where to search for the position
     * @param stringBuilder a string placeholder to return the correct message to the player
     * @return true if the position passed as input belongs to the tileToShow of the playspace,
     */
    @Override
    public boolean checkPos(String input, PlaySpace playSpace, StringBuilder stringBuilder) {
        try{
            if(input.length() != 3)
                return false;

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
     * This method "handles" the specialPower of ATLAS: it asks the player if he wants to place a dome
     * instead of a normal construction.
     * @param input a string passed from user
     * @return two different messages, whether the player answered "y" or "n".
     */
    @Override
    public String getMessage(String input) {
        Scanner in = new Scanner(System.in);
        System.out.println("Instead of a normal construction, do you want to place a dome? "+ HelpMessage.yesOrNo);
        while(true){
            String answer = in.nextLine();
            answer = answer.toLowerCase();
            if(answer.equals("y"))
                return "dome " + input;

            if(answer.equals("n"))
                return "standard " + input;

            System.out.println("Your choice in invalid, please type only "+ HelpMessage.yesOrNo);
        }

    }

    /**
     * Same thing as getMessage above
     * @param input string passed from user
     * @return the return of getMessage(String)
     */
    public String getMessageGui(String input){
        Boolean answer=null;
        answer= AlertBox.checkDome("Instead of a normal construction, do you want to place a dome? ");
        if(answer){
            return "dome " + input;
        }else{
            return "standard " + input;
        }

    }

    /**
     * This method calls checkPos above
     * @param input a string passed from user
     * @param playSpace The playspace where to search for the position
     * @param stringBuilder a string placeholder to return the correct message to the player
     * @return the return of checkPos(..)
     */
    @Override
    public boolean checkPosGui(String input, PlaySpace playSpace, StringBuilder stringBuilder) {
        return checkPos(input,playSpace,stringBuilder);
    }

}
