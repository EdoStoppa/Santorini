package it.polimi.ingsw.Message;

import it.polimi.ingsw.Client.PlaySpace;
import it.polimi.ingsw.Model.PossiblePhases;

public class BuildMessage extends GameMessage {
    private final int[][] buildingMatrix;

    public BuildMessage(String code, String currentPlayer, PossiblePhases currentPhase, int[][] matrix) {
        super(code, currentPlayer, currentPhase);
        this.buildingMatrix = matrix;
    }

    public int[][] getConstructorMatrix(){
        return this.buildingMatrix;
    }

    @Override
    public void autoSetMessage(boolean isMyTurn, boolean isCLI) {
        String text;

        if(isMyTurn){
            text = HelpMessage.endedPhase;
        } else {
            switch(getCode()){
                case "standard":
                    text = getIdPlayer() + " built on position " + getMessage();
                case "forcedDome":
                    text = getIdPlayer() + " built a dome on position " + getMessage();
                default:
                    text = "You broke the game!!! (Code in BuildMessage is unknown...)";
            }
        }

        setMessage(text);
    }

    @Override
    public void update(PlaySpace playSpace, boolean isMyTurn) {

    }
}
