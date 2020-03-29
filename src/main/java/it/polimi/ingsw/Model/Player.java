package it.polimi.ingsw.Model;

import java.util.ArrayList;

public class Player {

    private String idPlayer;
    private String playerBirthday;
    private ArrayList<Constructor> ownConstructors;
    private God god;
    private int playerNumber;

    public Player(String id, String day, int numP){
        this.idPlayer = id;
        this.playerBirthday = day;
        this.ownConstructors = new ArrayList<Constructor>();
        ownConstructors.add(new Constructor(numP));
        ownConstructors.add(new Constructor(numP));
        this.playerNumber = numP;
        this.god = null;
    }

    public ArrayList<Constructor> getAllConstructors(){
        return this.ownConstructors;
    }


    public God getGod() {
        return god;
    }

    public void setGod(God g) {
        this.god = g;
    }
}
