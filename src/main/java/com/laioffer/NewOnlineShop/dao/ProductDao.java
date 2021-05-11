package com.laioffer.NewOnlineShop.dao;

import com.laioffer.NewOnlineShop.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDao {
    @Autowired
    private SessionFactory sessionFactory;      // map the class to the database and creates sessions

    public void addProduct(Product product) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();             // Transactions to save the changes temporarily
            session.save(product);
            session.getTransaction().commit();      // commit changes using transactions
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();    // anything goes wrong we roll it back
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void deleteProduct(int productId) {      // ID from front end -- the press of the button and the request sent
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Product product = session.get(Product.class, productId);    // Find the corresponding object
            session.delete(product);           // delete the corresponding object, managed by hibernate
            session.getTransaction().commit();          // Commit changes
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    public void updateProduct(Product product) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }
    public Product getProductById(int productId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Product.class, productId);           // Handled by Hibernate
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<Product>();
        try (Session session = sessionFactory.openSession()) {
            products = session.createCriteria(Product.class).list();    //
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

}
