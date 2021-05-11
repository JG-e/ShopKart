package com.laioffer.NewOnlineShop.service;

import com.laioffer.NewOnlineShop.dao.CustomerDao;
import com.laioffer.NewOnlineShop.entity.Cart;
import com.laioffer.NewOnlineShop.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public void addCustomer(Customer customer) {
        customer.getUser().setEnabled(true);    // Required by a framework

        Cart cart = new Cart();
        customer.setCart(cart);

        customerDao.addCustomer(customer);         // the cart is cascaded
    }

    public Customer getCustomerByUserName(String userName) {
        return customerDao.getCustomerByUserName(userName);     // calling Dao to access the database
    }
}
