package com.java6.demoJV6.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.java6.demoJV6.jpa.CategoryJPA;
import com.java6.demoJV6.services.ProductServices;

import jakarta.validation.Valid;

import com.java6.demoJV6.bean.ProductBean;
import com.java6.demoJV6.entity.CategoryEntity;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductServices productService;
    @Autowired
    private CategoryJPA categoryJPA;
    
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryEntity>> getCategories() {
    	
        return ResponseEntity.ok(categoryJPA.findAll());
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
