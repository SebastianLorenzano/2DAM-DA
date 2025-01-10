package com.sl2425.da.sellersapp.Model.Database;
import com.sl2425.da.sellersapp.Model.Utils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import static com.sl2425.da.sellersapp.Model.LogProperties.logger;
import com.sl2425.da.sellersapp.Model.Entities.*;

import java.math.BigDecimal;
import java.util.List;

public class HibernateDBManager extends DatabaseManager
{

    public HibernateDBManager()
    {
        init();   // Initialize Session Factory so it doesn't have a delay when the user logs in
    }

    public final  SessionFactory sessionFactory;
    {
        SessionFactory temp = null;
        try
        {
            temp = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        }
        catch (Exception e)
        {
            Utils.logException("Error during SessionFactory creation: ", e);
        }
        sessionFactory = temp;
    }

    public void init()
    {
        if (sessionFactory == null)
        {
            logger.severe("SessionFactory is null. Cannot proceed with initialization.");
            return;
        }
        try (Session session = openSession())
        {
            logger.info("Database connection established.");
        }
        catch (Exception e)
        {
            Utils.logException("Error during database connection: ", e);
        }
    }

    private Session openSession() throws Exception
    {
        Session session = sessionFactory.openSession();
        if (session == null)
            throw new Exception("SessionFactory is null. Please check your Hibernate configuration file " +
                    "(hibernate.cfg.xml) and ensure it is correctly placed in the classpath.");
        return session;
    }


    public SellerEntity SelectSellerWithCifAndPassword(String cif, String password)
    {
        SellerEntity seller = null;
        if (cif == null || cif.isEmpty() || password == null || password.isEmpty())
            return null;
        try (Session session = openSession())
        {
            Query<SellerEntity> query = session.createQuery(
                    "from SellerEntity where cif = :cif and password = :password", SellerEntity.class);
            query.setParameter("cif", cif);
            query.setParameter("password", password);
            seller = query.uniqueResult();
        }
        catch (Exception e)
        {
            Utils.logException("Error during login check: ", e);
        }
        return seller;
    }

    public boolean updateSeller(SellerEntity updatedSeller)
    {
        if (sessionFactory == null)
        {
            logger.severe("SessionFactory is not initialized. Cannot proceed with updating seller.");
            return false;
        }

        Transaction transaction = null;
        try (Session session = openSession())
        {
            transaction = session.beginTransaction();

            SellerEntity seller = session.get(SellerEntity.class, updatedSeller.getId());
            if (seller != null)
            {
                seller.setName(updatedSeller.getName());
                seller.setBusinessName(updatedSeller.getBusinessName());
                seller.setPhone(updatedSeller.getPhone());
                seller.setEmail(updatedSeller.getEmail());
                seller.setPlainPassword(updatedSeller.getPlainPassword());
                seller.setPassword(updatedSeller.getPassword());
                seller.setUrl(updatedSeller.getUrl());
                transaction.commit();
                logger.info("Seller with ID " + updatedSeller.getId() + " updated successfully.");
                return true;
            }
            else
            {
                logger.info("Seller with ID " + updatedSeller.getId() + " not found.");
            }
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
            {
                transaction.rollback();
            }
            Utils.logException("Error during seller update: ", e);
        }
        return false;
    }

    public List<CategoryEntity> SelectCategories()
    {
        List<CategoryEntity> result = null;
        try (Session session = openSession())
        {
            Query<CategoryEntity> query = session.createQuery("from CategoryEntity", CategoryEntity.class);
            result = query.getResultList();
        }
        catch (Exception e)
        {
            logger.severe("Error during category selection: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public List<SellerProductEntity> SelectSellerProducts(SellerEntity seller)
    {
        List<SellerProductEntity> result = null;
        if (seller == null)
            return result;
        try (Session session = openSession())
        {
            Query<SellerProductEntity> query = session.createQuery(
                    "from SellerProductEntity where seller = :seller", SellerProductEntity.class);
            query.setParameter("seller", seller);
            result = query.getResultList();
        }
        catch (Exception e)
        {
            logger.severe("Error during product selection: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public List<ProductEntity> SelectAvailableProducts(SellerEntity seller, CategoryEntity category) // CHECK
    {
        List<ProductEntity> result = null;
        if (seller == null || category == null)
            return result;
        try (Session session = openSession())
        {
            Query<ProductEntity> query = session.createNativeQuery(
                            "SELECT * FROM select_available_products_sl2425(:sellerId, :categoryId)",
                            ProductEntity.class)
                    .setParameter("sellerId", seller.getId())
                    .setParameter("categoryId", category.getId());
            result = query.getResultList();
        }
        catch (Exception e)
        {
            logger.severe("Error during product selection: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public boolean InsertSellerProduct(SellerEntity seller, ProductEntity product, BigDecimal price, int stock)
    {
        if (seller == null || product == null || price.compareTo(BigDecimal.ZERO) <= 0)
            return false;
        Transaction transaction = null;
        try (Session session = openSession())
        {
            transaction = session.beginTransaction();
            SellerProductEntity sellerProduct = new SellerProductEntity(seller, product, price, stock);
            session.save(sellerProduct);
            transaction.commit();
            return true;
        } catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();
            logger.severe("Error during product insertion: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean AddOffer(SellerProductEntity updatedSellerProduct)
    {
        if (updatedSellerProduct == null)
            return false;
        Transaction transaction = null;
        try (Session session = openSession())
        {
            transaction = session.beginTransaction();
            SellerProductEntity sellerProduct = session.get(SellerProductEntity.class, updatedSellerProduct.getId());
            sellerProduct.setOfferPrice(updatedSellerProduct.getOfferPrice());
            sellerProduct.setOfferStartDate(updatedSellerProduct.getOfferStartDate());
            sellerProduct.setOfferEndDate(updatedSellerProduct.getOfferEndDate());
            session.save(sellerProduct);
            transaction.commit();
            return true;
        } catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
                transaction.rollback();
            logger.severe("Error during offer addition: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}


