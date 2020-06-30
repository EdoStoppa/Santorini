package it.polimi.ingsw.Controller.MiniController;

import it.polimi.ingsw.Client.PlaySpace;

import java.io.Serializable;
import java.util.List;

/**
 * This MiniController selects the first player to start by parsing the input
 */
public class OrderMiniController implements MiniController, Serializable {
    private static final long serialVersionUID = 1L;
    List<String> pList;
    public OrderMiniController(List<String> pList){
        this.pList = pList;
    }

    /**
     * This method parses the input
     * @param input should be equal to a idPlayer in Plist
     * @param playSpace
     * @param stringBuilder
     * @return true if the input is correct, false elsewhere.
     */
    @Override
    public boolean checkPos(String input, PlaySpace playSpace, StringBuilder stringBuilder) {
        for(String s : pList){
            if(s.equals(input)){
                return true;
            }
        }
        return false;
    }

    /**
     * This method return the input
     * @param input
     * @return
     */
    @Override
    public String getMessage(String input) {
        return input;
    }

    /**
     * This method just call getMessage above
     * @param message
     * @return
     */
    @Override
    public String getMessageGui(String message) {
        return getMessage(message);
    }

    /**
     * This method just calls checkPos above
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
