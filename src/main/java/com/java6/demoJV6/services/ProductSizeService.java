package com.java6.demoJV6.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java6.demoJV6.entity.ProductSizeEntity;
import com.java6.demoJV6.jpa.ProductSizeJPA;

@Service
public class ProductSizeService {

    @Autowired
    private ProductSizeJPA productSizeJPA;

    public Optional<ProductSizeEntity> findById(Integer id) {
        return productSizeJPA.findById(id);
    }
}
