package com.java6.demoJV6.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.java6.demoJV6.jpa.CategoryJPA;
import com.java6.demoJV6.jpa.ImageJPA;
import com.java6.demoJV6.jpa.ProductJPA;
import com.java6.demoJV6.services.ProductServices;

import jakarta.validation.Valid;

import com.java6.demoJV6.dto.CategoryDTO;
import com.java6.demoJV6.dto.ImageDTO;
import com.java6.demoJV6.dto.ProductDTO;
import com.java6.demoJV6.bean.ProductBean;
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
    
    @Autowired
    private ImageJPA imageJPA;

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
        return list.stream().map(product -> {
            ProductDTO dto = new ProductDTO();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setPrice(product.getPrice());
            dto.setStatus(product.isStatus());
            dto.setCategoryName(product.getCategory() != null ? product.getCategory().getName() : null);
            dto.setImageNames(product.getImages() != null ? 
                product.getImages().stream().map(image -> image.getName()).toList() : 
                new ArrayList<>()
            );
            return dto;
        }).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Integer id) {
        Optional<ProductEntity> product = productJPA.findById(id);
        if (product.isEmpty()) return ResponseEntity.notFound().build();

        ProductEntity p = product.get();
        ProductDTO dto = new ProductDTO();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        dto.setPrice(p.getPrice());
        dto.setStatus(p.isStatus());
        dto.setCategoryId(p.getCategory().getId());
        dto.setCategoryName(p.getCategory() != null ? p.getCategory().getName() : null);
        dto.setImageNames(p.getImages().stream().map(image -> image.getName()).toList());

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@Valid @ModelAttribute ProductBean productBean, BindingResult result) {
    	Map<String, String> errors = new HashMap<>();
        for (FieldError err : result.getFieldErrors()) {
            errors.put(err.getField(), err.getDefaultMessage());
        }
        
     
        String imageError = productBean.validateImageFiles();
        if (imageError != null) {
            errors.put("images", imageError);
        }

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        productService.createProduct(productBean);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") int id,
            @Valid @ModelAttribute ProductBean productBean, BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError err : result.getFieldErrors()) {
            errors.put(err.getField(), err.getDefaultMessage());
        }
        

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        productService.updateProduct(id, productBean);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/images")
    public List<ImageDTO> getAllImage(@RequestParam("productId") int productId) {
        return imageJPA.findAllImageByProductId(productId).stream().map(image -> {
            ImageDTO dto = new ImageDTO();
            dto.setId(image.getId());
            dto.setName(image.getName());
            return dto;
        }).toList();
    }

    @PostMapping("/image/delete")
    public ResponseEntity<?> deleteImage(@RequestParam("id") Integer id) {
        if (id == null) {
            return ResponseEntity.badRequest().body("Missing id parameter");
        }

        try {
            // Xóa ảnh theo ID
            imageJPA.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi xóa ảnh");
        }
    }
}
