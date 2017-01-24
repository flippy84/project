/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 *
 * @author S152427
 */
public class Database{

    private Statement statement;
    private ResultSet resultSet;
    private String url;
    {
        url = "jdbc:sqlserver://hitsql-db.hb.se:56077;database=oomuht1608;user=oomuht1608;password=spad66";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
    public Database() throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        statement = connection.createStatement();

    }
    public ArrayList<String> getLeagues() throws SQLException{
        resultSet = statement.executeQuery
        ("select leagueName from League");

        ArrayList<String> leagues = new ArrayList<>();
        while(resultSet.next())
            leagues.add(resultSet.getString(1));
        return leagues;
    }
    public ArrayList<String> getTournament(String leagueName) throws SQLException{
        resultSet = statement.executeQuery
        ("select tournamentName from Tournament where leagueName ='" + leagueName + "';");

        ArrayList<String> tournaments = new ArrayList<>();
        while(resultSet.next())
            tournaments.add(resultSet.getString(1));
        return tournaments;
    }
    public ArrayList<String> getTournamnetPlayers(String tournamentName) throws SQLException{
        resultSet = statement.executeQuery
        ("select username from CurrentTournament where tournamentName ='" + tournamentName + "';");
        ArrayList<String> players = new ArrayList<String>();
        
        while(resultSet.next()){
            players.add(resultSet.getString(1));
        }
        return players;
    }
    public boolean checkLogin(String username, String password) throws SQLException {
        resultSet = statement.executeQuery
        ("select username, passwords from Users");

        while (resultSet.next()){
                if(username.equals(resultSet.getString(1)) && password.equals(resultSet.getString(2))){
                    System.out.println("Main success!\n");
                    return true;
            }
        }
        System.out.println("Main failed!\n");
        return false;
    }
    public void createLeague(String leagueName, String game) throws SQLException{
            statement.execute
                    ("INSERT INTO League(leagueName,game) VALUES('" + leagueName + "','" + game + "');");

    }
    public void createTournament(String tName, String leagueName) throws SQLException{
            statement.execute
            ("INSERT INTO Tournament(tournamentName,leagueName) VALUES('" + tName + "','" + leagueName + "');");
            
            System.out.println("Tournamentt Created!\n");
    }
    public void addPlayer(String username, String tournamentName) throws SQLException{
        statement.execute
       ("INSERT INTO CurrentTournament(username, tournamentName) VALUES('" + username + "','" + tournamentName + "');");
        statement.execute
        ("Update CurrentTournament SET playerCount = (SELECT TOP 1 playerCount FROM CurrentTournament WHERE tournamentName like '" + tournamentName + "' ORDER BY playerCount Desc) + 1 WHERE tournamentName LIKE '" + tournamentName + "';");

        System.out.println("Player Added!\n");
    }
    public void removePlayer(String username, int tournamentID) throws SQLException{
        statement.execute
        ("DELETE FROM CurrentTournament\n" +
        "WHERE username =" + username + ";");
    }
    public void addUser(String username, String password, String userType) throws SQLException{
        if(username.isEmpty() || password.isEmpty()){
            System.out.println("No fields can be blank.");
            return;
        }
        statement.execute("INSERT INTO Users(username,passwords, userType) VALUES('" + username + "','" + password + "','" + userType + "');");

        System.out.println("Successful register of " + username);
    }
    public void changeUsername(String newUsername, String currentUsername) throws SQLException{
        statement.execute("UPDATE Users SET username = '" + newUsername + "' where username like '" + currentUsername + "';");
    }
    public void changePassword(String newPassword, String currentUsername) throws SQLException{
        statement.execute("UPDATE Users SET passwords = '" + newPassword + "' where username like '" + currentUsername + "';");
    }
    public boolean tournamentActive(String tournamentName) throws SQLException{
        return true;
    }
    public String getUserType(String username) throws SQLException {
        resultSet = statement.executeQuery("select userType from Users where username ='" + username + "';");

        if(resultSet.next())
            return resultSet.getString(1);
        return "Not found";
    }
}
