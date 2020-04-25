package it.polimi.ingsw.View;

import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.PosMessage;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;

public class View extends Observable<PosMessage>  implements Observer<GameMessage>{


    @Override
    public void update(GameMessage message) {

    }
}
