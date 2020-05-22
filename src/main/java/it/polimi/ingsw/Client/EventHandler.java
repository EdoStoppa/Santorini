package it.polimi.ingsw.Client;

import javafx.event.Event;

import java.util.EventListener;

public interface EventHandler<T extends Event> extends EventListener{
    void update(final int phase);
}
