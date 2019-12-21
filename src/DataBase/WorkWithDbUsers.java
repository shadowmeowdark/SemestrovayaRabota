package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkWithDbUsers {
    public static boolean addUserToDb(String username, String password) {
        boolean isUserExists = false;
        try {
            Connection connection = ConnectionToDb.connectionToDb();
            try (PreparedStatement preparedStatement1 = connection.prepareStatement("select username from users where username = ?")) {
                preparedStatement1.setString(1, username);
                try (ResultSet rs = preparedStatement1.executeQuery()) {
                    if (rs.next()) isUserExists = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (isUserExists == false) {
                PreparedStatement preparedStatement = connection.prepareStatement("insert into users values(?,?)");
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUserExists;
    }

    public static boolean checkUser(String username, String password) {
        boolean checkUser = false;
        Connection connection = ConnectionToDb.connectionToDb();
        try (
                PreparedStatement ps = connection.prepareStatement("select 1 from users where username = ? and password = ?")) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    checkUser = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return checkUser;
    }
}

