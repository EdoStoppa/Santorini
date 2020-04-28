package it.polimi.ingsw.Controller.GodController;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Message.PosMessage;
import it.polimi.ingsw.Model.Artemis;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.Position;

import java.util.ArrayList;
import java.util.List;

/**Artemis' GodController
 */
public class ArtemisController extends GodController {

    /**
     * Used to handle all the correct calls to the model for a special move
     *
     * @param model      Model of the game
     * @param posMessage Message containing the selected move position
     */
    @Override
    public void handleSpecialMove(Model model, Controller controller, PosMessage posMessage) {
        if(posMessage.getPosition()!= null){
            controller.handleMove(posMessage);
        }
    }

    /**
     * Used to handle all the correct calls to the model to prepare a special move phase
     *
     * @param model Model of the game
     */
    @Override
    public void prepareSpecialMove(Model model, Controller controller) {
        List<Position> deleteList = new ArrayList<>();
        Artemis artemis = (Artemis)model.getCurrentGod();

        deleteList.add(artemis.getPrevPosConstructor(model));

        if(artemis.cantDoAnother(model, deleteList)){

            // Now we have to change phase and prepare the nextPhase
            model.nextPhase();
            controller.preparePhase();
            return;
        }

        model.createPossibleMovePos(null, deleteList);
    }

}
