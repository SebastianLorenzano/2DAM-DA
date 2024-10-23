package com.sl2425.da;
import javax.xml.transform.Result;
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
            readResultSetOfEmployee(preparedListByName.executeQuery());
           // */
            // Second Function //
            ///*
            System.out.println("Looking by job");
            System.out.println("Job: ");
            job = scanner.next();
            preparedListByJob.setString(1, job);
            readResultSetOfEmployee(preparedListByJob.executeQuery());
            // */

            // Third Function //
            ///*
            System.out.println("Looking by department number");
            System.out.println("Department number: ");
            deptNumber = Integer.parseInt(scanner.next());
            preparedListByDeptno.setInt(1, deptNumber);
            readResultSetOfEmployee(preparedListByDeptno.executeQuery());
            // */
        }
    }

    static void readResultSetOfEmployee(ResultSet rs) throws SQLException
    {
        System.out.println("Empno" + "\t" + "Ename" + "\t" + "Job" + "\t" + "Deptno");
        System.out.println("-----------------------------------------");
        while (rs.next()) {
            System.out.println(rs.getInt(1) + "\t " +
                    rs.getString(2) + "\t " +
                    rs.getString(3) + "\t" +
                            rs.getInt(4));

        }
        rs.close();
    }
    /* QUERIES */
    /// exercise7.4.1 ///
    /*

-- FUNCTION: public.employee_list_by_job(character varying)
-- DROP FUNCTION IF EXISTS public.employee_list_by_job(character varying);

CREATE OR REPLACE FUNCTION public.employee_list_by_job(
	p_job character varying)
    RETURNS SETOF employee
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 1000

AS $BODY$
BEGIN
	RETURN QUERY SELECT * FROM employee WHERE job ILIKE p_job;
END;
$BODY$;

ALTER FUNCTION public.employee_list_by_job(character varying)
    OWNER TO postgres;

     */

    /// exercise7.4.2 ///

    /*

-- FUNCTION: public.employee_list_by_deptno(integer)
-- DROP FUNCTION IF EXISTS public.employee_list_by_deptno(integer);

CREATE OR REPLACE FUNCTION public.employee_list_by_deptno(
	p_deptno integer)
    RETURNS SETOF employee
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 1000

AS $BODY$
BEGIN
	RETURN QUERY SELECT * FROM employee WHERE deptno = p_deptno;
END;
$BODY$;

ALTER FUNCTION public.employee_list_by_deptno(integer)
    OWNER TO postgres;

     */

    /// exercise 7.4.3 ///

    /*

-- FUNCTION: public.employee_list_by_name(character varying, character varying)
-- DROP FUNCTION IF EXISTS public.employee_list_by_name(character varying, character varying);

CREATE OR REPLACE FUNCTION public.employee_list_by_name(
	p_name character varying,
	p_wildcard character varying DEFAULT ''::character varying)
    RETURNS SETOF employee
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 1000

AS $BODY$
DECLARE r_name VARCHAR;
BEGIN
	IF p_wildcard = 'a%' THEN
		r_name := p_name || '%';
	ELSIF p_wildcard = '%a' THEN
		r_name := '%' || p_name;
	ELSIF p_wildcard = '%a%' THEN
		r_name := '%' || p_name || '%';
	ELSE
		r_name := p_name;
	END IF;
	RETURN QUERY SELECT * FROM employee WHERE ename ILIKE r_name;
END;
$BODY$;

ALTER FUNCTION public.employee_list_by_name(character varying, character varying)
    OWNER TO postgres;

     */
}