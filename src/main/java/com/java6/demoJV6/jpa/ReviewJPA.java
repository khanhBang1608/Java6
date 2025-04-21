package com.java6.demoJV6.jpa;

import com.java6.demoJV6.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewJPA extends JpaRepository<ReviewEntity, Long> {
    @Query("SELECT r FROM ReviewEntity r " +
            "JOIN FETCH r.orderDetail od " +
            "JOIN FETCH od.productSize ps " +
            "JOIN FETCH ps.product p " +
            "LEFT JOIN FETCH od.order o " +     // Dùng LEFT JOIN
            "LEFT JOIN FETCH o.user u " +
            "WHERE p.id = :productId")
    List<ReviewEntity> findByProductId(@Param("productId") int productId);

    boolean existsByOrderDetail_Order_User_IdAndOrderDetail_ProductSize_Product_Id(Integer userId, Integer productId);
    
    
    boolean existsByOrderDetail_Id(int orderDetailId);
}

