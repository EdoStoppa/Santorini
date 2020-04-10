package it.polimi.ingsw.Controller.GodController;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Message.PosMessage;
import it.polimi.ingsw.Model.Model;

/**Interface used to implement a Strategy Pattern regarding the special effect of the God card
 */
public abstract class GodController {

    /**Used to handle all the correct calls to the model for a special move
     *
     * @param model Model of the game
     * @param posMessage Message containing the selected move position
     */
    public void handleSpecialMove(Model model, Controller controller, PosMessage posMessage){};

    /**Used to handle all the correct calls to the model for a special move
     *
     * @param model Model of the game
     * @param posMessage Message containing the selected move position
     */
    public void handleSpecialBuild(Model model, Controller controller, PosMessage posMessage){};

    /**Used to handle all the correct calls to the model to prepare a special choose constructor phase
     *
     * @param model Model of the game
     */
    public void prepareSpecialChooseConstructor(Model model, Controller controller){};

    /**Used to handle all the correct calls to the model to prepare a special move phase
     *
     * @param model Model of the game
     */
    public void prepareSpecialMove(Model model, Controller controller){};

    /**Used to handle all the correct calls to the model to prepare a special build phase
     *
     * @param model Model of the game
     */
    public void prepareSpecialBuild(Model model, Controller controller){};
}
