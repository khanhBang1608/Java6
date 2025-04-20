package com.java6.demoJV6.controller;

import com.java6.demoJV6.dto.CartDetailDTO;
import com.java6.demoJV6.entity.CartDetailEntity;
import com.java6.demoJV6.entity.CartEntity;
import com.java6.demoJV6.entity.ProductSizeEntity;
import com.java6.demoJV6.entity.UserEntity;
import com.java6.demoJV6.jpa.UserJPA;
import com.java6.demoJV6.services.CartDetailService;
import com.java6.demoJV6.services.CartService;
import com.java6.demoJV6.services.ProductSizeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/user/cart")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartDetailService cartDetailService;
    
    @Autowired
    private UserJPA userRepository;
    
    @Autowired
    private ProductSizeService productSizeService;

    private UserEntity getUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(
            @RequestParam("cartId") Integer cartId,
            @RequestParam("productSizeId") Integer productSizeId,
            @RequestParam("quantity") int quantity) {

        Optional<CartEntity> optionalCart = cartService.findById(cartId);
        if (optionalCart.isEmpty()) {
            return ResponseEntity.badRequest().body("Không tìm thấy giỏ hàng với ID: " + cartId);
        }

        Optional<ProductSizeEntity> optionalProductSize = productSizeService.findById(productSizeId);
        if (optionalProductSize.isEmpty()) {
            return ResponseEntity.badRequest().body("Không tìm thấy sản phẩm theo size ID: " + productSizeId);
        }

        CartDetailEntity added = cartDetailService.addToCart(
                optionalCart.get(),
                optionalProductSize.get(),
                quantity
        );

        CartDetailDTO dto = cartDetailService.toDTO(added);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateQuantity(
            @RequestParam("cartId") Integer cartId,
            @RequestParam("productSizeId") Integer productSizeId,
            @RequestParam("quantity") int quantity) {

        Optional<CartEntity> optionalCart = cartService.findById(cartId);
        if (optionalCart.isEmpty()) {
            return ResponseEntity.badRequest().body("Không tìm thấy giỏ hàng");
        }

        Optional<ProductSizeEntity> optionalProductSize = productSizeService.findById(productSizeId);
        if (optionalProductSize.isEmpty()) {
            return ResponseEntity.badRequest().body("Không tìm thấy sản phẩm size");
        }

        CartDetailEntity updated = cartDetailService.updateQuantity(optionalCart.get(), optionalProductSize.get(), quantity);
        if (updated == null) {
            return ResponseEntity.badRequest().body("Không tồn tại sản phẩm trong giỏ để cập nhật");
        }

        return ResponseEntity.ok(cartDetailService.toDTO(updated));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCartItem(
            @RequestParam("cartId") Integer cartId,
            @RequestParam("productSizeId") Integer productSizeId) {

        Optional<CartEntity> optionalCart = cartService.findById(cartId);
        if (optionalCart.isEmpty()) {
            return ResponseEntity.badRequest().body("Không tìm thấy giỏ hàng");
        }

        Optional<ProductSizeEntity> optionalProductSize = productSizeService.findById(productSizeId);
        if (optionalProductSize.isEmpty()) {
            return ResponseEntity.badRequest().body("Không tìm thấy sản phẩm size");
        }

        boolean deleted = cartDetailService.deleteByProductSize(optionalCart.get(), optionalProductSize.get());
        if (!deleted) {
            return ResponseEntity.badRequest().body("Không tìm thấy sản phẩm trong giỏ để xoá");
        }

        return ResponseEntity.ok("Đã xoá sản phẩm khỏi giỏ");
    }

    @GetMapping("/view")
    public ResponseEntity<?> viewCart(@RequestParam("cartId") Integer cartId) {
        List<CartDetailDTO> list = cartDetailService.getCartDetails(cartId);
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(@RequestParam("cartId") Integer cartId) {
        boolean cleared = cartDetailService.clearCart(cartId);
        if (!cleared) {
            return ResponseEntity.badRequest().body("Giỏ hàng đã trống hoặc không tồn tại");
        }
        return ResponseEntity.ok("Đã xoá toàn bộ giỏ hàng");
    }
}
