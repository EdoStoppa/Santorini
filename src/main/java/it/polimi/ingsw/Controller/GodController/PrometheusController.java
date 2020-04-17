package it.polimi.ingsw.Controller.GodController;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Controller.GodController.GodController;
import it.polimi.ingsw.Message.PosMessage;
import it.polimi.ingsw.Model.Hephaestus;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Model.Prometheus;

import java.util.List;

/**Prometheus' GodController
 */
public class PrometheusController extends GodController {
    /**Used to handle all the correct calls to the model for a special choose constructor
     *
     * @param model Model of the game
     * @param posMessage Message containing the selected move position
     */
    @Override
    public void handleSpecialChooseConstructor(Model model, Controller controller, PosMessage posMessage){
        controller.handleChooseConstructor(posMessage);

        // if(chosen position cannot perform special build (this is seen from posMessage)){
        //      Prometheus prometheus = model.getCurrentGod()
        //      prometheus.setCorrectPhase(model);
        // }
    }

    /**
     * Used to handle all the correct calls to the model to prepare a special choose constructor phase
     *
     * @param model Model of the game
     */
    @Override
    public void prepareSpecialChooseConstructor(Model model, Controller controller) {
        if(model.isLastStanding()){
            controller.executeWinSequence();
            return;
        }

        model.changeActiveConstructors();
        if(model.isLosing()){
            // TRANSITION TO LOSE SEQUENCE
            controller.executeLoseSequence();
            return;
        }

        Prometheus prometheus = (Prometheus)model.getCurrentGod();
        prometheus.createPossibleConstructorPos(model);
    }

    /**
     * Used to handle all the correct calls to the model for a special move
     *
     * @param model      Model of the game
     * @param posMessage Message containing the selected move position
     */
    @Override
    public void handleSpecialMove(Model model, Controller controller, PosMessage posMessage) {
        controller.handleMove(posMessage);
    }

    /**
     * Used to handle all the correct calls to the model to prepare a special move phase
     *
     * @param model Model of the game
     */
    @Override
    public void prepareSpecialMove(Model model, Controller controller) {
        Prometheus prometheus = (Prometheus) model.getCurrentGod();

        List<Position> wrongPos = prometheus.getWrongPos(model);

        model.createPossibleMovePos(null, wrongPos);
    }

    /**
     * Used to handle all the correct calls to the model for a special move
     *
     * @param model      Model of the game
     * @param posMessage Message containing the selected move position
     */
    @Override
    public void handleSpecialBuild(Model model, Controller controller, PosMessage posMessage) {
        // if it is a null pos it means that the player ended the phase without building, so nothing happens
        if(posMessage.getPosition() != null){
            controller.handleBuild(posMessage);

            Prometheus p = (Prometheus)model.getCurrentGod();
            p.setCanGoUp(false);
        }
    }

    /**
     * Used to handle all the correct calls to the model to prepare a special build phase
     *
     * @param model Model of the game
     */
    @Override
    public void prepareSpecialBuild(Model model, Controller controller) {
        Prometheus prometheus = (Prometheus)model.getCurrentGod();

        List<Position> sameOrDownList = prometheus.sameOrDownLevelCurrent(model);

        if(sameOrDownList.size() == 1){
            model.createPossibleBuildPos(null, sameOrDownList);
        } else {
            controller.prepareBuild();
        }
    }
}
