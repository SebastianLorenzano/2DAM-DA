package com.sl2425.da;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

import static com.sl2425.da.Main.sessionFactory;

public class DBOps
{
    private static Session openSession() throws Exception
    {

        Session session = sessionFactory.openSession();
        if (session == null)
            throw new Exception("Error opening session!");
        return session;
    }

    public static int insertDepartment(DeptEntity department)
    {
        if (department == null)
            return -1;
        Transaction transaction = null;
        try (Session session = openSession())
        {
            if (department.getDeptno() == 0)
                department.setDeptno(getNextDeptno(session));
            transaction = session.beginTransaction();
            session.save(department);
            transaction.commit();
            return department.getDeptno();
        }
        catch( Exception e )
        {
            if (transaction != null)
                transaction.rollback();
            System.out.println( e.getMessage() );
            return -1;
        }
    }

    public static int insertEmployee(EmployeeEntity employee)
    {
        if (employee == null)
            return -1;
        Transaction transaction = null;
        try (Session session = openSession())
        {
            transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
            return employee.getEmpno();
        }
        catch(Exception e)
        {
            if (transaction != null)
                transaction.rollback();
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public static List<EmployeeEntity> SelectEmployee(String where) // I know it's not a good practice to use
    // string for where clause and I wouldn't do it in a real app
    {
        String queryString = "from EmployeeEntity";
        if (where != null)
            queryString += " where " + where;
        try (Session session = openSession())
        {
            Query<EmployeeEntity> query = session.createQuery(queryString, EmployeeEntity.class);
            return query.list();
        }
        catch( Exception e )
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static List<DeptEntity> selectDepartment(String where) // I know it's not a good practice to use
    // string for where clause and I wouldn't do it in a real app
    {
        String queryString = "from DeptEntity";
        if (where != null)
            queryString += " where " + where;
        try (Session session = openSession())
        {
            Query<DeptEntity> query = session.createQuery(queryString, DeptEntity.class);
            return query.list();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static boolean updateEmployee( int employeeNumber, EmployeeEntity newEmployee)
    {
        try (Session session = openSession())
        {
            EmployeeEntity employee = session.get(EmployeeEntity.class, employeeNumber);
            if (employee != null)
            {
                if (newEmployee.getEname() != null)
                    employee.setEname(newEmployee.getEname());
                if (newEmployee.getJob() != null)
                    employee.setJob(newEmployee.getJob());
                employee.setDepartment(newEmployee.getDepartment());
                session.update(employee);
                return true;
            }
            return false;
        }
        catch( Exception e )
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean updateDepartment(int departmentNumber, DeptEntity newDepartment)
    {
        try (Session session = openSession())
        {
            DeptEntity department = session.get(DeptEntity.class, departmentNumber);
            if (department != null)
            {
                if (newDepartment.getDname() != null)
                    department.setDname(newDepartment.getDname());
                if (newDepartment.getLoc() != null)
                    department.setLoc(newDepartment.getLoc());
                session.update(department);
                return true;
            }
            return false;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean deleteEmployee(int employeeNumber)
    {
        Transaction transaction = null;
        try (Session session = openSession())
        {
            EmployeeEntity employee = session.get(EmployeeEntity.class, employeeNumber);
            if (employee != null)
            {
                transaction = session.beginTransaction();
                session.delete(employee);
                transaction.commit(); // End of transaction
                return true;
            }
            else
            {
                if (transaction != null)
                    transaction.rollback();
                return false;
            }
        }
        catch( Exception e )
        {
            if (transaction != null)
                transaction.rollback();
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean deleteDepartment(int departmentNumber)
    {
        Transaction transaction = null;
        try (Session session = openSession())
        {
            DeptEntity department = session.get(DeptEntity.class, departmentNumber);
            if (department != null)
            {
                transaction = session.beginTransaction();
                session.delete(department);
                transaction.commit(); // End of transaction
                return true;
            }
            else
            {
                if (transaction != null)
                    transaction.rollback();
                return false;
            }
        }
        catch(Exception e)
        {
            if (transaction != null)
                transaction.rollback();
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static void PrintEmployees(List<EmployeeEntity> employees)
    {
        if (employees != null)
            for (EmployeeEntity employee : employees)
            {
                System.out.println(employee.getEmpno() + " " + employee.getEname() + " " +
                        employee.getJob() + " " + employee.getDepartment().getDname());
            }
    }

    private static int getNextDeptno(Session session) {
        Query<Integer> query = session.createQuery("SELECT MAX(d.deptno) " +
                "FROM DeptEntity d", Integer.class);
        Integer maxDeptno = query.uniqueResult();
        return (maxDeptno == null ? 1 : maxDeptno + 1);
    }

}
