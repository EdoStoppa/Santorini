package it.polimi.ingsw.Controller.GodController;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Controller.GodController.GodController;
import it.polimi.ingsw.Message.PosMessage;
import it.polimi.ingsw.Model.Apollo;
import it.polimi.ingsw.Model.Minotaur;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.Position;

import java.util.List;

/**Minotaur's GodController
 */
public class MinotaurController extends GodController {
    /**Used to handle all the correct calls to the model for a special choose constructor
     *
     * @param model Model of the game
     * @param posMessage Message containing the selected move position
     */
    @Override
    public void handleSpecialChooseConstructor(Model model, Controller controller, PosMessage posMessage){
        controller.handleChooseConstructor(posMessage);
    }

    /**
     * Used to handle all the correct calls to the model to prepare a special choose constructor phase
     *
     * @param model Model of the game
     */
    @Override
    public void prepareSpecialChooseConstructor(Model model, Controller controller){
        if(model.isLastStanding()){
            controller.executeWinSequence();
            return;
        }

        Minotaur minotaur = (Minotaur) model.getCurrentGod();
        minotaur.changeActiveConstructors(model);
        if(model.isLosing()){
            // TRANSITION TO LOSE SEQUENCE
            controller.executeLoseSequence();
            return;
        }
        model.createPossibleConstructorPos();
    }

    /**
     * Used to handle all the correct calls to the model for a special move
     *
     * @param model      Model of the game
     * @param posMessage Message containing the selected move position
     */
    @Override
    public void handleSpecialMove(Model model, Controller controller, PosMessage posMessage) {
        Position p = posMessage.getPosition();

        if(model.isOccupied(p)){
            model.performPush(p);
            if(model.checkWin()){
                // TRANSITION TO END GAME
                controller.executeWinSequence();
            }
        } else {
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
        Minotaur minotaur = (Minotaur) model.getCurrentGod();
        List<Position> addList = minotaur.getMoveAddList(model);
        model.createPossibleMovePos(addList, null);
    }
}
