package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Controller.GodController.GodController;
import it.polimi.ingsw.Message.*;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.PossiblePhases;
import it.polimi.ingsw.Observer.Observer;

/**
 * This class is the controller of the application
 */
public class Controller implements Observer<PosMessage> {

    private Model model;
    private boolean shouldContinue;

    public Controller(Model model) {
        this.model = model;
        this.shouldContinue = true;
    }

    /**
     * This method is used to direct (depending on the current phase) to the correct
     * sub-method to manage correctly the next action
     * <p>
     * If the phase is a special one, the method calls the appropriate <em>GodController</em>
     * and launch the special method
     * </p>
     *
     * @param message the message arrived from a view
     */
    public void handleAction(PosMessage message) {
        System.out.println("Started handleAction");
        PossiblePhases phase = model.getCurrentPhase();
        switch (phase) {
            case CHOOSE_CONSTRUCTOR:
                handleChooseConstructor(message);
                break;
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

    /**
     * This method places the current constructor in the position passed calling the method performChooseConstructor in model
     * @param message PosMessage with the position where to place the currentConstructor
     */
    public void handleChooseConstructor(PosMessage message) {
        model.performChooseConstructor(message.getPosition());
    }

    /**
     * This method calls the performMove method in model. It also checks if the game ends, when the currentConstructor
     * goes up from the second level to the third.
     * @param message
     */
    public void handleMove(PosMessage message) {
        model.performMove(message.getPosition());
        if (model.checkWin()) {
            // TRANSITION TO END GAME
            executeWinSequence();
        }
    }

    /**
     * This method calls the performBuild in model.
     * @param message PosMessage with the position where to build
     */
    public void handleBuild(PosMessage message) {
        model.performBuild(message.getPosition());
    }

    /**
     * This method is used to direct (depending on the current phase) to the correct
     * sub-method to manage correctly the preparation of the next phase
     * <p>
     * If the phase is a special one, the method calls the appropriate <em>GodController</em>
     * and launch the special method
     * </p>
     */
    public void preparePhase() {
        System.out.println("Started PreparePhase");
        PossiblePhases phase = model.getCurrentPhase();
        switch (phase) {
            case CHOOSE_CONSTRUCTOR:
                prepareChooseConstructor();
                break;
            case SPECIAL_CHOOSE_CONSTRUCTOR:
                GodController chooseGodController = model.getCurrentPlayerController();
                chooseGodController.prepareSpecialChooseConstructor(model, this);
                break;
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

    /**
     * This method prepares the chooseConstructorPhase. In particular, it checks if the current Player is the last standing
     * and if he is losing. If none of the conditions mentioned before are TRUE, it creates the matrix with all possible
     * constructors.
     */
    public void prepareChooseConstructor() {
        if (model.isLastStanding()) {
            executeWinSequence();
            return;
        }

        model.changeActiveConstructors();
        if (model.isLosing()) {
            // TRANSITION TO LOSE SEQUENCE
            executeLoseSequence();
            return;
        }
        model.createPossibleConstructorPos();
    }

    /**
     * This method creates the matrix with all the tiles where the currentConstructor can move.
     */
    public void prepareMove() {
        model.createPossibleMovePos(null, null);
    }

    /**
     * This method creates the matrix with all the tiles where the currentConstructor can build.
     */
    public void prepareBuild() {
        model.createPossibleBuildPos(null, null);
    }

    /**
     * This method launches the lose sequence, where the current player is removed after the change
     * of turn
     */
    public void executeLoseSequence() {
        model.destroyRemainingPhases();
        // SEND TO VIEW THAT THIS PLAYER HAS LOST IN THIS LINE <-
        System.out.println("Lose");

        String toDeleteId = model.getCurrentPlayerId();
        model.nextPhase();
        model.removePlayer(toDeleteId);
        preparePhase();
    }

    /**
     * This method launches the win sequence, where is declared a winner and the game simply end
     */
    public void executeWinSequence() {
        model.endGame();
        shouldContinue = false;
        System.out.println("Win");
    }

    /**
     * When a new message arrives the method check if it's from the current player, if not
     * simply discard the message, if it's the player turn instead launch the correct sequence of action
     *
     * @param message the message arrived from a view
     */
    @Override
    public void update(PosMessage message) {
        if (!model.isPlayerTurn(message.getIdPlayer())) {
            System.out.println("Serious problem with input from Client, this message shouldn't have arrived...");
            return;
        }

        handleAction((PosMessage) message);
        if(shouldContinue){
            model.nextPhase();
            preparePhase();
        }
    }

/*
------------------------------------------------------------------------------------------------------------------------
                                            FOR TESTING PURPOSE
------------------------------------------------------------------------------------------------------------------------
 */

    protected Model getModel()  {
        return this.model;
    }

}

