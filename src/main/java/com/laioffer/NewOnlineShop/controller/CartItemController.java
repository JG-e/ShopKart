package com.laioffer.NewOnlineShop.controller;

import com.laioffer.NewOnlineShop.entity.Cart;
import com.laioffer.NewOnlineShop.entity.CartItem;
import com.laioffer.NewOnlineShop.entity.Customer;
import com.laioffer.NewOnlineShop.entity.Product;
import com.laioffer.NewOnlineShop.service.CartItemService;
import com.laioffer.NewOnlineShop.service.CartService;
import com.laioffer.NewOnlineShop.service.CustomerService;
import com.laioffer.NewOnlineShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
public class CartItemController {
    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @RequestMapping("/cart/add/{productId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addCartItem(@PathVariable(value = "productId") int productId) {
        // On the webpage, an add Item to Cart click event has happened.
        // There is only the product that is passed into this function
        // So we need to find the current cart
        // So we need to find the current logged in user
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        // We will need the username to find the Customer instance to find the cart
        String username = loggedInUser.getName();
        // get the customer instance
        Customer customer = customerService.getCustomerByUserName(username);
        // This is pretty straight forward
        Cart cart = customer.getCart();
        List<CartItem> cartItems = cart.getCartItem();
        Product product = productService.getProductById(productId);
        for (int i = 0; i < cartItems.size(); i++) {
            // for all the items, if we have the product in the cart, we add the number of item by 1
            // and increment the price by the its price.
            CartItem cartItem = cartItems.get(i);
            if (product.getId() == (cartItem.getProduct().getId())) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cartItem.setPrice(cartItem.getQuantity() * cartItem.getProduct().getProductPrice());
                cartItemService.addCartItem(cartItem);
                return;
            }
        }
        // Otherwise, we create a new one in the cart
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(1);
        cartItem.setProduct(product);
        cartItem.setPrice(product.getProductPrice());
        cartItem.setCart(cart);
        cartItemService.addCartItem(cartItem);
    }

    @RequestMapping(value = "/cart/removeCartItem/{cartItemId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeCartItem(@PathVariable(value = "cartItemId") int cartItemId) {
        cartItemService.removeCartItem(cartItemId);
    }

    @RequestMapping(value = "/cart/removeAllItems/{cartId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeAllCartItems(@PathVariable(value = "cartId") int cartId) {
        Cart cart = cartService.getCartById(cartId);
        cartItemService.removeAllCartItems(cart);
    }


    }
