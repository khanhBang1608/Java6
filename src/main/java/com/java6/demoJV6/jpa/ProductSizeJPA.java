package com.java6.demoJV6.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java6.demoJV6.entity.ProductSizeEntity;

public interface ProductSizeJPA extends JpaRepository<ProductSizeEntity, Integer> {

}
