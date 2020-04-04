package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.*;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Observer.Observer;

public class Controller implements Observer<GameMessage> {

    Model model;

    private void handleAction(GameMessage message){}

    private void preparePhase(){}

    @Override
    public void update(GameMessage message) {
        if(message instanceof PosMessage){
            handleAction(message);
            model.nextPhase();
            preparePhase();
        } else {}

    }
}
