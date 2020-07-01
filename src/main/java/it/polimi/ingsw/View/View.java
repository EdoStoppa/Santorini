package it.polimi.ingsw.View;

import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Message.MoveMessages.RemovedPlayerMessage;
import it.polimi.ingsw.Message.PosMessage;
import it.polimi.ingsw.Message.TileToShowMessages.TileToShowMessage;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.Server.SocketClientConnection;

/**
 * This is the view of the application
 */
public class View extends Observable<PosMessage>  implements Observer<GameMessage> {

    /**
     * This class is used to receive messages
     */
    private class MessageReceiver implements Observer<String> {
        @Override
        public void update(String input) {
            System.out.println("Received: " + input);
            try {
                parseClientInput(input);
            } catch (IllegalArgumentException e) {
                clientConnection.asyncSend("Error!");
            }
        }
    }

    private final String idPlayer;
    private final int numPlayer;
    private final SocketClientConnection clientConnection;

    public View(String id, int numPlayer, SocketClientConnection clientConnection) {
        this.idPlayer = id;
        this.numPlayer = numPlayer;
        this.clientConnection = clientConnection;
        this.clientConnection.addObserver(new MessageReceiver());
    }

    /**
     * @return the id of the player
     */
    public String getIdPlayer() {
        return idPlayer;
    }

    /**
     * This method sends a message to the client
     * @param message sent to the client
     */
    public void sendToClient(Object message) {
        clientConnection.send(message);
    }

    /**
     * This method parses the input from the client. If input equals to "end", then it notifies a PosMessage
     * with no position, elsewhere it notifies a PosMessage with the chosen position
     * @param clientMessage
     */
    public void parseClientInput(String clientMessage) {
        String[] cod = clientMessage.split(" ");
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

    /**
     * This method is used to update the view. If the idPlayer of the message is null then the game is
     * ended, elsewhere the view sends the message to the client.
     * @param message
     */
    @Override
    public void update(GameMessage message) {
        if(message.getIdPlayer() != null)   {
            if (message instanceof RemovedPlayerMessage && message.getIdPlayer().equals(idPlayer))
                clientConnection.setPlaying(false);

            System.out.println( idPlayer + " sends -> Current Player: " + message.getIdPlayer() + ", Phase: " + message.getPhase() +
                                ", Message: " + message.getClass().getSimpleName() + (message instanceof TileToShowMessage? ", # of tileToShow: " + ((TileToShowMessage)message).getTileToShow().size() : ""));
            sendToClient(message);
        } else {
            System.out.println("Game ended, proceeding to close server-side of the match");
            clientConnection.close(numPlayer);
        }
    }
}
