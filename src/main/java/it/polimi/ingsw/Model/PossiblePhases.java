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
    CHOOSE_CONSTRUCTOR("Choose which constructor you'll use among the highlighted ones", " is choosing which constructor to use"),
    MOVE("Choose where to move among the highlighted tiles", " is choosing where to move"),
    BUILD("Choose where to build among the highlighted tiles", " is choosing where to build"),
    SPECIAL_CHOOSE_CONSTRUCTOR("Choose your constructor", " is choosing which constructor to use"),
    SPECIAL_MOVE("Choose where to move among the highlighted tiles", " is choosing where to move"),
    SPECIAL_BUILD("Choose where to build among the highlighted tiles", " is choosing where to build");
    private final String message1;
    private final String message2;

    PossiblePhases(String message1, String message2){
        this.message1 = message1;
        this.message2 = message2;
    }

    public String toString(boolean current) {
        if(current)
            return message1;
        else
            return message2;
    }
}
