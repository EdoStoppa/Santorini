package it.polimi.ingsw.Client;

public enum Color {
    ANSI_RED("\u001B[31m"), //player1
    CYAN_BACKGROUND_BRIGHT("\033[0;106m"), // tiles to show
    ANSI_YELLOW("\u001B[33m"),
    ANSI_PURPLE("\033[095m"),
    ANSI_BLACK("\u001B[30m"),
    ANSI_BLUE("\u001B[34m");

    static final String RESET="\u001B[0m";

    private String escape;

    Color(String escape){
        this.escape=escape;
    }

    public String getEscape(){
        return escape;
    }

    @Override
    public String toString(){
        return escape;
    }


}
