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
    
    @Autowired
    ImageServices imageServices;

    public void createProduct(ProductBean bean) {
    	 ProductEntity entity = new ProductEntity();

         entity.setName(bean.getName());
         entity.setDescription(bean.getDescription());
         entity.setPrice(bean.getPrice());
         entity.setStatus(bean.getStatus());

         Optional<CategoryEntity> category = categoryJPA.findById(bean.getCategoryId());
         entity.setCategory(category.get());

         productJPA.save(entity);
         
         List<String> fileNames = imageServices.saveImages(bean.getImages());

         List<ImageEntity> images = new ArrayList<>();
         for (String filename : fileNames) {
             ImageEntity img = new ImageEntity();
             img.setProduct(entity);
             img.setName(filename); 
             images.add(img);
         }

         imageJPA.saveAll(images);
     }
 }

