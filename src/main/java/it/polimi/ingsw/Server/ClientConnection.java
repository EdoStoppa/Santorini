package it.polimi.ingsw.Server;


import it.polimi.ingsw.Message.ChosenGodMessage;
import it.polimi.ingsw.Message.OrderGameMessage;
import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Observer.Observer;

import java.util.ArrayList;
import java.util.Map;

public interface ClientConnection{

    void closeConnection();

    void addObserver(Observer<String> observer);

    void asyncSend(Object message);

    String enterNewName(Map <String, ClientConnection> NameOpponent);

    ArrayList<Integer> ChooseGod(int player);

    God PickGod(ChosenGodMessage chosenGodMessage);

    ArrayList<Player> ChooseFirstPlayer(OrderGameMessage orderGameMessage);
}
