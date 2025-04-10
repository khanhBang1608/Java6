package com.java6.demoJV6.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Category")
public class CategoryEntity {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="category_id")
    private int id;

    @Column(name="category_name" , nullable = false, columnDefinition = "NVARCHAR(100)")
    private String name;

    @Column(name = "status", nullable = false)
    private boolean status = true;

}
