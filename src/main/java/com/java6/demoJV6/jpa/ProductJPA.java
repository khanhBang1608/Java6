package com.java6.demoJV6.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java6.demoJV6.entity.ProductEntity;

public interface ProductJPA extends JpaRepository<ProductEntity, Integer> {

}
