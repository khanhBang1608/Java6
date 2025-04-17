package com.java6.demoJV6.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartDTO {
    private Integer id;
    private Integer userId;
    private List<CartDetailDTO> cartDetails;

}

