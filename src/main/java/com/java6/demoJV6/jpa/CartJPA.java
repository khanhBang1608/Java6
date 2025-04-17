package com.java6.demoJV6.jpa;

import com.java6.demoJV6.entity.CartDetailEntity;
import com.java6.demoJV6.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import com.java6.demoJV6.entity.CartEntity;

import java.util.Optional;

public interface CartJPA extends JpaRepository<CartEntity,Integer>{
    Optional<CartEntity> findByUser(UserEntity user);

    Optional<CartEntity> findByUserId(Integer cartId);
}
