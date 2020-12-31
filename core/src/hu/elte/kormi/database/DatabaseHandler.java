package hu.elte.kormi.database;

import hu.elte.kormi.helpers.GameInfo;
import hu.elte.kormi.skins.SkinInfo;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandler {

    // Method for closing the connection with the database
    public static void closeConnection(Connection connection){
        try {
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Method for inserting a player's score into the database
    public static void uploadPlayerScore(User user, int season, int map, int score){

        String query = "INSERT INTO scores (email, season, map_level, score) VALUES (?, ?, ?, ?)";
        try {
            Connection databaseConnection = DatabaseHandler.getConnection();

            assert databaseConnection != null;
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setInt(2, season);
            preparedStatement.setInt(3, map);
            preparedStatement.setInt(4, score);
            preparedStatement.execute();

            DatabaseHandler.closeConnection(databaseConnection);

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Function that returns the available skins for the user
    public static ArrayList<SkinInfo> getAvailableSkins(User user){

        ArrayList<SkinInfo> skinInfo = new ArrayList<>();
        for(int i = 0; i < GameInfo.SEASON; i++){
            String query = "SELECT DISTINCT map_level FROM scores WHERE email = ? AND season = ?";
            try {

                Connection databaseConnection = DatabaseHandler.getConnection();

                assert databaseConnection != null;
                PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
                preparedStatement.setString(1, user.getEmail());
                preparedStatement.setInt(2, i + 1);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    skinInfo.add(new SkinInfo(i + 1, resultSet.getInt("map_level"), "M"));
                }

                DatabaseHandler.closeConnection(databaseConnection);

            } catch (SQLException e){
                e.printStackTrace();
                return null;
            }

            query = "SELECT * FROM gambling WHERE email = ? AND season = ?";

            try {

                Connection databaseConnection = DatabaseHandler.getConnection();

                assert databaseConnection != null;
                PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
                preparedStatement.setString(1, user.getEmail());
                preparedStatement.setInt(2, i + 1);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    skinInfo.add(new SkinInfo(i + 1, resultSet.getInt("gambling"), "G"));
                }

                DatabaseHandler.closeConnection(databaseConnection);
                return skinInfo;

            } catch (SQLException e){
                e.printStackTrace();
                return null;
            }


        }

        return skinInfo;

    }

    // Return the scores from a map from a specified season
    public static ArrayList<Score> getLeaderboardScores(int season, int map){

        String query = "SELECT * FROM scores WHERE map_level = ? AND season = ? ORDER BY score desc LIMIT 10";
        ArrayList<Score> result = new ArrayList<>();
        int rank = 1;
        try {

            Connection databaseConnection = DatabaseHandler.getConnection();

            assert databaseConnection != null;
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
            preparedStatement.setInt(1, map);
            preparedStatement.setInt(2, season);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(new Score(rank, resultSet.getString("email"), resultSet.getInt("score")));
                rank = rank + 1;
            }

            DatabaseHandler.closeConnection(databaseConnection);
            return result;

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }


    // Returns the terrain information for the map
    public static ArrayList<String> getTerrain(int season, int map){

        String query = "SELECT row_info FROM terrain WHERE season = ? AND map_level = ? ORDER BY row_nr";
        ArrayList<String> result = new ArrayList<>();
        try {

            Connection databaseConnection = DatabaseHandler.getConnection();

            assert databaseConnection != null;
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
            preparedStatement.setString(1, Integer.toString(season));
            preparedStatement.setString(2, Integer.toString(map));

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(resultSet.getString("row_info"));
            }

            DatabaseHandler.closeConnection(databaseConnection);
            return result;

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    // Returns the target information for the map
    public static ArrayList<String> getTargets(int season, int map){

        String query = "SELECT row_info FROM targets WHERE season = ? AND map_level = ? ORDER BY row_nr";
        ArrayList<String> result = new ArrayList<>();
        try {

            Connection databaseConnection = DatabaseHandler.getConnection();

            assert databaseConnection != null;
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
            preparedStatement.setString(1, Integer.toString(season));
            preparedStatement.setString(2, Integer.toString(map));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(resultSet.getString("row_info"));
            }

            DatabaseHandler.closeConnection(databaseConnection);

            return result;

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    // Returns the rocks' information for the map
    public static ArrayList<String> getRocks(int season, int map){

        String query = "SELECT row_info FROM rocks WHERE season = ? AND map_level = ? ORDER BY row_nr";
        ArrayList<String> result = new ArrayList<>();
        try {

            Connection databaseConnection = DatabaseHandler.getConnection();

            assert databaseConnection != null;
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
            preparedStatement.setString(1, Integer.toString(season));
            preparedStatement.setString(2, Integer.toString(map));

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(resultSet.getString("row_info"));
            }

            DatabaseHandler.closeConnection(databaseConnection);
            return result;

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    // Returns all email addresses in the database
    public static ArrayList<String> getEmails(){

        try {
            Connection databaseConnection = DatabaseHandler.getConnection();

            assert databaseConnection != null;
            Statement statement = databaseConnection.createStatement();

            ArrayList<String> queryResult = new ArrayList<>();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM users;");

            while (resultSet.next()) {
                queryResult.add(resultSet.getString("email"));
            }

            DatabaseHandler.closeConnection(databaseConnection);

            return queryResult;

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    // Inserting a player's gambling result into the database
    public static void insertGamblingResult(String email, int season,  int gamblingResult){

        String query = "INSERT IGNORE INTO gambling (email, season, gambling) VALUES (?, ?, ?)";

        try {

            Connection databaseConnection = DatabaseHandler.getConnection();

            assert databaseConnection != null;
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, season);
            preparedStatement.setInt(3, gamblingResult);

            preparedStatement.execute();

            DatabaseHandler.closeConnection(databaseConnection);

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Returns the actual season number
    public static int getSeason(){

        String query = "SELECT season FROM admin_table";
        int result = 0;
        try {

            Connection databaseConnection = DatabaseHandler.getConnection();

            assert databaseConnection != null;
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result = resultSet.getInt("season");
            }

            DatabaseHandler.closeConnection(databaseConnection);
            return result;

        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    // Returns that if the given email address is verified
    public static boolean getIsEmailVerified(String email){

        String query = "SELECT verified FROM users WHERE email = ?";
        try {

            Connection databaseConnection = DatabaseHandler.getConnection();

            assert databaseConnection != null;
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            boolean result = false;

            while (resultSet.next()) {
                result = resultSet.getBoolean("verified");
            }

            DatabaseHandler.closeConnection(databaseConnection);

            return result;

        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    // Automatically gives the user his/her first skin
    public static void giveFirstSkin(String email){

        String query = "INSERT INTO gambling (email, season, gambling) VALUES (?, 1, 1)";
        try {

            Connection databaseConnection = DatabaseHandler.getConnection();

            assert databaseConnection != null;
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
            preparedStatement.setString(1, email);

            preparedStatement.execute();

            DatabaseHandler.closeConnection(databaseConnection);

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Returns the ids of the skins which are gathered by completing maps
    public static ArrayList<Integer> getMappedSkins(String email, int season){

        ArrayList<Integer> result = new ArrayList<>();
        String query = "SELECT DISTINCT map_level FROM scores WHERE email = ? AND season = ?";

        try {

            Connection databaseConnection = DatabaseHandler.getConnection();

            assert databaseConnection != null;
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, season);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
               result.add(resultSet.getInt("map_level"));
            }

            DatabaseHandler.closeConnection(databaseConnection);
            return result;

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }


    }

    // Returns the ids of the skins which are gathered by gambling
    public static ArrayList<Integer> getGambledSkins(String email, int season){

        ArrayList<Integer> result = new ArrayList<>();

        String query = "SELECT * FROM gambling WHERE email = ? AND season = ?";


        try {

            Connection databaseConnection = DatabaseHandler.getConnection();

            assert databaseConnection != null;
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, season);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(resultSet.getInt("gambling"));
            }

            DatabaseHandler.closeConnection(databaseConnection);
            return result;

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }


    }

    // Returns a user's username by his/her email address
    public static String getUsernameByEmail(String email){

        String query = "SELECT username FROM users WHERE email = ?";
        try {

            Connection databaseConnection = DatabaseHandler.getConnection();

            assert databaseConnection != null;
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            String result = null;

            while (resultSet.next()) {
                result = resultSet.getString("username");
            }

            DatabaseHandler.closeConnection(databaseConnection);

            return result;

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    // Returns the password for the corresponding email address
    public static String getPasswordByEmail(String email){

        String query = "SELECT password FROM users WHERE email = ?";
        try {

            Connection databaseConnection = DatabaseHandler.getConnection();

            assert databaseConnection != null;
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            String result = null;

            while (resultSet.next()) {
                result = resultSet.getString("password");
            }

            DatabaseHandler.closeConnection(databaseConnection);
            return result;

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    // Inserting the newly registered user into the database
    public static void registerUser(String username, String password, String email){

        String query = "INSERT INTO users (username, email, password, endpoint)"
                + " values (?, ?, ?, ?)";

        try {

            Connection databaseConnection = DatabaseHandler.getConnection();

            assert databaseConnection != null;
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, "");

            preparedStatement.execute();

            DatabaseHandler.closeConnection(databaseConnection);

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Returns a connection to the database
    public static Connection getConnection(){

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:30330/tankharc","root","R00t+");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
