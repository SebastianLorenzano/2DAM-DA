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
        String SQLlistByDeptno = "SELECT * FROM public.employee_list_by_deptno(?)";
        String SQLlistByJob = "SELECT * FROM public.employee_list_by_job(?)";
        String SQLlistByName = "SELECT * FROM public.employee_list_by_name(?, ?)";
        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedListByDeptno = con.prepareStatement(SQLlistByDeptno);
             PreparedStatement preparedListByJob = con.prepareStatement(SQLlistByJob);
             PreparedStatement preparedListByName = con.prepareStatement(SQLlistByName);
             )
        {
            Scanner scanner = new Scanner(System.in);
            String name = "";
            String wildcard = "";
            String job = "";
            int deptNumber;
            // First Function //
            //*
            System.out.println("Looking by name");
            System.out.println("Name: ");
            name = scanner.nextLine();
            System.out.println("Wildcard (a%, %a, %a%, or none): ");
            wildcard = scanner.nextLine();
            preparedListByName.setString(1, name);
            preparedListByName.setString(2, wildcard);
            ResultSet rs1 = preparedListByName.executeQuery();
            rs1.next();
           // */
            // Second Function //
            /*
            System.out.println("Looking by job");
            System.out.println("Job: ");
            job = scanner.next();
            preparedListByJob.setString(1, job);
            ResultSet rs2 = preparedListByJob.executeQuery();
            rs2.next();
             */

            // Third Function //
            /*
            System.out.println("Looking by department number");
            System.out.println("Department number: ");
            deptNumber = Integer.parseInt(scanner.next());
            preparedListByDeptno.setInt(1, deptNumber);
            ResultSet rs3 = preparedListByDeptno.executeQuery();
            rs3.next();
             */
        }

    }
}