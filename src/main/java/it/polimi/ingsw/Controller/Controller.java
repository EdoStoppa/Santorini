package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.*;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.PossiblePhases;
import it.polimi.ingsw.Observer.Observer;

public class Controller implements Observer<FromClientMessage> {

    Model model;

    private void handleAction(PosMessage message){
        PossiblePhases phase = model.getCurrentPhase();
        switch (phase) {
            case MOVE:
                handleMove(message);
            case BUILD:
                handleBuild(message);
            default:
                System.out.println("You broke the game! GG WP :D");
        }
    }

    private void handleMove(PosMessage message){
        model.performMove(message.getPosition());
        if(model.checkWin()){
            // TRANSITION TO END GAME
        }
    }

    private void handleBuild(PosMessage message){
        model.performBuild(message.getPosition());
    }

    private void preparePhase(){}

    @Override
    public void update(FromClientMessage message) {
        if(!model.isPlayerTurn(message.getPlayer())){
            // DO SOMETHING WITH VIEW
            return;
        }
        if(message instanceof PosMessage){
            // CHECK IF POS I A POSSIBLE POS, IF NOT DO SOMETHING WITH VIEW AND RETURN
            handleAction((PosMessage)message);
            model.nextPhase();
            preparePhase();
            return;
        }
        if (message instanceof SpecialActionMessage){
            // UNDERSTAND WHICH SPECIAL ACTION IS AND DO SOMETHING
            return;
        }

        System.out.println("Something went wrong, GameMessage should be a FromClientMessage... :(");
    }
}
