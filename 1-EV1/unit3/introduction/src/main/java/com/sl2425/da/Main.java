package com.sl2425.da;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class Main
{
    public static SessionFactory sessionFactory = null;
    public static void main(String[] args)
    {
        DBOps.SelectEmployee(null);
        var employee = new EmployeeEntity() {{ setEname("John"); setJob("Manager");}};
        int empno = DBOps.insertEmployee(employee);

        var department = new DeptEntity() {{ setDname("HR"); setLoc("New York");}};
        int deptno = DBOps.insertDepartment(department);
        updateEmployee()

    }




}