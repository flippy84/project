package Arena.Shared;

/**
 * This class contains information about an user, make sure this class
 * is up to date with the table columns of table Users.
 */
public class User {
    public String username;
    public String password;
    public int rating;
    public UserType userType;

    public User(String username, String password, UserType userType) {
        this(username, password, 0, userType);
    }

    public User(String username, String password, int rating, UserType userType) {
        this.username = username;
        this.password = password;
        this.rating = rating;
        this.userType = userType;
    }
}