package it.polimi.ingsw.Model;

/**
 * Enum class used to represent all the possible phase of each turn
 * <p>
 *     CHOOSE_CONSTRUCTOR and END_TURN are used as first/last phase by everyone
 *     MOVE and BUILD are the "standard" phases
 *     SPECIAL_MOVE and SPECIAL_BUILD are used to manage all the phases related to a god card
 * </p>
 */
public enum PossiblePhases {
    CHOOSE_CONSTRUCTOR("Choose your constructor"), MOVE("Move your constructor"), BUILD("Build a construction"),
    SPECIAL_CHOOSE_CONSTRUCTOR("Choose your constructor"), SPECIAL_MOVE("Thanks to your God, you can do a special move!"),
    SPECIAL_BUILD("Thanks to your God, you can build again!");
    private final String message;

    PossiblePhases(String message){
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
