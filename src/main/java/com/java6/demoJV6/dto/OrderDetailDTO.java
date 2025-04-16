package com.java6.demoJV6.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class OrderDetailDTO {
    private Integer orderId;
    private String fullName;
    private LocalDateTime orderDate;
    private String address;
    private Integer status;
    private List<OrderItemDTO> items; 
}
