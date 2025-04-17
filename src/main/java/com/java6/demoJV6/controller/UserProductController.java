package com.java6.demoJV6.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.java6.demoJV6.jpa.CategoryJPA;
import com.java6.demoJV6.jpa.ImageJPA;
import com.java6.demoJV6.jpa.ProductJPA;
import com.java6.demoJV6.jpa.ProductSizeJPA;
import com.java6.demoJV6.jpa.SizeJPA;
import com.java6.demoJV6.services.ProductServices;


import com.java6.demoJV6.dto.CategoryDTO;
import com.java6.demoJV6.dto.ProductDTO;
import com.java6.demoJV6.dto.ProductDetailDTO;
import com.java6.demoJV6.dto.ProductSizeDTO;
import com.java6.demoJV6.entity.CategoryEntity;
import com.java6.demoJV6.entity.ProductEntity;
import com.java6.demoJV6.entity.ProductSizeEntity;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class UserProductController {

    @Autowired
    private ProductServices productService;

    @Autowired
    private ProductJPA productJPA;

    @Autowired
    private CategoryJPA categoryJPA;
    
    @Autowired
    private ImageJPA imageJPA;
    
    @Autowired
    private SizeJPA sizeJPA;
    
    @Autowired
    private ProductSizeJPA productSizeJPA;

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
            
            CategoryEntity category = product.getCategory();
            if (category != null) {
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setId(category.getId());
                categoryDTO.setName(category.getName());
                categoryDTO.setStatus(category.isStatus());
                dto.setCategory(categoryDTO);
            }
            dto.setCategoryName(product.getCategory() != null ? product.getCategory().getName() : null);
            dto.setImageNames(product.getImages() != null ? 
                product.getImages().stream().map(image -> image.getName()).toList() : 
                new ArrayList<>()
            );
            return dto;
        }).toList();
    }

    @GetMapping("/productne/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Integer id) {
        Optional<ProductEntity> product = productJPA.findById(id);
        if (product.isEmpty()) return ResponseEntity.notFound().build();

        ProductEntity p = product.get();
        ProductDetailDTO dto = new ProductDetailDTO();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        dto.setPrice(p.getPrice());

        // Lấy danh sách tên ảnh
        dto.setImageNames(
            p.getImages().stream()
             .map(image -> image.getName())
             .toList()
        );

        // Lấy danh sách size và tồn kho
        List<ProductSizeDTO> sizeDTOs = p.getProductSizes().stream()
            .map(ps -> new ProductSizeDTO(
                ps.getSize().getId(),
                ps.getSize().getName(),
                ps.getStock()
            ))
            .toList();
        dto.setSizes(sizeDTOs);

        return ResponseEntity.ok(dto);
    }

    
    @GetMapping("/sizes")
    public ResponseEntity<?> getSizes(@RequestParam("productId") int productId) {
        List<ProductSizeEntity> list = productSizeJPA.findByProductId(productId);
        List<ProductSizeDTO> result = list.stream().map(productSize -> {
            ProductSizeDTO dto = new ProductSizeDTO();
            dto.setId(productSize.getId());
            dto.setSizeName(productSize.getSize().getName());
            dto.setStock(productSize.getStock());
            return dto;
        }).toList();

        return ResponseEntity.ok(result);
    }
}
