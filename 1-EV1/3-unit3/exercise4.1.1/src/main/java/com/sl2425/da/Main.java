package com.sl2425.da;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ConsultaBD1(null);
    }
    public static void ConsultaBD1(String[] args)
            throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:5432/VTInstitute_SL_2425";
        String user = "postgres";
        String password = "postgres";
        Connection con = DriverManager.getConnection(url, user,
                password);
        Statement statement = con.createStatement();
        String SQLsentence = "SELECT * FROM subjects ORDER BY code";
        ResultSet rs = statement.executeQuery(SQLsentence);
        System.out.println("Code" + "\t" + "Name");
        System.out.println("-----------------------------------------");
        while (rs.next()) {
            System.out.println(rs.getString(1) + "\t " +
                    rs.getString(2));
        }
        rs.close();
        con.close();
    }
}