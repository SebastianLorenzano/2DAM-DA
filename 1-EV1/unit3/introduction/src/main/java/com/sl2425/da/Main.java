package com.sl2425.da;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import static com.sl2425.da.DBOps.*;

public class Main
{
    public final static SessionFactory sessionFactory;
    static
    {
        SessionFactory temp = null;
        try
        {
            temp = new Configuration().configure().buildSessionFactory();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        sessionFactory = temp;
    }

    public static void main(String[] args)
    {
        if (sessionFactory == null)
        {
            System.out.println("Error creating session factory!");
            return;
        }

        SelectEmployee(null);
        var employee = new EmployeeEntity() {{ setEname("John"); setJob("Manager");}};
        int empno = insertEmployee(employee);

        var department = new DeptEntity() {{ setDname("HR"); setLoc("New York");}};
        int deptno = DBOps.insertDepartment(department);
        updateEmployee()


    }




}