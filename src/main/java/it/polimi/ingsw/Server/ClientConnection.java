package it.polimi.ingsw.Server;


import it.polimi.ingsw.Observer.Observer;

import java.util.ArrayList;
import java.util.Map;

public interface ClientConnection{

    void closeConnection();

    void addObserver(Observer<String> observer);

    void asyncSend(Object message);

    String enterNewName(Map <String, ClientConnection> NameOpponent);

    ArrayList<Integer> ChooseGod(int player);

    ArrayList<Integer> PickGod(ClientConnection opponent,ArrayList<Integer> pickPool);

    ArrayList<Integer>PickGod3P(ClientConnection opponent1, ClientConnection opponent2, ArrayList<Integer> pickPool);
}
