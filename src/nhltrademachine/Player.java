
package nhltrademachine;

/**
 * This is the Player class it will store all of the information needed about the
 * players on a team
 * @author oicr1
 */
public class Player {

    // The datafields for the player class
    private String name;
    private String position;
    private String positionType;
    private double rating;
    private long id;

    /**
     * This is a 4 argument constructor initializing the name, position, rating
     * and id data fields
     *
     * @param name The name of the player
     * @param position The position of the player
     * @param rating The rating assigned to the player
     * @param id The ID of the player
     * @param positionType the type of position the player plays
     */
    public Player(String name, String position, double rating, long id, String positionType) {
        this.name = name;
        this.position = position;
        this.rating = rating;
        this.id = id;
        this.positionType = positionType;
    }

     /**
     * This is used to get the name of the player
     *
     * @return This will return the name of a specific player
     */
    public String getName() {
        return name;
    }

     /**
     * This will give the data field name a value
     *
     * @param name The name of a player
     */
    public void setName(String name) {
        this.name = name;
    }

     /**
     * This is used to get the position of a player
     *
     * @return This will return the position of a player
     */
    public String getPosition() {
        return position;
    }

     /**
     * This will give the data field position a value
     *
     * @param position The position of a player
     */
    public void setPosition(String position) {
        this.position = position;
    }

     /**
     * This is used to get the rating of a player
     *
     * @return This will return the rating of the player
     */
    public double getRating() {
        return rating;
    }

     /**
     * This will give the data field rating a value
     *
     * @param rating The rating a player was given
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

     /**
     * This is used to get the ID of a player
     *
     * @return This will return the ID of a player
     */
    public long getId() {
        return id;
    }

     /**
     * This will give the data field id a value
     *
     * @param id The ID of a player
     */
    public void setId(long id) {
        this.id = id;
    }

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    /**
     * This will return a string that will display the player's name, the
     * position they play, and the rating they were given
     *
     * @return This will return the player's name, their position, and their
     * rating in a String
     */
    @Override
    public String toString() {
        return getName() + "\nPosition: " + getPosition() + "\nRating: " + getRating();
    }
    
    
}
