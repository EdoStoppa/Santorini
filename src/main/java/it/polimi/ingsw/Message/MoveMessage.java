package it.polimi.ingsw.Message;

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
            switch(getCode()){
                case "standard":
                    text = TextMessage.moveCurrent;
                case "canEnd":
                    text = TextMessage.moveCurrent + TextMessage.canEnd + (isCLI? TextMessage.withCLI : TextMessage.withGUI);
                default:
                    text = "You broke the Game (the message code was wrong, how did you do it?)";
            }
        } else {
            text = getIdPlayer() + TextMessage.moveOther;
        }

        setMessage(text);
    }
}
