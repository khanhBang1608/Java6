package com.java6.demoJV6.jpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java6.demoJV6.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderDetailJPA extends JpaRepository<OrderDetailEntity,Integer>{
    @Query("SELECT od FROM OrderDetailEntity od WHERE od.order.user.id = :userId AND od.productSize.product.id = :productId")
    List<OrderDetailEntity> findAllByUserAndProduct(@Param("userId") Integer userId, @Param("productId") Integer productId);


    @Query("SELECT COUNT(od) > 0 FROM OrderDetailEntity od "+
    "WHERE od.order.user.id = :userId " +
    "AND od.productSize.product.id = :productId")
    boolean hasUserPurchasedProductByProductId(@Param("userId") Integer userId,
                                               @Param("productId") Integer productId);
}
