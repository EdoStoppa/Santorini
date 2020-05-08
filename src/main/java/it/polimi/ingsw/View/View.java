package it.polimi.ingsw.View;

import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.PosMessage;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.Server.ClientConnection;


public class View extends Observable<PosMessage>  implements Observer<GameMessage> {

    private class MessageReceiver implements Observer<String> {
        @Override
        public void update(String input) {
            System.out.println("Received: " + input);
            try {
                ParseClientInput(input);
            } catch (IllegalArgumentException e) {
                clientConnection.asyncSend("Error!");
            }
        }
    }

    private String idPlayer;
    private final Boolean isCli;
    private ClientConnection clientConnection;

    public View(String id, Boolean CLI, ClientConnection clientConnection) {
        this.idPlayer = id;
        this.isCli = CLI;
        this.clientConnection = clientConnection;
        this.clientConnection.addObserver(new MessageReceiver());
    }

    public String getIdPlayer() {
        return idPlayer;
    }

    public void SendToClient(Object message) {
        clientConnection.asyncSend(message);
    }

    public void ParseClientInput(String ClientMessage) {
        String[] cod = ClientMessage.split(" ");
        String[] pos = cod[1].split(",");
        if(cod[0].equals("end"))   {
            PosMessage clientInput = new PosMessage(cod[0],this.idPlayer, this, null);
            notify(clientInput);
        }
        else    {
            PosMessage clientInput = new PosMessage(cod[0], this.idPlayer, this, new Position(Integer.parseInt(pos[0]), Integer.parseInt(pos[1])));
            notify(clientInput);
        }
    }


    @Override
    public void update(GameMessage message) {
        SendToClient(message);
    }
}
