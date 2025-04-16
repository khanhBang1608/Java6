package com.java6.demoJV6.services;

import com.java6.demoJV6.dto.OrderDetailDTO;

public interface OrderService {
    OrderDetailDTO getOrderDetail(Integer orderId);
}
