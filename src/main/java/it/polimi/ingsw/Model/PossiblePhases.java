package it.polimi.ingsw.Model;

public enum PossiblePhases {
    START_TURN ("New turn started"), CHOOSE_CONSTRUCTOR("Choose your constructor"), MOVE("Move your constructor"), BUILD("Build a construction"),
    SPECIAL_MOVE("Thanks to your God, you can do a special move!"), SPECIAL_BUILD("Thanks to your God, you can build again!"), END_TURN("End of turn");
    private final String message;

    PossiblePhases(String message){
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
