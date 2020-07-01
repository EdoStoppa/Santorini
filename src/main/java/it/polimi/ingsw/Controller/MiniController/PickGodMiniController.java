package it.polimi.ingsw.Controller.MiniController;

import it.polimi.ingsw.Client.PlaySpace;

import java.io.Serializable;

/**
 * This MiniController checks the input during the initialization part where the first player
 * chooses 2/3 gods
 */
public class PickGodMiniController implements MiniController, Serializable {
    private static final long serialVersionUID = 1L;
    int numGod;
    int numPlayer;
    public PickGodMiniController(int numGod, int numPlayer){
        this.numGod = numGod;
        this.numPlayer = numPlayer;
    }

    /**
     * This method parses the input.
     * @param input string passed from user
     * @param playSpace The playspace where to search for the position
     * @param stringBuilder a string placeholder to return the correct message to the player
     * @return true if every god chosen is correct, false elsewhere
     */
    @Override
    public boolean checkPos(String input, PlaySpace playSpace, StringBuilder stringBuilder) {
        if(numPlayer == 2){
            if(input.length() == 3){
                String[] gods = input.split(",");
                if(gods.length == 2 && !gods[0].equals(gods[1])){
                    int pr;
                    for(int i=0; i<2; i++){
                        try{
                            pr = Integer.parseInt(gods[i]);
                        }catch(Exception e){
                            stringBuilder(numPlayer, stringBuilder);
                            return false;
                        }

                        if(pr < 0 || pr >= numGod){
                            stringBuilder.delete(0,100);
                            stringBuilder.append("Please, choose a possible God");
                            return false;
                        }
                    }

                    return true;

                } else{
                    stringBuilder.delete(0,100);
                    stringBuilder.append("Please, choose 2 Gods");
                    return false;
                }
            } else {
                stringBuilder(numPlayer, stringBuilder);
                return false;
            }

        } else {
            if (input.length() == 5) {
                String[] gods = input.split(",");
                if (gods.length == 3 && !gods[0].equals(gods[1]) && !gods[1].equals(gods[2]) && !gods[2].equals(gods[0])) {
                    int pr;
                    for (int i = 0; i < 3; i++) {
                        try {
                            pr = Integer.parseInt(gods[i]);
                        } catch (Exception e) {
                            stringBuilder(numPlayer, stringBuilder);
                            return false;
                        }

                        if (pr < 0 || pr >= numGod) {
                            stringBuilder.delete(0, 100);
                            stringBuilder.append("Please, choose a possible God");
                            return false;
                        }
                    }

                    return true;

                } else {
                    stringBuilder.delete(0, 100);
                    stringBuilder.append("Please, choose 3 Gods");
                    return false;
                }
            } else {
                stringBuilder(numPlayer, stringBuilder);
                return false;
            }
        }
    }

    /**
     * This method creates the correct message
     * @param numPlayer player number
     * @param stringBuilder a string placeholder to return the correct message to the player
     */
    private void stringBuilder(int numPlayer, StringBuilder stringBuilder){
        stringBuilder.delete(0, 100);
        stringBuilder.append("Your choice is invalid, please input ");
        stringBuilder.append(numPlayer);
        stringBuilder.append(" numbers all separated by a \",\" with no spaces");
    }

    /**
     *
     * @param input a string passed from user
     * @return the input
     */
    @Override
    public String getMessage(String input) {
        return input;
    }

    /**
     * This method just calls getMessage above
     * @param message a string passed from user
     * @return same as getMessage(String)
     */
    @Override
    public String getMessageGui(String message) {
        return getMessage(message);
    }

    /**
     * This message just calls checkPos above
     * @param input a string passed
     * @param playSpace The playspace where to search for the position
     * @param stringBuilder a string placeholder to return the correct message to the player
     * @return same as checkPos(..)
     */
    @Override
    public boolean checkPosGui(String input, PlaySpace playSpace, StringBuilder stringBuilder) {
        return checkPos(input,playSpace,stringBuilder);
    }
}
