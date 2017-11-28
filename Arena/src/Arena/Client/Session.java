package Arena.Client;

import Arena.Shared.User;

public class Session {
    private static Session sessionInstance;
    private User user;

    private Session() {

    }

    public static Session getInstance() {
        if (sessionInstance == null)
            sessionInstance = new Session();
        return sessionInstance;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
