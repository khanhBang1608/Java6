package com.java6.demoJV6.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTO {
	private int id;
    private String name;
    private String description;
    private long price;
    private Boolean status;
    private String categoryName;
    private List<String> imageNames;
}
