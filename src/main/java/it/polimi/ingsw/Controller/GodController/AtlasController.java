package it.polimi.ingsw.Controller.GodController;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.PosMessage;
import it.polimi.ingsw.Model.Atlas;
import it.polimi.ingsw.Model.Model;

/**Atlas' GodController
 */
public class AtlasController extends GodController {
    /**
     * Used to handle all the correct calls to the model for a special move
     *
     * @param model      Model of the game
     * @param posMessage Message containing the selected move position
     */
    @Override
    public void handleSpecialBuild(Model model, Controller controller, PosMessage posMessage) {
        if(!posMessage.getMessage().equals("dome")){
            controller.handleBuild(posMessage);
        } else {
            Atlas atlas = (Atlas)model.getCurrentGod();
            atlas.forceDome(model, posMessage.getPosition());
        }
    }

    /**
     * Used to handle all the correct calls to the model to prepare a special build phase
     *
     * @param model Model of the game
     */
    @Override
    public void prepareSpecialBuild(Model model, Controller controller) {
        Atlas atlas = (Atlas)model.getCurrentGod();
        atlas.prepareBuild(model);
    }
}
