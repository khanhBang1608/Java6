package com.java6.demoJV6.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailDTO {
	private int id;
	private CartDTO cart;
	private ProductDTO product;
	private ProductSizeDTO productSize;
	private int quantity;
	private CategoryDTO category;
}
