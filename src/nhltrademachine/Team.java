
package nhltrademachine;

import java.util.ArrayList;

/**
 * This is the Team class it will store all of the information needed about the
 * team
 * @author oicr1
 */
public class Team {

    // These are the datafields for the Team class
    private String name;
    private int id;
    private ArrayList<Player> players;

   
     /**
     * This is a 3 argument constructor initializing the name, id, and players
     * data fields
     *
     * @param name The name of the team
     * @param id The ID of the team
     * @param players An array list of all the players on the team
     */
    public Team(String name, int id, ArrayList<Player> players) {
        this.name = name;
        this.id = id;
        this.players = players;
    }

      /**
     * This is used to get the name of the team
     *
     * @return This will return the name of the team
     */
    public String getName() {
        return name;
    }

    /**
     * This will give the data field name a value
     *
     * @param name The name of the team
     */
    public void setName(String name) {
        this.name = name;
    }

      /**
     * This is used to get the ID of the team
     *
     * @return This will return the team's ID
     */
    public long getId() {
        return id;
    }

    /**
     * This will give the data field id a value
     *
     * @param id The team's ID
     */
    public void setId(int id) {
        this.id = id;
    }

      /**
     * This is used to get the players on the team
     *
     * @return This will return the players who play for this team
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * This will give the data field players a value
     *
     * @param players The information about the players who play for this team
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
    
    
    
    
}
