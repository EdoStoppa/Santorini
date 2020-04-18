package it.polimi.ingsw.Controller.GodController;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Message.PosMessage;
import it.polimi.ingsw.Model.Athena;
import it.polimi.ingsw.Model.Model;

public class AthenaController extends GodController {
    /**Used to handle all the correct calls to the model for a special choose constructor
     *
     * @param model Model of the game
     * @param posMessage Message containing the selected move position
     */
    @Override
    public void handleSpecialChooseConstructor(Model model, Controller controller, PosMessage posMessage){
        controller.handleChooseConstructor(posMessage);
    }

    /**Used to handle all the correct calls to the model to prepare a special choose constructor phase
     *
     * @param model Model of the game
     */
    @Override
    public void prepareSpecialChooseConstructor(Model model, Controller controller){
        Athena athena = (Athena)model.getCurrentGod();
        athena.changeCanGoUp(model, true);

        controller.prepareChooseConstructor();
    }

    /**Used to handle all the correct calls to the model for a special move
     *
     * @param model Model of the game
     * @param posMessage Message containing the selected move position
     */
    @Override
    public void handleSpecialMove(Model model, Controller controller, PosMessage posMessage){
        controller.handleMove(posMessage);

        Athena athena = (Athena)model.getCurrentGod();
        if(athena.shouldActivatePower(model)){
            athena.changeCanGoUp(model, false);
        }
    }

    /**Used to handle all the correct calls to the model to prepare a special move phase
     *
     * @param model Model of the game
     */
    @Override
    public void prepareSpecialMove(Model model, Controller controller){
        Athena athena = (Athena)model.getCurrentGod();
        athena.changeCanGoUp(model, true);

        controller.prepareMove();
    }
}
