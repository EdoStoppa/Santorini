package it.polimi.ingsw.Controller.GodController;

import it.polimi.ingsw.Message.PosMessage;
import it.polimi.ingsw.Model.Model;

/**Interface used to implement a Strategy Pattern regarding the special effect of the God card
 */
public interface GodController {

    /**Used to handle all the correct calls to the model for a special move
     *
     * @param model Model of the game
     * @param posMessage Message containing the selected move position
     */
    public void handleSpecialMove(Model model, PosMessage posMessage);

    /**Used to handle all the correct calls to the model for a special move
     *
     * @param model Model of the game
     * @param posMessage Message containing the selected move position
     */
    public void handleSpecialBuild(Model model, PosMessage posMessage);

    /**Used to handle all the correct calls to the model to prepare a special move phase
     *
     * @param model Model of the game
     */
    public void prepareSpecialMove(Model model);

    /**Used to handle all the correct calls to the model to prepare a special build phase
     *
     * @param model Model of the game
     */
    public void prepareSpecialBuild(Model model);
}
