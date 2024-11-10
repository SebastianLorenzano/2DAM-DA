package com.sl2425.da.sellersapp.Model;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
        }
        catch (Exception e)
        {
            logger.severe("Error during login check: " + e.getMessage());
            e.printStackTrace();
        }
        return seller;
    }

    public static boolean updateSeller(SellerEntity updatedSeller) {
        if (sessionFactory == null) {
            logger.severe("SessionFactory is not initialized. Cannot proceed with updating seller.");
            return false;
        }

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            SellerEntity seller = session.get(SellerEntity.class, updatedSeller.getId());
            if (seller != null) {
                seller.setName(updatedSeller.getName());
                seller.setBusinessName(updatedSeller.getBusinessName());
                seller.setPhone(updatedSeller.getPhone());
                seller.setEmail(updatedSeller.getEmail());
                seller.setPlainPassword(updatedSeller.getPlainPassword());
                seller.setPassword(updatedSeller.getPassword());
                transaction.commit();
                System.out.println("Seller updated successfully.");
                return true;
            } else {
                System.out.println("Seller with ID " + updatedSeller.getId() + " not found.");
            }
        } catch (Exception e) {
            System.out.println("Error during seller update: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            logger.severe("Error during seller update: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}

