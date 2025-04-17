package com.java6.demoJV6.controller;

import com.java6.demoJV6.dto.CartDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/cart")
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
    @PostMapping("/user/add")
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

        CartDetailDTO dto = cartDetailService.toDTO(added); // chuyển sang DTO để trả ra client

        return ResponseEntity.ok(dto);
    }



//    @GetMapping("/items")
//    public ResponseEntity<?> getCartItems(@CookieValue(value = "token") String token) {
//        try {
//            Integer userId = 
//            CartDTO cart = cartService.getCartByUserId(userId);
//            return ResponseEntity.ok(cart);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
//        }
//    }


//    @PostMapping("/add")
//    public ResponseEntity<?> addToCart(
//            @CookieValue() Integer cartId,
//            @RequestParam Integer productSizeId,
//            @RequestParam Integer quantity) {
//        try {
//            cartService.addToCart(getUserById(userId), productSizeId, quantity);
//            return ResponseEntity.ok("Added to cart");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
//        }
//    }
//
//    @DeleteMapping("/remove")
//    public ResponseEntity<?> removeFromCart(
//            @CookieValue(value = "token") String token,
//            @RequestParam Integer cartDetailId) {
//        try {
//            Integer userId = ExtractToken.extractUserIdFromToken(token);
//            UserEntity user = getUserById(userId);
//            cartService.removeCartDetailById(user, cartDetailId);
//            return ResponseEntity.ok("Removed from cart");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
//        }
//    }
//
//    @PutMapping("/update-size")
//    public ResponseEntity<?> updateSizeInCart(
//            @CookieValue(value = "token") String token,
//            @RequestParam Integer cartDetailId,
//            @RequestParam Integer newSizeId) {
//        try {
//            Integer userId = ExtractToken.extractUserIdFromToken(token);
//            UserEntity user = getUserById(userId);
//            cartService.updateSizeInCart(user, cartDetailId, newSizeId);
//            return ResponseEntity.ok("Updated size in cart");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
//        }
//    }
//
//    @PutMapping("/update-quantity")
//    public ResponseEntity<?> updateQuantity(@RequestParam Integer cartDetailId,
//                                            @RequestParam Integer quantity,
//                                            @CookieValue("token") String token) {
//        try {
//            Integer userId = ExtractToken.extractUserIdFromToken(token);
//            UserEntity user = getUserById(userId);
//            cartService.updateQuantityInCart(user, cartDetailId, quantity);
//            return ResponseEntity.ok("Updated size in cart");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
//        }
//    }
//
//
//    @DeleteMapping("/clear")
//    public ResponseEntity<?> clearCart(@CookieValue(value = "token") String token) {
//        try {
//            Integer userId = ExtractToken.extractUserIdFromToken(token);
//            UserEntity user = getUserById(userId);
//            cartService.clearCart(user);
//            return ResponseEntity.ok("Cleared cart");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
//        }
//    }

}
