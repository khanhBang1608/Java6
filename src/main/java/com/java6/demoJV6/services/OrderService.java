package com.java6.demoJV6.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java6.demoJV6.dto.OrderDetailDTO;
import com.java6.demoJV6.dto.OrderItemDTO;
import com.java6.demoJV6.entity.OrderEntity;
import com.java6.demoJV6.jpa.OrderJPA;

@Service
public class OrderService {
	
	@Autowired
	OrderJPA orderJPA;
    
    
    public OrderDetailDTO getOrderDetail(Integer orderId) {
        Optional<OrderEntity> optionalOrder = orderJPA.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new RuntimeException("Không tìm thấy đơn hàng với ID: " + orderId);
        }

        OrderEntity order = optionalOrder.get();

        OrderDetailDTO dto = new OrderDetailDTO();
        dto.setOrderId(order.getId());
        dto.setFullName(order.getUser().getName());
        dto.setOrderDate(order.getOrderDate());
        dto.setAddress(order.getAddress());
        dto.setStatus(order.getStatus());

        List<OrderItemDTO> items = order.getOrderDetails().stream().map(detail -> {
            OrderItemDTO itemDTO = new OrderItemDTO();
            itemDTO.setId(detail.getId());
            itemDTO.setProductName(detail.getProductSize().getProduct().getName());
            itemDTO.setSize(detail.getProductSize().getSize().getName());
            itemDTO.setPrice(detail.getPrice());
            itemDTO.setQuantity(detail.getQuantity());
            return itemDTO;
        }).collect(Collectors.toList());

        dto.setItems(items);

        return dto;
    }
    
}
