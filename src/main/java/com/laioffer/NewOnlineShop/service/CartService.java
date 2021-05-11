package com.laioffer.NewOnlineShop.service;

import com.laioffer.NewOnlineShop.dao.CartDao;
import com.laioffer.NewOnlineShop.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartDao cartDao;

    public Cart getCartById(int cartId) {
        return cartDao.getCartById(cartId);
    }

}
