package com.java6.demoJV6.jpa;
import com.java6.demoJV6.entity.CartDetailEntity;
import com.java6.demoJV6.entity.CartEntity;
import com.java6.demoJV6.entity.ProductSizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartDetailJPA extends JpaRepository<CartDetailEntity, Integer> {
    List<CartDetailEntity> findByCart(CartEntity cart);
    void deleteByCartAndProductSizeId(CartEntity cart, Integer productSizeId);
    void deleteAllByCart(CartEntity cart);
    Optional<CartDetailEntity> findByCartAndProductSize(CartEntity cart, ProductSizeEntity productSize);
}
