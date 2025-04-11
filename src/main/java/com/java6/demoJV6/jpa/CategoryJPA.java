package com.java6.demoJV6.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java6.demoJV6.entity.CategoryEntity;

public interface CategoryJPA extends JpaRepository<CategoryEntity, Integer> {
    Optional<CategoryEntity> findByName(String name); // Phương thức tìm theo tên
}
