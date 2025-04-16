package com.java6.demoJV6.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private Integer id;
    private String customerName;
    private String phone;
    private String address;
    private String userId; // hoặc userId
}
