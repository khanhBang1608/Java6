package com.java6.demoJV6.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java6.demoJV6.entity.CartEntity;
import com.java6.demoJV6.entity.UserEntity;
import com.java6.demoJV6.jpa.CartJPA;

@Service
public class CartService {

    @Autowired
    private CartJPA cartJPA;

    public Optional<CartEntity> findById(Integer id) {
        return cartJPA.findById(id);
    }

    public CartEntity save(CartEntity cart) {
        return cartJPA.save(cart);
    }

    public void deleteById(Integer id) {
        cartJPA.deleteById(id);
    }

    public CartEntity getCartByUser(UserEntity user) {
        for (CartEntity cart : cartJPA.findAll()) {
            if (cart.getUser().getId().equals(user.getId())) {
                return cart;
            }
        }
        return null;
    }
}
