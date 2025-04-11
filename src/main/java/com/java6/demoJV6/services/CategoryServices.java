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
	public void createCategory(CategoryBean bean) {
		CategoryEntity entity = new CategoryEntity();

		entity.setName(bean.getName());
		entity.setStatus(bean.getStatus());

		categoryJPA.save(entity);
	}

	// Lấy danh sách danh mục
	public Iterable<CategoryEntity> getAllCategories() {
		return categoryJPA.findAll();
	}

	// Cập nhật danh mục
	public void updateCategory(Integer id, CategoryBean bean) {
		Optional<CategoryEntity> existingCategory = categoryJPA.findById(id);

		if (existingCategory.isPresent()) {
			CategoryEntity entity = existingCategory.get();

			entity.setName(bean.getName());
			entity.setStatus(bean.getStatus());

			categoryJPA.save(entity);
		}
	}

	// Xóa danh mục
	public void deleteCategory(Integer id) {
		Optional<CategoryEntity> existingCategory = categoryJPA.findById(id);

		if (existingCategory.isPresent()) {
			categoryJPA.delete(existingCategory.get());
		}
	}

	// Lấy một danh mục theo ID
	public CategoryEntity getCategoryById(Integer id) {
		return categoryJPA.findById(id).orElse(null);
	}

}
