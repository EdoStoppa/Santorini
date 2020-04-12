package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Controller.GodController.GodController;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.*;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Model.PossiblePhases;
import it.polimi.ingsw.Observer.Observer;

import java.util.List;

// -----------------------------------------------------------------------------------

//                  ADD NOTIFY MID NEXT PHASE + CONTROLLER FROM DEMETER

// -----------------------------------------------------------------------------------

public class Controller implements Observer<FromClientMessage> {

    Model model;

    public Controller(Model model){
        this.model = model;
    }

    private void handleAction(PosMessage message){
        PossiblePhases phase = model.getCurrentPhase();
        switch (phase) {
            case CHOOSE_CONSTRUCTOR: case SPECIAL_CHOOSE_CONSTRUCTOR:
                handleChooseConstructor(message);
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

    private void preparePhase(){
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

        model.deactivateConstructorIfNeeded();
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

    public void executeLoseSequence(){
        model.destroyRemainingPhases();
        // SEND TO VIEW THAT THIS PLAYER HAS LOST IN THIS LINE <-

        //String toDeleteId = model.getCurrentPlayerId();
        model.nextPhase();
        //model.removePlayer(toDeleteId);
        preparePhase();
    }

    public void executeWinSequence(){
        // ALERT EVERYONE THAT SOMEONE HAS WON
    }

    @Override
    public void update(FromClientMessage message) {
        if(!model.isPlayerTurn(message.getPlayer())){
            // DO SOMETHING WITH VIEW
            return;
        }

        if(message instanceof PosMessage){
            List<Position> listPos = model.getTileToShow();
            Position messagePos = ((PosMessage) message).getPosition();

            for(Position p : listPos){
                if(p.equals(messagePos)){
                    handleAction((PosMessage)message);
                    model.nextPhase();
                    preparePhase();
                    return;
                }
            }
            //If the method did not return before it means that the position is wrong, so do
            //something with view
        }

        if (message instanceof SpecialActionMessage){
            // UNDERSTAND WHICH SPECIAL ACTION IS AND DO SOMETHING
            return;
        }

        System.out.println("Something went wrong, GameMessage should be a FromClientMessage... :(");
    }
}
