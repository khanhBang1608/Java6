package com.java6.demoJV6.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartDetailDTO {
    private Integer id;
    private Integer productId;
    private String productName;
    private String productImage;
    private Integer quantity;
    private SizeDTO size;
    private int stock;
    private long price;
    private List<SizeDTO> availableSizes;

}
