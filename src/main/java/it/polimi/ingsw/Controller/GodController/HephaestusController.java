package it.polimi.ingsw.Controller.GodController;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Controller.GodController.GodController;
import it.polimi.ingsw.Message.PosMessage;
import it.polimi.ingsw.Model.Hephaestus;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.Position;

import java.util.List;

/**Hephaestus' GodController
 */
public class HephaestusController extends GodController {

    /**
     * Used to handle all the correct calls to the model for a special move
     *
     * @param model      Model of the game
     * @param posMessage Message containing the selected move position
     */
    @Override
    public void handleSpecialBuild(Model model, Controller controller, PosMessage posMessage) {

    }

    /**
     * Used to handle all the correct calls to the model to prepare a special build phase
     *
     * @param model Model of the game
     */
    @Override
    public void prepareSpecialBuild(Model model, Controller controller) {
        Hephaestus hephaestus = (Hephaestus)model.getCurrentGod();
        Position lastBuildPos = hephaestus.getLastBuildPos(model);

        if(hephaestus.isMaxedOrDome(model, lastBuildPos)){
            // Tell the player he can't do another build

            // Now we have to change phase and prepare the nextPhase
            model.nextPhase();
            controller.preparePhase();
        } else {
            hephaestus.createPossibleSpecialBuild(model);
        }
    }
}
