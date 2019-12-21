package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDb {
    public static Connection connectionToDb() {
        String dbPassword = "postgres";
        String user = "postgres";
        String url = "jdbc:postgresql://localhost:5432/postgres?user=" + user + "&password=" + dbPassword;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
