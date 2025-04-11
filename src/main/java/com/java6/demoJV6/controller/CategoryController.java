package com.java6.demoJV6.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java6.demoJV6.bean.CategoryBean;
import com.java6.demoJV6.entity.CategoryEntity;
import com.java6.demoJV6.services.CategoryServices;
import java.util.Map;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/category")
public class CategoryController {
	@Autowired
	private CategoryServices categoryServices;

	// Lấy tất cả danh mục
	@GetMapping("/list")
	public ResponseEntity<List<CategoryEntity>> getCategories() {
		List<CategoryEntity> categories = (List<CategoryEntity>) categoryServices.getAllCategories();
		return ResponseEntity.ok(categories);
	}

	@PostMapping("/add")
	public ResponseEntity<?> addCategory(@Valid @RequestBody CategoryBean categoryBean, BindingResult result) {
		if (result.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			for (FieldError err : result.getFieldErrors()) {
				errors.put(err.getField(), err.getDefaultMessage());
			}
			return ResponseEntity.badRequest().body(errors);
		}

		categoryServices.createCategory(categoryBean);
		return ResponseEntity.ok().build();
	}
	
	// Cập nhật danh mục
    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Integer id, 
                                            @Valid @RequestBody CategoryBean categoryBean, 
                                            BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError err : result.getFieldErrors()) {
                errors.put(err.getField(), err.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        categoryServices.updateCategory(id, categoryBean);
        return ResponseEntity.ok().build();
    }
}
