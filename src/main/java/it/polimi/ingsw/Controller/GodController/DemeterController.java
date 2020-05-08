package it.polimi.ingsw.Controller.GodController;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Message.PosMessage;
import it.polimi.ingsw.Model.Demeter;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.Position;

import java.util.ArrayList;
import java.util.List;

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
        if(posMessage.getPosition() != null){
            controller.handleBuild(posMessage);
        }
    }

    /**
     * Used to handle all the correct calls to the model to prepare a special build phase
     *
     * @param model Model of the game
     */
    @Override
    public void prepareSpecialBuild(Model model, Controller controller) {
        List<Position> delList = new ArrayList<Position>();
        Demeter demeter = (Demeter)model.getCurrentGod();
        if(demeter.canBuildAgain(model)){
            delList.add(demeter.getLastBuild(model));
            model.createPossibleBuildPos(null, delList);
        } else {
            // Now we have to change phase and prepare the nextPhase
            model.nextPhase();
            controller.preparePhase();
        }
    }
}
