package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.TimeZone;

public class LogirovanieForDb {
    public static void log(String user, int choose_Number) {
        Connection con = ConnectionToDb.connectionToDb();
        PreparedStatement preparedStatement = null;
        String inf = "";
        try {
            preparedStatement = con.prepareStatement("INSERT INTO information(username, time, information) VALUES(?,?,?)");
            preparedStatement.setString(1, user);
            ZonedDateTime receivedTimestamp = ZonedDateTime.now(ZoneId.systemDefault());
            Timestamp ts = new Timestamp(receivedTimestamp.toInstant().toEpochMilli());
            preparedStatement.setTimestamp(2, ts, Calendar.getInstance(TimeZone.getTimeZone(receivedTimestamp.getZone()))
            );
            switch (choose_Number) {
                case 1:
                    inf = "registration";
                    break;
                case 2:
                    inf = "sign in";
                    break;
                case 3:
                    inf = "log out";
                    break;
            }
            preparedStatement.setString(3, inf);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addResults(String username, String result) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionToDb.connectionToDb();
            preparedStatement = connection.prepareStatement("insert into results values(?,?);");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, result);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
