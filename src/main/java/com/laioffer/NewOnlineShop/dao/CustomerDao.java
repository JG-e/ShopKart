package com.laioffer.NewOnlineShop.dao;
import com.laioffer.NewOnlineShop.entity.Authorities;
import com.laioffer.NewOnlineShop.entity.Customer;
import com.laioffer.NewOnlineShop.entity.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDao {
    @Autowired
    private SessionFactory sessionFactory;

    // Add a new customer via Hibernate
    public void addCustomer(Customer customer) {
        Authorities authorities = new Authorities();
        authorities.setAuthorities("ROLE_USER");    // Set the authority to a user instead of admin
        authorities.setEmailId(customer.getUser().getEmailId());
        Session session = null;                     // Declare a session

        try {
            session = sessionFactory.openSession();         // open a session connecting to the database
            session.beginTransaction();                     // A transaction is to store everything we are about to
            // give it, such as authority and the customer
            session.save(authorities);
            session.save(customer);
            session.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback(); // in case anything went wrong
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    public Customer getCustomerByUserName(String userName) {
        User user = null;
        try (Session session = sessionFactory.openSession()) {
            // Query a user from the database session via Hibernate
            user = session.get(User.class, userName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if(user != null) {
            return user.getCustomer();
        }

        return null;
    }

}
