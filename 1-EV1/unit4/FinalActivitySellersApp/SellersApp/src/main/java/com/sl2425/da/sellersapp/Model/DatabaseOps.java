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
            temp = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        }
        catch (Exception e)
        {
            logger.severe("Failed to create SessionFactory: " + e.getMessage());
        }
        sessionFactory = temp;
    }

    private static Session openSession() throws Exception
    {

        Session session = sessionFactory.openSession();
        if (session == null)
            logger.severe("SessionFactory is null. Please check your Hibernate configuration file (hibernate.cfg.xml) and ensure it is correctly placed in the classpath.");
        return session;
    }


    public static SellerEntity SelectSellerWithCifAndPassword(String cif, String password)
    {
        SellerEntity seller = null;
        if (cif == null || cif.isEmpty() || password == null || password.isEmpty())
            return null;
        try (Session session = sessionFactory.openSession())
        {
            System.out.println("Checking login...");
            Query<SellerEntity> query = session.createQuery(
                    "from SellerEntity where cif = :cif and password = :password", SellerEntity.class);
            query.setParameter("cif", cif);
            query.setParameter("password", password);
            seller = query.uniqueResult();
            System.out.println("Seller: " + seller.toString());
        }
        catch (Exception e)
        {
            logger.severe("Error during login check: " + e.getMessage());
            e.printStackTrace();
        }
        return seller;
    }
}

