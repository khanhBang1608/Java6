package com.java6.demoJV6.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteDTO {

    private Integer id;              
    private Integer userId;          
    private Integer productId;       
    private String productName;      
    private String productImage;     
    private Double productPrice;     

    // Bạn có thể thêm các thuộc tính khác như số lượng, mô tả sản phẩm, v.v.
}
