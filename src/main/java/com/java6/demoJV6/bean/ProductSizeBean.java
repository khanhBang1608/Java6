package com.java6.demoJV6.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductSizeBean {
	private int productId;
    private int sizeId;
    private int stock;
}
