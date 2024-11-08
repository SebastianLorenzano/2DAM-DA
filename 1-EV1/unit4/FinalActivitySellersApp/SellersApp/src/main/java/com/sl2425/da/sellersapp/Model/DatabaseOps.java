package com.sl2425.da.sellersapp.Model;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import static com.sl2425.da.sellersapp.Model.LogProperties.logger;
import com.sl2425.da.sellersapp.Model.Entities.*;

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


    public SellerEntity checkLogin(String cif, String password) {
        SellerEntity seller = null;

        try (Session session = sessionFactory.openSession())
        {
            String sqlString = "FROM SellerEntity WHERE cif = :cif AND password = :password";

            Query<SellerEntity> query = session.createQuery(sqlString, SellerEntity.class);
            query.setParameter("cif", cif);
            query.setParameter("password", password);

            seller = query.uniqueResult();
        } catch (Exception e) {
            logger.severe("Error during login check: " + e.getMessage());
            e.printStackTrace();
        }

        return seller;
    }
}

