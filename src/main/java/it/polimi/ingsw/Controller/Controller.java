package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Controller.GodController.GodController;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.*;
import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Model.PossiblePhases;
import it.polimi.ingsw.Observer.Observer;

import java.util.List;

// -----------------------------------------------------------------------------------

//                  ADD NOTIFY MID NEXT PHASE

// -----------------------------------------------------------------------------------

public class Controller implements Observer<PosMessage> {

    Model model;

    public Controller(Model model){
        this.model = model;
    }

    /**
     * This method is used to direct (depending on the current phase) to the correct
     * sub-method to manage correctly the next action
     *<p>
     *     If the phase is a special one, the method calls the appropriate <em>GodController</em>
     *     and launch the special method
     *</p>
     *
     * @param message the message arrived from a view
     */
    public void handleAction(PosMessage message){
        PossiblePhases phase = model.getCurrentPhase();
        switch (phase) {
            case CHOOSE_CONSTRUCTOR:
                handleChooseConstructor(message);
            case SPECIAL_CHOOSE_CONSTRUCTOR:
                GodController chooseGodController = model.getCurrentPlayerController();
                chooseGodController.handleSpecialChooseConstructor(model, this, message);
                break;
            case MOVE:
                handleMove(message);
                break;
            case SPECIAL_MOVE:
                GodController moveGodController = model.getCurrentPlayerController();
                moveGodController.handleSpecialMove(model, this, message);
                break;
            case BUILD:
                handleBuild(message);
                break;
            case SPECIAL_BUILD:
                GodController buildGodController = model.getCurrentPlayerController();
                buildGodController.handleSpecialBuild(model, this, message);
                break;
            default:
                System.out.println("You broke the game! GG WP :D");
                break;
        }
    }

    public void handleChooseConstructor(PosMessage message){
        model.performChooseConstructor(message.getPosition());
    }

    public void handleMove(PosMessage message){
        model.performMove(message.getPosition());
        if(model.checkWin()){
            // TRANSITION TO END GAME
            executeWinSequence();
        }
    }

    public void handleBuild(PosMessage message){
        model.performBuild(message.getPosition());
    }

    /**
     * This method is used to direct (depending on the current phase) to the correct
     * sub-method to manage correctly the preparation of the next phase
     *<p>
     *     If the phase is a special one, the method calls the appropriate <em>GodController</em>
     *     and launch the special method
     *</p>
     */
    public void preparePhase(){
        PossiblePhases phase = model.getCurrentPhase();
        switch (phase) {
            case CHOOSE_CONSTRUCTOR:
                prepareChooseConstructor();
                break;
            case SPECIAL_CHOOSE_CONSTRUCTOR:
                GodController chooseGodController = model.getCurrentPlayerController();
                chooseGodController.prepareSpecialChooseConstructor(model, this);
            case MOVE:
                prepareMove();
                break;
            case SPECIAL_MOVE:
                GodController moveGodController = model.getCurrentPlayerController();
                moveGodController.prepareSpecialMove(model, this);
                break;
            case BUILD:
                prepareBuild();
                break;
            case SPECIAL_BUILD:
                GodController buildGodController = model.getCurrentPlayerController();
                buildGodController.prepareSpecialBuild(model, this);
                break;
            default:
                System.out.println("You broke the game! GG WP :D");
        }
    }

    public void prepareChooseConstructor(){
        if(model.isLastStanding()){
            executeWinSequence();
            return;
        }

        model.changeActiveConstructors();
        if(model.isLosing()){
            // TRANSITION TO LOSE SEQUENCE
            executeLoseSequence();
            return;
         }
        model.createPossibleConstructorPos();
    }

    public void prepareMove(){
        model.createPossibleMovePos(null, null);
    }

    public void prepareBuild(){
        model.createPossibleBuildPos(null, null);
    }

    /**
     * This method launches the lose sequence, where the current player is removed after the change
     * of turn
     */
    public void executeLoseSequence(){
        model.destroyRemainingPhases();
        // SEND TO VIEW THAT THIS PLAYER HAS LOST IN THIS LINE <-

        // String toDeleteId = model.getCurrentPlayerId();
        model.nextPhase();
        //model.removePlayer(toDeleteId);
        preparePhase();
    }

    /**
     * This method launches the win sequence, where is declared a winner and the game simply end
     */
    public void executeWinSequence(){
        // ALERT EVERYONE THAT SOMEONE HAS WON
    }

    /**
     * When a new message arrives the method check if it's from the current player, if not
     * simply discard the message, if it's the player turn instead launch the correct sequence of action
     *
     * @param message the message arrived from a view
     */
    @Override
    public void update(PosMessage message) {
        if(!model.isPlayerTurn(message.getIdPlayer())){
            // DO SOMETHING WITH VIEW
            return;
        }

        handleAction((PosMessage)message);
        model.nextPhase();
        preparePhase();
    }
}
