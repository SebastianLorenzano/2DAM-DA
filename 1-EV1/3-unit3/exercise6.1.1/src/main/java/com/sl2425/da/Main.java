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
        String SQLsentence = "INSERT INTO subjects (Name, Year, Hours) VALUES (?, ?, ?)";
        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = con.prepareStatement(SQLsentence))
        {
            Scanner scanner = new Scanner(System.in);
            String name; int year; int hours;
            while (true)
            {
                System.out.println("Adding a new Asignature");
                System.out.println("Name: ");
                name = scanner.nextLine();
                System.out.println("Year: ");
                year = Integer.parseInt(scanner.nextLine());
                System.out.println("Hours: ");
                hours = Integer.parseInt(scanner.nextLine());

                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, year);
                preparedStatement.setInt(3, hours);
                int result = preparedStatement.executeUpdate();
                System.out.println(result);

                System.out.println("Do you want to continue? (y/n)");
                if (!scanner.nextLine().equalsIgnoreCase("y"))
                    break;
            }
        }

    }
}