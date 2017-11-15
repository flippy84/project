package Shared;

public class User {
    public String username;
    public String password;
    public int rating;
    public UserType userType;

    public User(String username, String password) {
        this(username, password, 0, UserType.Player);
    }

    public User(String username, String password, int rating, UserType userType) {
        this.username = username;
        this.password = password;
        this.rating = rating;
        this.userType = userType;
    }
}