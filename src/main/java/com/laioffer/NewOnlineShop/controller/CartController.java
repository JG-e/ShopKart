package com.laioffer.NewOnlineShop.controller;

import com.laioffer.NewOnlineShop.entity.Cart;
import com.laioffer.NewOnlineShop.entity.Customer;
import com.laioffer.NewOnlineShop.service.CartService;
import com.laioffer.NewOnlineShop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CartController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartService cartService;

    @RequestMapping(value = "/cart/getCartById", method = RequestMethod.GET)
    public ModelAndView getCartId(){
        ModelAndView modelAndView = new ModelAndView("cart");
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();       // Spring Security holds Security Context Holder
        String username = loggedInUser.getName();
        Customer customer = customerService.getCustomerByUserName(username);
        // Anything you want to show the page, you can add object that thing.
        modelAndView.addObject("cartId", customer.getCart().getId());

        return modelAndView; // Return a webpage with the corresponding data loaded
    }

    @RequestMapping(value = "/cart/getCart/{cartId}", method = RequestMethod.GET)
    @ResponseBody   // Serialize to JSON using this
    public Cart getCartItems(@PathVariable(value="cartId")int cartId){
        return cartService.getCartById(cartId);
    }

}
