package com.java6.demoJV6.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java6.demoJV6.entity.CartDetailEntity;
import com.java6.demoJV6.entity.CartEntity;
import com.java6.demoJV6.entity.ProductSizeEntity;

public interface CartDetailJPA extends JpaRepository<CartDetailEntity,Integer>{
	   Optional<CartDetailEntity> findByCartAndProductSize(CartEntity cart, ProductSizeEntity productSize);
}
