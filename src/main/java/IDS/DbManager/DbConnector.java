package IDS.DbManager;
import java.sql.*;

public class DbConnector {
    private static final String username = "root";
    private static final String password = "";
    private static final String url = "jdbc:mysql://localhost:3306/loyaltyplatform";
    private static Connection connection;

    /**
     * Connection method
     */
    public static void init() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Connection connection = DriverManager.getConnection(url, username, password);
            connection = DriverManager.getConnection(url, username, password);
            //DbConnector.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new IllegalStateException("Not connected", e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void insertQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }

    public static ResultSet executeQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public static void closeConnection()
    {
        try {
            DbConnector.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}