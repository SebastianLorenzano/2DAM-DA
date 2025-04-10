package com.sl2425.da;
import java.sql.*;

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
        String SQLsentence = "INSERT INTO subjects (Name, Year, Hours) VALUES (?, ?, ?)";
        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = con.prepareStatement(SQLsentence))
        {
            preparedStatement.setString(1, "TEST_ASIGNATURE");
            preparedStatement.setInt(2, 0);
            preparedStatement.setInt(3, 0);
            int result = preparedStatement.executeUpdate();
            System.out.println(result);
        }

    }
}