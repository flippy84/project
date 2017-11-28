package Arena.Shared;

import java.io.Serializable;

/**
 * This class contains information about an user, make sure this class
 * is up to date with the table columns of table Users.
 */
public class User implements Serializable {
    public int id;
    public String username;
    public String password;
    public int rating;
    public UserType userType;
    public double accountBalance;

    public User(String username, String password, UserType userType) {
        this(username, password, 0, userType, 0);
    }

    public User(String username, String password, int rating, UserType userType, double accountBalance) {
        this.username = username;
        this.password = password;
        this.rating = rating;
        this.userType = userType;
        this.accountBalance = accountBalance;
    }
}