package com.java6.demoJV6.controller;

import com.java6.demoJV6.dto.FavoriteDTO;
import com.java6.demoJV6.entity.FavoriteEntity;
import com.java6.demoJV6.entity.ProductEntity;
import com.java6.demoJV6.entity.UserEntity;
import com.java6.demoJV6.jpa.ProductJPA;
import com.java6.demoJV6.jpa.UserJPA;
import com.java6.demoJV6.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user/favorites")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") 
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private ProductJPA productJPA;
    
    @Autowired
    private UserJPA userJPA;

    // Lấy danh sách yêu thích của user
    @GetMapping("/{userId}")
    public List<FavoriteDTO> getFavorites(@PathVariable Integer userId) {
        return favoriteService.getFavoritesByUser(userId);
    }


    // Thêm sản phẩm vào danh sách yêu thích
    @PostMapping("/favorites")
    public ResponseEntity<?> addToFavorite(@RequestParam Integer userId, @RequestParam Integer productId) {
        try {
            FavoriteDTO savedFavoriteDTO = favoriteService.addFavorite(userId, productId);
            return ResponseEntity.ok(savedFavoriteDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Xóa sản phẩm khỏi danh sách yêu thích
    @DeleteMapping("/{userId}/{productId}")
    public void removeFavorite(@PathVariable Integer userId, @PathVariable Integer productId) {
        favoriteService.removeFavorite(userId, productId);
        
    }
}

