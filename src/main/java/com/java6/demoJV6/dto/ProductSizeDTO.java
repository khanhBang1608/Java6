package com.java6.demoJV6.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductSizeDTO {
    private int id;
    private String sizeName;
    private int stock;
}
