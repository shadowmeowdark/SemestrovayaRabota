package DataBase;

import WorkWithData.GetDataFromDb;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class WorkWithDbData {

    public static void addDataToDb(BufferedReader br, String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String line = "";
        try {
            connection = ConnectionToDb.connectionToDb();
            clearData(connection, preparedStatement);
            preparedStatement = connection.prepareStatement("insert into data values(?,?,?,?,?,?);");
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] dates = line.split(";");
                Date dateReady = new Date(new SimpleDateFormat("dd/MM/yy").parse(dates[0]).getTime());
                preparedStatement.setDate(1, dateReady);
                preparedStatement.setFloat(2, Float.valueOf(dates[2]));
                preparedStatement.setFloat(3, Float.valueOf(dates[3]));
                preparedStatement.setFloat(4, Float.valueOf(dates[4]));
                preparedStatement.setFloat(5, Float.valueOf(dates[5]));
                preparedStatement.setString(6, username);
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void clearData(Connection conn, PreparedStatement ps) {
        try {
            ps = conn.prepareStatement("delete from data");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<GetDataFromDb> getData(String username , LocalDate date1 , LocalDate date2) {
        ArrayList<GetDataFromDb> listWithData = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        GetDataFromDb getDataFromDb = null;
        try {
            con = ConnectionToDb.connectionToDb();
            ps = con.prepareStatement("SELECT * FROM data where (username = '" + username +"' and date BETWEEN '"+ date1 + "' AND '"+ date2+"')");
            rs = ps.executeQuery();
            while (rs.next()) {
                getDataFromDb = new GetDataFromDb(rs.getString("date"), rs.getFloat("open"), rs.getFloat("low"), rs.getFloat("high"), rs.getFloat("close"));
                listWithData.add(getDataFromDb);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listWithData;
    }
}
