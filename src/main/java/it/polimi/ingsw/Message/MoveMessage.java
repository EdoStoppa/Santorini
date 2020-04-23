package it.polimi.ingsw.Message;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Model.PossiblePhases;

public class MoveMessage extends GameMessage {
    private final int[][] constructorMatrix;

    public MoveMessage(String code, String currentPlayer, PossiblePhases currentPhase, int[][] matrix) {
        super(code, currentPlayer, currentPhase);
        this.constructorMatrix = matrix;
    }

    public int[][] getConstructorMatrix(){
        return this.constructorMatrix;
    }

    @Override
    public void autoSetMessage(boolean isMyTurn, boolean isCLI) {
        String text;

        if(isMyTurn){
            if(getCode().equals("removedPlayer"))
                text = "You lose... :(";
            else
                text = HelpMessage.endedPhase;
        } else {
            switch(getCode()){
                case "standard":
                    text = getIdPlayer() + " moved to " + getMessage();
                case "swap":
                    text = getIdPlayer() + " swapped with constructor on position: " + getMessage();
                case "push":
                    text = getIdPlayer() + " pushed constructor on position: " + getMessage();
                case "removedPlayer":
                    text = getIdPlayer() + " lost";
                default:
                    text = "You broke the game!!! (Code in MoveMessage is unknown...)";
            }
        }

        setMessage(text);
    }

    @Override
    public void update(PlaySpace playSpace, boolean isMyTurn) {

    }
}
