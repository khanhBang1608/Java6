package com.java6.demoJV6.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Integer id;
    private String productName;
    private String size;
    private Double price;
    private Integer quantity;
}
