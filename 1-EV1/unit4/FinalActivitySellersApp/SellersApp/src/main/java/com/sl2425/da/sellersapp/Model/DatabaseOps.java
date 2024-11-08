package com.sl2425.da.sellersapp.Model;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
public class DatabaseOps
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

    private static Session openSession() throws Exception
    {

        Session session = sessionFactory.openSession();
        if (session == null)
            throw new Exception("Error opening session!");
        return session;
    }

}
