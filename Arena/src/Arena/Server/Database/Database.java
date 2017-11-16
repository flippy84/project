package Arena.Server.Database;

import Arena.Shared.User;
import Arena.Shared.UserType;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    private Statement statement;
    private ResultSet resultSet;
    private String url;
    private Connection connection;

    {
        url = "jdbc:sqlserver://hitsql-db.hb.se:56077;Arena.Server.Database.Database=oomuht1608;user=oomuht1608;password=spad66";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Database() throws SQLException {
        connection = DriverManager.getConnection(url);
        statement = connection.createStatement();

    }

    public ArrayList<String> getLeagues() throws SQLException {
        resultSet = statement.executeQuery
                ("SELECT leagueName FROM League");

        ArrayList<String> leagues = new ArrayList<>();
        while (resultSet.next())
            leagues.add(resultSet.getString(1));
        return leagues;
    }

    public ArrayList<String> getTournament(String leagueName) throws SQLException {
        resultSet = statement.executeQuery
                ("select tournamentName from Tournament where leagueName ='" + leagueName + "';");

        ArrayList<String> tournaments = new ArrayList<>();
        while (resultSet.next())
            tournaments.add(resultSet.getString(1));
        return tournaments;
    }

    public ArrayList<String> getTournamnetPlayers(String tournamentName) throws SQLException {
        resultSet = statement.executeQuery
                ("select username from CurrentTournament where tournamentName ='" + tournamentName + "';");
        ArrayList<String> players = new ArrayList<String>();

        while (resultSet.next()) {
            players.add(resultSet.getString(1));
        }
        return players;
    }

    public boolean checkLogin(String username, String password) throws SQLException {
        resultSet = statement.executeQuery
                ("SELECT username, passwords FROM Users");

        while (resultSet.next()) {
            if (username.equals(resultSet.getString(1)) && password.equals(resultSet.getString(2))) {
                System.out.println("Main success!\n");
                return true;
            }
        }
        System.out.println("Main failed!\n");
        return false;
    }

    public void createLeague(String leagueName, String game) throws SQLException {
        statement.execute
                ("INSERT INTO League(leagueName,game) VALUES('" + leagueName + "','" + game + "');");

    }

    public void createTournament(String tName, String leagueName) throws SQLException {
        statement.execute
                ("INSERT INTO Tournament(tournamentName,leagueName) VALUES('" + tName + "','" + leagueName + "');");

        System.out.println("Tournamentt Created!\n");
    }

    public void addPlayer(String username, String tournamentName) throws SQLException {
        statement.execute
                ("INSERT INTO CurrentTournament(username, tournamentName) VALUES('" + username + "','" + tournamentName + "');");
        statement.execute
                ("Update CurrentTournament SET playerCount = (SELECT TOP 1 playerCount FROM CurrentTournament WHERE tournamentName like '" + tournamentName + "' ORDER BY playerCount Desc) + 1 WHERE tournamentName LIKE '" + tournamentName + "';");

        System.out.println("Player Added!\n");
    }

    public void removePlayer(String username, int tournamentID) throws SQLException {
        statement.execute
                ("DELETE FROM CurrentTournament\n" +
                        "WHERE username =" + username + ";");
    }

    public void changeUsername(String newUsername, String currentUsername) throws SQLException {
        statement.execute("UPDATE Users SET username = '" + newUsername + "' where username like '" + currentUsername + "';");
    }

    public void changePassword(String newPassword, String currentUsername) throws SQLException {
        statement.execute("UPDATE Users SET passwords = '" + newPassword + "' where username like '" + currentUsername + "';");
    }

    public boolean tournamentActive(String tournamentName) throws SQLException {
        return true;
    }

    public boolean addUser(String username, String password, int rating, UserType userType) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Users(username, password, rating, userType) VALUES(?, ?, ?, ?)");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setInt(3, rating);
            statement.setInt(4, userType.value());
            statement.executeUpdate();

        } catch (SQLException exception) {
            return false;
        }

        return true;
    }

    public boolean addUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Users(username, password, rating, userType) VALUES(?, ?, ?, ?)");
            statement.setString(1, user.username);
            statement.setString(2, user.password);
            statement.setInt(3, user.rating);
            statement.setInt(4, user.userType.value());
            statement.executeUpdate();

        } catch (SQLException exception) {
            return false;
        }

        return true;
    }

    public Optional<User> getUser(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT username, password, rating, userType FROM Users WHERE username = ?");
            statement.setString(1, username);
            resultSet = statement.executeQuery();

            if(!resultSet.next())
                return Optional.empty();

            UserType userType = UserType.Player;
            int value = resultSet.getInt("userType");
            for (UserType t : UserType.values()) {
                if (t.value() == value) {
                    userType = t;
                    break;
                }
            }

            return Optional.of(new User(resultSet.getString("username"), resultSet.getString("password"), resultSet.getInt("rating"), userType));
        } catch (SQLException exception) {
            return Optional.empty();
        }
    }

    public String getUserType(String username) throws SQLException {
        resultSet = statement.executeQuery("select userType from Users where username ='" + username + "';");

        if (resultSet.next())
            return resultSet.getString(1);
        return "Not found";
    }
}
