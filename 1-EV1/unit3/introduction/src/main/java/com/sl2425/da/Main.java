package com.sl2425.da;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        SessionFactory sessionFactory =
                new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        if (session != null) {
            System.out.println("Session successfully opened!");
        } else {
            System.out.println("Error opening session!");
        }

        Query<EmployeeEntity> myQuery =
                session.createQuery("from com.sl2425.da.EmployeeEntity");
        List<EmployeeEntity> employees = myQuery.list();
        for ( EmployeeEntity employee : employees ) {
            System.out.printf("Number : %d : Name: %s \n", employee.getEmpno(),
                    employee.getEname());
        }



    }
}