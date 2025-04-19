package com.java6.demoJV6.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.java6.demoJV6.dto.OrderDTO;
import com.java6.demoJV6.dto.OrderDetailDTO;
import com.java6.demoJV6.entity.AddressEntity;
import com.java6.demoJV6.entity.OrderDetailEntity;
import com.java6.demoJV6.entity.OrderEntity;
import com.java6.demoJV6.entity.ProductSizeEntity;
import com.java6.demoJV6.jpa.OrderDetailJPA;
import com.java6.demoJV6.jpa.OrderJPA;
import com.java6.demoJV6.jpa.ProductSizeJPA;
import com.java6.demoJV6.services.OrderService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/order")
public class ManageOrderController {

    @Autowired
    private OrderJPA orderJPA;
    
    @Autowired
    private OrderDetailJPA orderDetailJPA;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private ProductSizeJPA productSizeJPA;

    

    @GetMapping
    public List<OrderDTO> getAllOrder() {
        List<OrderEntity> orders = orderJPA.findAll();
        return orders.stream().map(order -> {
            OrderDTO dto = new OrderDTO();
            dto.setOrderId(order.getId());
            dto.setFullName(order.getUser().getName());
            dto.setStatus(order.getStatus());
            dto.setOrderDate(order.getOrderDate());
            dto.setTotalAmount(order.getTotalAmount());
            dto.setAddress(order.getAddress());

            // Nếu address là 1 chuỗi lưu trực tiếp thì parse tạm,
            // Nếu cần địa chỉ đầy đủ: lấy từ bảng address
            if (order.getUser().getAddresses() != null && !order.getUser().getAddresses().isEmpty()) {
                AddressEntity firstAddress = order.getUser().getAddresses().get(0);
                dto.setCustomerName(firstAddress.getCustomerName());
                dto.setPhone(firstAddress.getPhone());
            }

            dto.setUserId(order.getUser().getId());
            return dto;
        }).collect(Collectors.toList());
    }
    
    @PostMapping("/update-status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id, @RequestBody Map<String, Object> payload) {
        Optional<OrderEntity> optionalOrder = orderJPA.findById(id);
        if (optionalOrder.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }

        OrderEntity order = optionalOrder.get();
        Integer newStatus = (Integer) payload.get("status");

        if (newStatus == 1) {
            for (OrderDetailEntity detail : order.getOrderDetails()) {
                ProductSizeEntity productSize = detail.getProductSize();
                int currentStock = productSize.getStock();
                int quantityOrdered = detail.getQuantity();

                if (currentStock < quantityOrdered) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Không đủ tồn kho cho sản phẩm: " + productSize.getProduct().getName() +
                                  " - Size: " + productSize.getSize().getName());
                }

                productSize.setStock(currentStock - quantityOrdered);
                productSizeJPA.save(productSize); // cập nhật tồn kho
            }
        }

        order.setStatus(newStatus);
        orderJPA.save(order);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/detail")
    public ResponseEntity<OrderDetailDTO> getOrderDetail(@RequestParam("orderId") Integer orderId) {
        return ResponseEntity.ok(orderService.getOrderDetail(orderId));
    }
}
