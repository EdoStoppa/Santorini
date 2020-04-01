package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.God.God;

import java.util.ArrayList;

/**Represents the playing Player. After different player's provided information (idPlayer and playerBirthday)
 * the class maintain the assigned Constructor (2 of them), a god Card and the assigned numPlayer.
 */
public class Player {

    private String idPlayer;
    private String playerBirthday;
    /**List containing the 2 Constructors assigned to each Player
     */
    private ArrayList<Constructor> ownConstructors;
    private God god;
    /**Integer assigned for quick identification
     */
    private int playerNumber;



    /**Creates a Player with the chosen id and birthday. Plus assign the corresponding numPlayer and
     * the 2 Constructors
     *
     * @param id id chosen by the Player
     * @param day birthday of the Player
     * @param numP number assigned by the server
     */
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
