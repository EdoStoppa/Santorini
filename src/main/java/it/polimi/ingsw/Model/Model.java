package it.polimi.ingsw.Model;

import it.polimi.ingsw.Message.GameMessage;
import it.polimi.ingsw.Observer.Observable;

import java.util.List;

public class Model extends Observable<GameMessage> {
    private GameState gameState;
    private Board board;
    private List<Position> tileToShow;

    public Model(){}

    public void nextPhase(){
        boolean hasNextPhase = gameState.nextPhase();

        if(!hasNextPhase){
            this.gameState.nextTurn();
        }
    }
}
