package Arena.Server.Database;

import Arena.Client.Games.GameDescription;
import Arena.Shared.Advertisement;
import Arena.Shared.GameState;
import Arena.Shared.User;
import Arena.Shared.UserType;
import Arena.Shared.Utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    private Statement statement;
    private ResultSet result;
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
        result = statement.executeQuery
                ("SELECT leagueName FROM League");

        ArrayList<String> leagues = new ArrayList<>();
        while (result.next())
            leagues.add(result.getString(1));
        return leagues;
    }

    public ArrayList<String> getTournament(String leagueName) throws SQLException {
        result = statement.executeQuery
                ("select tournamentName from Tournament where leagueName ='" + leagueName + "';");

        ArrayList<String> tournaments = new ArrayList<>();
        while (result.next())
            tournaments.add(result.getString(1));
        return tournaments;
    }

    public ArrayList<String> getTournamnetPlayers(String tournamentName) throws SQLException {
        result = statement.executeQuery
                ("select username from CurrentTournament where tournamentName ='" + tournamentName + "';");
        ArrayList<String> players = new ArrayList<String>();

        while (result.next()) {
            players.add(result.getString(1));
        }
        return players;
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

    public boolean depositFunds(String username, Double deposit) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE Users SET accountBalance = accountBalance + ? WHERE username = ?");
            statement.setDouble(1, deposit);
            statement.setString(2, username);
            statement.executeUpdate();

        } catch (SQLException exception) {
            return false;
        }

        return true;
    }

    public boolean withdrawFunds(String username, Double withdrawal) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE Users SET accountBalance = accountBalance - ? WHERE username = ?");
            statement.setDouble(1, withdrawal);
            statement.setString(2, username);
            statement.executeUpdate();

        } catch (SQLException exception) {
            return false;
        }

        return true;
    }

    public double getBalance(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT accountBalance FROM Users WHERE username = ?");
            statement.setString(1, username);
            result = statement.executeQuery();

            if (!result.next())
                return 0;

            return result.getDouble(1);
        } catch (SQLException exception) {
            return 0;
        }
    }

    public boolean addUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Users(username, password, rating, userType, accountBalance) VALUES(?, ?, ?, ?, ?)");
            statement.setString(1, user.username);
            statement.setString(2, user.password);
            statement.setInt(3, user.rating);
            statement.setInt(4, user.userType.value());
            statement.setDouble(5, user.accountBalance);
            statement.executeUpdate();

        } catch (SQLException exception) {
            return false;
        }

        return true;
    }

    private Optional<InputStream> getInputStream(GameState gameState) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(gameState);

            byte[] buffer = out.toByteArray();
            ByteArrayInputStream in = new ByteArrayInputStream(buffer, 0, buffer.length);
            return Optional.of(in);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    public void saveGame(GameState gameState, User player1, User player2) {
        Optional<InputStream> inputOptional = getInputStream(gameState);
        if (!inputOptional.isPresent())
            return;
        InputStream input = inputOptional.get();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Matches WHERE player1 = ? AND player2 = ?");
            statement.setInt(1, player1.id);
            statement.setInt(2, player2.id);

            result = statement.executeQuery();
            if (!result.next()) {
                // No ResultSet, insert a new row.
                statement = connection.prepareStatement("INSERT INTO Matches (player1, player2, gameState) VALUES (?, ?, ?)");
                statement.setInt(1, player1.id);
                statement.setInt(2, player2.id);
                statement.setBinaryStream(3, input);
                statement.execute();
            } else {
                // A row for this set already exists, update it with the new GameState.
                statement = connection.prepareStatement("UPDATE Matches SET gameState = ? WHERE player1 = ? AND player2 = ?");
                statement.setObject(1, input);
                statement.setInt(2, player1.id);
                statement.setInt(3, player2.id);
                statement.execute();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public boolean uploadGame(String gameBase64, String name, String description) {
            try {
                ByteArrayInputStream in = new ByteArrayInputStream(Base64.getDecoder().decode(gameBase64));
                PreparedStatement statement = connection.prepareStatement("INSERT INTO Games (name, description, jar, approved) VALUES (?, ?, ?, ?)");
                statement.setString(1, name);
                statement.setString(2, description);
                statement.setBinaryStream(3, in);
                statement.setBoolean(4, false);
                if (statement.executeUpdate() == 0)
                    return false;
            } catch (Exception exception) {
                return false;
            }

            return true;
    }

    public String downloadGame(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT jar FROM Games WHERE id = ?");
            statement.setInt(1, id);
            result = statement.executeQuery();
            if (!result.next())
                return "";

            InputStream out = result.getBinaryStream(1);
            String encodedGame = Utility.getBase64String(out);
            return encodedGame;
        } catch (Exception exception) {
            return "";
        }
    }

    private List<GameDescription> getGameList(boolean onlyApproved) {
        List<GameDescription> gameList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT id, name, description, approved FROM Games" + (onlyApproved == true ? " WHERE approved = 1" : "") + ";");
            result = statement.executeQuery();

            while (result.next()) {
                gameList.add(new GameDescription(result.getInt(1), result.getString(2), result.getString(3), result.getBoolean(4)));
            }
        } catch (Exception exception) {
            return gameList;
        }

        return gameList;
    }

    public List<GameDescription> getApprovedGameList() {
        return getGameList(true);
    }

    public List<GameDescription> getGameList() {
        return getGameList(false);
    }

    public Optional<GameState> loadGame(User player1, User player2) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT gameState FROM Matches WHERE player1 = ? AND player2 = ?");
            statement.setInt(1, player1.id);
            statement.setInt(2, player2.id);
            result = statement.executeQuery();

            if (!result.next())
                return Optional.empty();

            GameState gameState = (GameState) result.getObject(1);
            return Optional.of(gameState);
        } catch (SQLException exception) {
            return Optional.empty();
        }
    }

    public User getUser(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT username, password, rating, userType, accountBalance FROM Users WHERE username = ?");
            statement.setString(1, username);
            result = statement.executeQuery();

            if(!result.next())
                return null;

            UserType userType = UserType.Player;
            int value = result.getInt("userType");
            for (UserType t : UserType.values()) {
                if (t.value() == value) {
                    userType = t;
                    break;
                }
            }

            return new User(result.getString("username"), result.getString("password"), result.getInt("rating"), userType, result.getDouble("accountBalance"));
        } catch (SQLException exception) {
            return null;
        }
    }

    public void approveGame(int id) {
        updateApproval(id, true);
    }

    public void revokeGame(int id) {
        updateApproval(id, false);
    }

    private void updateApproval(int id, boolean approved) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE Games SET approved = ? WHERE id = ?");
            statement.setBoolean(1, approved);
            statement.setInt(2, id);
            statement.execute();
        } catch (SQLException exception) {
            return;
        }
    }

    public void removeGame(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Games WHERE id = ?");
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException exception) {
            return;
        }
    }

    public boolean addAdvertisement(Advertisement advertisement) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Advertisement(id, name, duration, image, url) VALUES(?, ?, ?, ?, ?)");
            statement.setInt(1, advertisement.id);
            statement.setString(2, advertisement.name);
            statement.setInt(3, advertisement.duration);
            statement.setString(4, advertisement.image);
            statement.setString(5, advertisement.url);
            statement.executeUpdate();

        } catch (SQLException exception) {
            return false;
        }

        return true;
    }

    public Advertisement getAdvertisment(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT id, name, duration, image, url FROM Advertisement WHERE id = ?");
            statement.setInt(1, id);
            result = statement.executeQuery();

            if(!result.next())
                return null;

            return new Advertisement(result.getInt("id"), result.getString("name"), result.getInt("duration"), result.getString("image"), result.getString("url"));
        } catch (SQLException exception) {
            return null;
        }
    }
}
