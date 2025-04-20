package com.java6.demoJV6.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.java6.demoJV6.dto.CheckoutRequest;
import com.java6.demoJV6.dto.OrderDTO;
import com.java6.demoJV6.dto.OrderDetailDTO;
import com.java6.demoJV6.entity.*;
import com.java6.demoJV6.jpa.*;
import com.java6.demoJV6.services.OrderService;

@RestController
@RequestMapping("/api/user/order")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") // thêm allowCredentials để gửi cookie
public class OrderController {

    @Autowired
    private OrderJPA orderJPA;

    @Autowired
    private OrderDetailJPA orderDetailJPA;

    @Autowired
    private ProductSizeJPA productSizeJPA;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserJPA userJPA;

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody CheckoutRequest request) {
        try {
            UserEntity user = userJPA.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            OrderEntity order = new OrderEntity();
            order.setUser(user);
            order.setOrderDate(LocalDateTime.now());
            order.setStatus(0);
            order.setAddress(request.getAddress());

            double totalAmount = 0.0;
            for (CheckoutRequest.Item item : request.getItems()) {
                ProductSizeEntity productSize = productSizeJPA.findById(item.getProductSizeId())
                        .orElseThrow(() -> new RuntimeException("Product size not found"));

                if (!productSize.getProduct().isStatus()) {
                    throw new RuntimeException("Sản phẩm đã ngưng hoạt động: " + productSize.getProduct().getName());
                }

                if (!productSize.getProduct().getCategory().isStatus()) {
                    throw new RuntimeException("Danh mục của sản phẩm đã bị ẩn: " + productSize.getProduct().getCategory().getName());
                }

                totalAmount += item.getQuantity() * productSize.getProduct().getPrice();
            }
            order.setTotalAmount(totalAmount);

            OrderEntity savedOrder = orderJPA.save(order);

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

    @PostMapping("/update-status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id, @RequestBody Map<String, Object> payload) {
        Optional<OrderEntity> optionalOrder = orderJPA.findById(id);
        if (optionalOrder.isPresent()) {
            OrderEntity order = optionalOrder.get();
            Integer newStatus = (Integer) payload.get("status");
            order.setStatus(newStatus);
            orderJPA.save(order);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
    }

    @GetMapping("/detail")
    public ResponseEntity<OrderDetailDTO> getOrderDetail(@RequestParam("orderId") Integer orderId) {
        return ResponseEntity.ok(orderService.getOrderDetail(orderId));
    }

    // ✅ Thêm API lấy danh sách đơn hàng dựa vào userId từ cookie
    @GetMapping("/list")
    public ResponseEntity<?> getOrdersFromCookie(HttpServletRequest request) {
        try {
            String userIdStr = null;
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("userId")) {
                    userIdStr = cookie.getValue();
                    break;
                }
            }

            if (userIdStr == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bạn chưa đăng nhập");
            }

            Integer userId = Integer.parseInt(userIdStr);
            List<OrderEntity> orders = orderJPA.findByUserIdOrderByOrderDateDesc(userId);

            // Chuyển đổi danh sách OrderEntity thành OrderDTO
            List<OrderDTO> orderDTOs = orders.stream().map(order -> {
                OrderDTO dto = new OrderDTO();
                dto.setOrderId(order.getId());
                dto.setOrderDate(order.getOrderDate());
                dto.setStatus(order.getStatus());
                dto.setTotalAmount(order.getTotalAmount());
                dto.setAddress(order.getAddress());
                dto.setUserId(order.getUser().getId()); // mặc dù userId không cần hiển thị nhưng vẫn cần cho API
                return dto;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(orderDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi: " + e.getMessage());
        }
    }


}
