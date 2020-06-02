package it.polimi.ingsw.Controller.MiniController;

import it.polimi.ingsw.Client.PlaySpace;

import java.io.Serializable;

public class ChosenMiniController implements MiniController, Serializable {
    private static final long serialVersionUID = 1L;
    int size;
    public ChosenMiniController(int size){
        this.size = size;
    }

    @Override
    public boolean checkPos(String input, PlaySpace playSpace, StringBuilder stringBuilder) {
        try{
            if(input.length() != 1)
                return false;

            int nGod;
            try{
                nGod = Integer.parseInt(input);
            }catch(Exception e){
                return false;
            }


            if(nGod>=0 && nGod<size)
                return true;
            else{
                build(stringBuilder);
                return false;
            }

        } catch(Exception e){
            stringBuilder.delete(0, 100);
            stringBuilder.append("Your choice is invalid, please input a correct number");
            return false;
        }
    }

    private void build(StringBuilder s){
        s.delete(0, 100);
        s.append("Your choice is invalid, please input a correct number");
    }
    @Override
    public String getMessage(String input) {
        return input;
    }

    @Override
    public String getMessageGui(String message) {
       return getMessage(message);
    }

    @Override
    public boolean checkPosGui(String input, PlaySpace playSpace, StringBuilder stringBuilder) {
        return checkPos(input,playSpace,stringBuilder);
    }
}
