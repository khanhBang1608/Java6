package com.java6.demoJV6.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.java6.demoJV6.bean.ProductBean;
import com.java6.demoJV6.entity.CategoryEntity;
import com.java6.demoJV6.entity.ImageEntity;
import com.java6.demoJV6.entity.ProductEntity;
import com.java6.demoJV6.jpa.CategoryJPA;
import com.java6.demoJV6.jpa.ImageJPA;
import com.java6.demoJV6.jpa.ProductJPA;


@Service
public class ProductServices {

    @Autowired
    private ProductJPA productJPA;

    @Autowired
    private CategoryJPA categoryJPA;
    
    @Autowired
    private ImageJPA imageJPA;

    public void createProduct(ProductBean bean) {
    	 ProductEntity entity = new ProductEntity();

         entity.setName(bean.getName());
         entity.setDescription(bean.getDescription());
         entity.setPrice(bean.getPrice());
         entity.setStatus(bean.getStatus());

         CategoryEntity category = categoryJPA.findById(bean.getCategoryId()).orElse(null);
         entity.setCategory(category);

         productJPA.save(entity);

         List<ImageEntity> images = new ArrayList<>();
         for (MultipartFile file : bean.getImages()) {
             ImageEntity img = new ImageEntity();
             img.setProduct(entity);
             img.setName(file.getOriginalFilename()); 
             images.add(img);
         }

         imageJPA.saveAll(images);
     }
 }

