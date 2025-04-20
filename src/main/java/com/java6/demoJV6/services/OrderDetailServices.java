package com.java6.demoJV6.services;

import com.java6.demoJV6.entity.OrderDetailEntity;
import com.java6.demoJV6.jpa.OrderDetailJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderDetailServices {

    private final OrderDetailJPA orderDetailJPA;

    public OrderDetailEntity findByUserAndProduct(Integer userId, Integer productId) {
        List<OrderDetailEntity> list = orderDetailJPA.findAllByUserAndProduct(userId, productId);
        return list.isEmpty() ? null : list.get(0);
    }

}
