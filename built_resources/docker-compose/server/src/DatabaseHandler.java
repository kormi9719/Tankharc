import java.sql.*;
import java.util.*;

public class DatabaseHandler {

    public static ResultSet query(String queryString, Connection connection){

        ResultSet resultSet = null;

        try {

            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);

        } catch (SQLException e){
            e.printStackTrace();
        }

        return resultSet;
    }

    public static void closeConnection(Connection connection){
        try {
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
	
    public static void updateVerification(String endpoint){
		
        String query = "UPDATE users SET verified = true WHERE endpoint = ?";

        try {

			Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, endpoint);

            preparedStmt.execute();

        } catch (SQLException e){
            e.printStackTrace();
        }

    }

	public static void updateEndpoint(String email, String endpoint){

        // String query = "UPDATE users SET endpoint = " + '"' + endpoint + '"' + " WHERE email = " + '"' + email + '"';
		String query = "UPDATE users SET endpoint = ?  WHERE email = ?";

        try {

			Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString(1, endpoint);
			preparedStmt.setString(2, email);

            preparedStmt.execute();

        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static void registerUser(String username, String email, String password){

        String query = "INSERT INTO users (username, email, password)"
                + " values (?, ?, ?)";
				
		System.out.println("String OK");

        try {

            Connection databaseConnection = DatabaseHandler.getConnection();
            PreparedStatement preparedStmt = databaseConnection.prepareStatement(query);
            preparedStmt.setString(1, username);
            preparedStmt.setString(2, email);
            preparedStmt.setString(3, password);

            preparedStmt.execute();

            databaseConnection.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<User> getUsers(){

        try {
            Connection con = DatabaseHandler.getConnection();
			
            Statement stmt = con.createStatement();

            ArrayList<User> mysqlQueryResult = new ArrayList<>();

            ResultSet rs = stmt.executeQuery("SELECT * FROM users;");

            while (rs.next()) {

                User user = new User(rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("endpoint"), rs.getBoolean("verified"));
                mysqlQueryResult.add(user);

            }
			System.out.println("End OK");
			
			return mysqlQueryResult;
			
        } catch (SQLException e){
			e.printStackTrace();
            return null;
        }
    }

    public static Connection getConnection(){

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://host.docker.internal:30330/tankharc","root","R00t+");
            System.out.println("Database connection is alive");
            return connection;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
