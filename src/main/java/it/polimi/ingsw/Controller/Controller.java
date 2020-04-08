package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Controller.GodController.GodController;
import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.*;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Model.PossiblePhases;
import it.polimi.ingsw.Observer.Observer;

import java.util.List;

public class Controller implements Observer<FromClientMessage> {

    Model model;

    private void handleAction(PosMessage message){
        PossiblePhases phase = model.getCurrentPhase();
        switch (phase) {
            case CHOOSE_CONSTRUCTOR:
                handleChooseConstructor(message);
                break;
            case MOVE:
                handleMove(message);
                break;
            case SPECIAL_MOVE:
                // WAIT FOR "getCurrentPlayerController" IMPLEMENTATION IN MODEL

                //GodController godController = model.getCurrentPlayerController();
                //godController.handleSpecialMove(model, message);
                break;
            case BUILD:
                handleBuild(message);
                break;
            case SPECIAL_BUILD:
                // WAIT FOR "getCurrentPlayerController" IMPLEMENTATION IN MODEL

                //GodController godController = model.getCurrentPlayerController();
                //godController.handleSpecialBuild(model, message);
                break;
            default:
                System.out.println("You broke the game! GG WP :D");
        }
    }

    private void handleChooseConstructor(PosMessage message){
        // WAIT FOR "performChooseConstructor" IMPLEMENTATION IN MODEL

        //model.performChooseConstructor(message.getPosition());
    }

    private void handleMove(PosMessage message){
        model.performMove(message.getPosition());
        if(model.checkWin()){
            // TRANSITION TO END GAME
            executeWinSequence();
        }
    }

    private void handleBuild(PosMessage message){
        model.performBuild(message.getPosition());
    }

    private void preparePhase(){
        PossiblePhases phase = model.getCurrentPhase();
        switch (phase) {
            case CHOOSE_CONSTRUCTOR:
                prepareChooseConstructor();
                break;
            case MOVE:
                prepareMove();
                break;
            case SPECIAL_MOVE:
                // WAIT FOR "getCurrentPlayerController" IMPLEMENTATION IN MODEL

                //GodController godController = model.getCurrentPlayerController();
                //godController.prepareSpecialMove(model);
                break;
            case BUILD:
                prepareBuild();
                break;
            case SPECIAL_BUILD:
                // WAIT FOR "getCurrentPlayerController" IMPLEMENTATION IN MODEL

                //GodController godController = model.getCurrentPlayerController();
                //godController.prepareSpecialBuild(model);
                break;
            default:
                System.out.println("You broke the game! GG WP :D");
        }
    }

    private void prepareChooseConstructor(){
        /*if(model.isLastStanding()){
            executeWinSequence();
            return;
        }*/

        // WAIT FOR "deactivateConstructorIfNeeded" IMPLEMENTATION IN MODEL (NEW VERSION WITHOUT A PLAYER INPUT)
        // model.deactivateConstructorIfNeeded();
        // WAIT FOR "isLosing" IMPLEMENTATION IN MODEL (NEW VERSION WITHOUT A PLAYER INPUT)
        /*if(model.isLosing()){
            // TRANSITION TO LOSE SEQUENCE
            executeLoseSequence();
            return;
         }*/

        // WAIT FOR "createPossibleConstructorPos" IMPLEMENTATION IN MODEL

        //model.createPossibleConstructorPos();
    }

    private void prepareMove(){
        // WAIT FOR "createPossibleMovePos" IMPLEMENTATION IN MODEL

        // model.createPossibleMovePos();
    }

    private void prepareBuild(){
        // WAIT FOR "createPossibleBuildPos" IMPLEMENTATION IN MODEL

        // model.createPossibleBuildPos();
    }

    private void executeLoseSequence(){
        /*model.destroyRemainingPhases();
        // SEND TO VIEW THAT THIS PLAYER HAS LOST IN THIS LINE <-

        Player toDelete = model.getCurrentPlayer();
        model.nextPhase();
        model.removePlayer(p);
        model.preparePhase();*/
    }

    private void executeWinSequence(){
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
