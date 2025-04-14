package com.java6.demoJV6.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.java6.demoJV6.jpa.CategoryJPA;
import com.java6.demoJV6.jpa.ProductJPA;
import com.java6.demoJV6.services.ProductServices;

import jakarta.validation.Valid;

import com.java6.demoJV6.bean.ProductBean;
import com.java6.demoJV6.dto.CategoryDTO;
import com.java6.demoJV6.dto.ProductDTO;
import com.java6.demoJV6.entity.CategoryEntity;
import com.java6.demoJV6.entity.ImageEntity;
import com.java6.demoJV6.entity.ProductEntity;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductServices productService;
    
    @Autowired
    private ProductJPA productJPA;
    
    @Autowired
    private CategoryJPA categoryJPA;
    
    @GetMapping("/categories")
    public List<CategoryDTO> getAllCategories() {
    	return categoryJPA.findAll().stream().map(category -> {
    		CategoryDTO dto = new CategoryDTO();
    		dto.setId(category.getId());
    		dto.setName(category.getName());
    		dto.setStatus(category.isStatus());
    		return dto;
    	}).toList();
        
    }
    @GetMapping("/products")
    public List<ProductDTO> getAllProducts() {
        List<ProductEntity> list = productJPA.findAll();
        System.out.println("Tổng sản phẩm: " + list.size());  // thêm log này

        return list.stream().map(product -> {
            ProductDTO dto = new ProductDTO();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setPrice(product.getPrice());
            dto.setStatus(product.isStatus());
            dto.setCategoryName(product.getCategory() != null ? product.getCategory().getName() : null);
            dto.setImageNames(product.getImages() != null ?
                product.getImages().stream().map(ImageEntity::getName).toList() :
                new ArrayList<>()
            );
            return dto;
        }).toList();
    }



    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@Valid @ModelAttribute ProductBean productBean, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError err : result.getFieldErrors()) {
                errors.put(err.getField(), err.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        productService.createProduct(productBean);
        return ResponseEntity.ok().build();
    }
//    @PostMapping("/update/{id}")
//    public ResponseEntity<?> updateProduct(
//            @PathVariable("id") Integer id,
//            @Valid @ModelAttribute ProductBean productBean,
//            BindingResult result) {
//
//        if (result.hasErrors()) {
//            Map<String, String> errors = new HashMap<>();
//            result.getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
//            return ResponseEntity.badRequest().body(errors);
//        }
//
//        productService.updateProduct(id, productBean);
//        return ResponseEntity.ok().build();
//    }

}
