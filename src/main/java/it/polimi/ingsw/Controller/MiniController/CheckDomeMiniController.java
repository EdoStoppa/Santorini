package it.polimi.ingsw.Controller.MiniController;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Message.HelpMessage;

import java.util.Scanner;

public class CheckDomeMiniController implements MiniController{
    @Override
    public boolean checkPos(String input, PlaySpace playSpace, StringBuilder stringBuilder) {
        try{
            if(input.length() != 3)
                return false;

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
}