package it.polimi.ingsw.Controller.MiniController;

import it.polimi.ingsw.Client.PlaySpace;

import java.io.Serializable;

/**
 * This MiniController checks the parsing of inputs during the initialization, when the
 *player choose which god he wants to use.
 */
public class ChosenMiniController implements MiniController, Serializable {
    private static final long serialVersionUID = 1L;
    int size;
    public ChosenMiniController(int size){
        this.size = size;
    }

    /**
     * This method actually parses the input
     * @param input should be a string with 1 of length, representing an integer
     * @param playSpace
     * @param stringBuilder
     * @return true if the input is valid, false elsewhere
     */
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

    /**
     * This method returns the input
     * @param input
     * @return
     */
    @Override
    public String getMessage(String input) {
        return input;
    }

    /**
     * This method just calls the method above
     * @param message
     * @return
     */
    @Override
    public String getMessageGui(String message) {
       return getMessage(message);
    }

    /**
     * This method just call the method checkPos above
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
