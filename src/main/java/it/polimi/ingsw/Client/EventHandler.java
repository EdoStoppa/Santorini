package it.polimi.ingsw.Client;

import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.ServerMessage.ServerMessage;
import javafx.event.Event;

import java.util.EventListener;

public interface EventHandler<T extends Event> extends EventListener{
    void update(ServerMessage message);

    void updateConstructor(GameMessage message);

    void updateBuilding(GameMessage message,boolean dome);
}
