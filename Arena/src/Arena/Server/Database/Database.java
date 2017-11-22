package Arena.Server.Database;

import Arena.Shared.GameState;
import Arena.Shared.User;
import Arena.Shared.UserType;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import javafx.fxml.Initializable;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.sql.*;
import java.util.ArrayList;
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

    public boolean checkLogin(String username, String password) throws SQLException {
        result = statement.executeQuery
                ("SELECT username, passwords FROM Users");

        while (result.next()) {
            if (username.equals(result.getString(1)) && password.equals(result.getString(2))) {
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

    private Optional<InputStream> getInputStream(GameState gameState) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(gameState);

            byte[] buffer = out.toByteArray();
            ByteInputStream input = new ByteInputStream(buffer, buffer.length);
            return Optional.of(input);
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

    public void uploadGame() throws Exception {
            File f = new File("C:\\Users\\Ante\\IdeaProjects\\project\\out\\artifacts\\Sample_jar\\Sample.jar");
            FileReader reader = new FileReader(f);
            FileInputStream input = new FileInputStream(f);

            PreparedStatement statement = connection.prepareStatement("INSERT INTO Games (jar) VALUES (?)");
            statement.setBinaryStream(1, input);
            if (statement.execute()) {
                System.out.println("Games uploaded successfully");
            }
    }

    public void downloadGame() throws Exception {
        PreparedStatement statement = connection.prepareStatement("SELECT jar FROM Games WHERE id = 1");
        result = statement.executeQuery();
        if (!result.next())
            return;

        InputStream stream = result.getBinaryStream(1);
        Path p = FileSystems.getDefault().getPath("C:\\Games\\sample.jar");
        Files.createDirectories(p.getParent());
        Files.copy(stream, p, StandardCopyOption.REPLACE_EXISTING);
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

    public Optional<User> getUser(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT username, password, rating, userType FROM Users WHERE username = ?");
            statement.setString(1, username);
            result = statement.executeQuery();

            if(!result.next())
                return Optional.empty();

            UserType userType = UserType.Player;
            int value = result.getInt("userType");
            for (UserType t : UserType.values()) {
                if (t.value() == value) {
                    userType = t;
                    break;
                }
            }

            return Optional.of(new User(result.getString("username"), result.getString("password"), result.getInt("rating"), userType));
        } catch (SQLException exception) {
            return Optional.empty();
        }
    }

    public String getUserType(String username) throws SQLException {
        result = statement.executeQuery("select userType from Users where username ='" + username + "';");

        if (result.next())
            return result.getString(1);
        return "Not found";
    }
}
