package com.java6.demoJV6.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.java6.demoJV6.dto.CheckoutRequest;
import com.java6.demoJV6.entity.*;
import com.java6.demoJV6.jpa.*;

@RestController
@RequestMapping("/api/user/order")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderJPA orderJPA;

    @Autowired
    private OrderDetailJPA orderDetailJPA;

    @Autowired
    private ProductSizeJPA productSizeJPA;

    @Autowired
    private UserJPA userJPA;

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody CheckoutRequest request) {
        try {
            // Lấy user từ ID
            UserEntity user = userJPA.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

            // Tạo đơn hàng
            OrderEntity order = new OrderEntity();
            order.setUser(user);
            order.setOrderDate(LocalDateTime.now());
            order.setStatus(0); // Mặc định: 0 = đang xử lý
            order.setAddress(request.getAddress());

         // Tính tổng tiền
            double totalAmount = 0.0;
            for (CheckoutRequest.Item item : request.getItems()) {
                ProductSizeEntity productSize = productSizeJPA.findById(item.getProductSizeId())
                        .orElseThrow(() -> new RuntimeException("Product size not found"));

                // Kiểm tra trạng thái sản phẩm và danh mục
                if (!productSize.getProduct().isStatus()) {
                    throw new RuntimeException("Sản phẩm đã ngưng hoạt động: " + productSize.getProduct().getName());
                }

                if (!productSize.getProduct().getCategory().isStatus()) {
                    throw new RuntimeException("Danh mục của sản phẩm đã bị ẩn: " + productSize.getProduct().getCategory().getName());
                }

                totalAmount += item.getQuantity() * productSize.getProduct().getPrice();
            }
            order.setTotalAmount(totalAmount);

            // Lưu đơn hàng
            OrderEntity savedOrder = orderJPA.save(order);

            // Lưu từng chi tiết đơn hàng
            for (CheckoutRequest.Item item : request.getItems()) {
                ProductSizeEntity productSize = productSizeJPA.findById(item.getProductSizeId()).orElseThrow();

                OrderDetailEntity detail = new OrderDetailEntity();
                detail.setOrder(savedOrder);
                detail.setProductSize(productSize);
                detail.setQuantity(item.getQuantity());
                detail.setPrice(Double.valueOf(productSize.getProduct().getPrice()));

                orderDetailJPA.save(detail);
            }

            return ResponseEntity.ok("Đặt hàng thành công!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Đặt hàng thất bại: " + e.getMessage());
        }
    }
}
