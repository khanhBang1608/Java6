package com.java6.demoJV6.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java6.demoJV6.entity.ProductSizeEntity;

import java.util.List;
import java.util.Optional;

public interface ProductSizeJPA extends JpaRepository<ProductSizeEntity, Integer> {
    List<ProductSizeEntity> findByProductId(Integer productId);
    Optional<ProductSizeEntity> findByProduct_IdAndSize_Id(Integer productId, Integer sizeId);


}
