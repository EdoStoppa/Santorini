package it.polimi.ingsw.Controller.GodController;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Controller.GodController.GodController;
import it.polimi.ingsw.Message.PosMessage;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.Pan;

/**Pan's GodController
 */
public class PanController extends GodController {

    /**
     * Used to handle all the correct calls to the model for a special move
     *
     * @param model      Model of the game
     * @param posMessage Message containing the selected move position
     */
    @Override
    public void handleSpecialMove(Model model, Controller controller, PosMessage posMessage) {
        controller.handleMove(posMessage);

        Pan pan = (Pan)model.getCurrentGod();
        if(pan.checkWin(model)){
            // TRANSITION TO END GAME
            controller.executeWinSequence();
        }
    }

    /**
     * Used to handle all the correct calls to the model to prepare a special move phase
     *
     * @param model Model of the game
     */
    @Override
    public void prepareSpecialMove(Model model, Controller controller) {
        controller.prepareMove();
    }
}
