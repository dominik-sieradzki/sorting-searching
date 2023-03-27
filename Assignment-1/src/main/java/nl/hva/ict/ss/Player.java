package nl.hva.ict.ss;

/**
 * Player keeps track of the name and the highscore for a player.
 * Once created the name can't be changed. The best score for this player
 * can be changed during the lifetime of an instance.
 */
public class Player implements Comparable<Player> {
    private String firstName;
    private String lastName;
    private long highScore;

    public Player(String firstName, String lastName, long highScore) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.highScore = highScore;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getHighScore() {
        return highScore;
    }

    public void setHighScore(long highScore) {
        this.highScore = highScore;
    }

    @Override
    public int compareTo(Player other) {
        // Should sort ascending on highscore
        // In case the highscores are equal the last and first name should be taken into account.
        // First sort by lastname and only take the first name into consideration if the
        // lastnames are identical.
        if(this.highScore > other.highScore) return 1;
        if(this.highScore < other.highScore) return -1;
        if(this.lastName.compareTo(other.lastName) != 0) return this.lastName.compareTo(other.lastName);
        if(this.firstName.compareTo(other.firstName) != 0) return this.firstName.compareTo(other.firstName);
        else return 0;
    }
}
