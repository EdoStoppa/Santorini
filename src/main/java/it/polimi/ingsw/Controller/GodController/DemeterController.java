package it.polimi.ingsw.Controller.GodController;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Message.PosMessage;
import it.polimi.ingsw.Model.Model;

/**Demeter's GodController
 */
public class DemeterController extends GodController {
    /**
     * Used to handle all the correct calls to the model for a special move
     *
     * @param model      Model of the game
     * @param posMessage Message containing the selected move position
     */
    @Override
    public void handleSpecialBuild(Model model, Controller controller, PosMessage posMessage) {
        controller.handleBuild(posMessage);
    }

    /**
     * Used to handle all the correct calls to the model to prepare a special build phase
     *
     * @param model Model of the game
     */
    @Override
    public void prepareSpecialBuild(Model model, Controller controller) {
        // DAMN I NEED TO ADD LAST BUILD IN CONSTRUCTOR AND MODIFY ALL RELATED THINGS
    }
}
