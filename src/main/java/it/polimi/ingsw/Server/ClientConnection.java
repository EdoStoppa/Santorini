package it.polimi.ingsw.Server;


import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Observer.Observer;

import java.util.ArrayList;
import java.util.Map;

public interface ClientConnection{

    void closeConnection();

    void addObserver(Observer<String> observer);

    void asyncSend(Object message);

    String enterNewName(Map <String, ClientConnection> NameOpponent);

    ArrayList<God> ChooseGod(int player);

    ArrayList<God> PickGod(ClientConnection opponent,ArrayList<God> pickPool);

    ArrayList<God>PickGod3P(ClientConnection opponent1, ClientConnection opponent2, ArrayList<God> pickPool);
}
