package it.polimi.ingsw.Model;

import java.util.ArrayList;

/**Represents the playing <em>Player</em>. After getting multiple information from the <em>Player<em> (idPlayer and playerBirthday)
 * the class creates and stores the assigned <em>Constructor</em> (2 of them), stores a god Card and the assigned numPlayer.
 */
public class Player {

    private String idPlayer;
    private String playerBirthday;
    /**List containing the 2 <em>Constructors</em> assigned to each <em>Player</em>
     */
    private ArrayList<Constructor> ownConstructors;
    private God god;
    /**Integer assigned for quick identification
     */
    private int playerNumber;



    /**Creates a <em>Player</em> with the chosen id and birthday. Plus assign the corresponding numPlayer and
     * the 2 <em>Constructors</em>
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

    public String getIdPlayer(){
        return idPlayer;
    }

    public String getPlayerBirthday(){
        return playerBirthday;
    }

    public int getPlayerNumber(){
        return playerNumber;
    }

}
