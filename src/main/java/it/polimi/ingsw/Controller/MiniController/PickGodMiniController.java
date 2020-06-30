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
     * @param input should be 3 characters long
     * @param playSpace
     * @param stringBuilder
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

    private void stringBuilder(int numPlayer, StringBuilder stringBuilder){
        stringBuilder.delete(0, 100);
        stringBuilder.append("Your choice is invalid, please input ");
        stringBuilder.append(numPlayer);
        stringBuilder.append(" numbers all separated by a \",\" with no spaces");
    }

    /**
     *
     * @param input
     * @return the input
     */
    @Override
    public String getMessage(String input) {
        return input;
    }

    /**
     * This method just calls getMessage above
     * @param message
     * @return
     */
    @Override
    public String getMessageGui(String message) {
        return getMessage(message);
    }

    /**
     * This message just calls checkPos above
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
