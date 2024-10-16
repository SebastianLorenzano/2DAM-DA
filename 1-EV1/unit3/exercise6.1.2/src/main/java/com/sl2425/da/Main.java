package com.sl2425.da;
import java.sql.*;
import java.util.Scanner;

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
        String SQLCreateTable = "CREATE TABLE courses (code SERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL)";
        String SQLInsert = "INSERT INTO courses (name) VALUES (?) RETURNING code";
        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedCreate = con.prepareStatement(SQLCreateTable);
             PreparedStatement preparedInsert = con.prepareStatement(SQLInsert))
        {
            int result;
            result = preparedCreate.executeUpdate();
            System.out.println("Resultado: " + result);
            Scanner scanner = new Scanner(System.in);
            String name;
            while (true)
            {
                System.out.println("Adding a new Course");
                System.out.println("Name: ");
                name = scanner.nextLine();
                preparedInsert.setString(1, name);
                ResultSet rs = preparedInsert.executeQuery();
                rs.next();
                int generatedCode = rs.getInt(1);
                System.out.println("The auto-generated code is: " + generatedCode);

                System.out.println("Do you want to continue? (y/n)");
                if (!scanner.nextLine().equalsIgnoreCase("y"))
                    break;
            }
        }

    }
}