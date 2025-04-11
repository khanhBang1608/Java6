package com.java6.demoJV6.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java6.demoJV6.bean.CategoryBean;
import com.java6.demoJV6.entity.CategoryEntity;
import com.java6.demoJV6.jpa.CategoryJPA;

@Service
public class CategoryServices {
	@Autowired
	CategoryJPA categoryJPA;

	// Tạo mới danh mục
	public void createCategory(CategoryBean bean) throws IllegalArgumentException {
	    // Kiểm tra trùng tên khi tạo mới
	    Optional<CategoryEntity> existingCategory = categoryJPA.findByName(bean.getName());
	    if (existingCategory.isPresent()) {
	        throw new IllegalArgumentException("Tên danh mục đã tồn tại");
	    }
	    
	    // Nếu không trùng tên, tạo mới danh mục
	    CategoryEntity entity = new CategoryEntity();
	    entity.setName(bean.getName());
	    entity.setStatus(bean.getStatus());

	    categoryJPA.save(entity);
	}

	// Cập nhật danh mục
	public void updateCategory(Integer id, CategoryBean bean) throws IllegalArgumentException {
	    Optional<CategoryEntity> existingCategory = categoryJPA.findById(id);

	    if (existingCategory.isPresent()) {
	        // Kiểm tra trùng tên khi cập nhật
	        Optional<CategoryEntity> existingCategoryByName = categoryJPA.findByName(bean.getName());

	        // Kiểm tra nếu tồn tại danh mục với tên trùng và không phải là danh mục hiện
	        if (existingCategoryByName.isPresent()) {
	            CategoryEntity categoryByName = existingCategoryByName.get();

	            // Kiểm tra ID không trùng với danh mục hiện tại
	            if (categoryByName.getId() != id) {
	                throw new IllegalArgumentException("Tên danh mục đã tồn tại");
	            }
	        }

	        CategoryEntity entity = existingCategory.get();
	        entity.setName(bean.getName());
	        entity.setStatus(bean.getStatus());

	        categoryJPA.save(entity);
	    } else {
	        throw new IllegalArgumentException("Danh mục không tồn tại");
	    }
	}


	// Lấy một danh mục theo ID
	public CategoryEntity getCategoryById(Integer id) {
		return categoryJPA.findById(id).orElse(null);
	}

}
