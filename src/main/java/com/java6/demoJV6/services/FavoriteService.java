package com.java6.demoJV6.services;

import com.java6.demoJV6.dto.FavoriteDTO;
import com.java6.demoJV6.entity.FavoriteEntity;

import com.java6.demoJV6.entity.UserEntity;
import com.java6.demoJV6.entity.ProductEntity;
import com.java6.demoJV6.jpa.FavoriteJPA;
import com.java6.demoJV6.jpa.UserJPA;
import com.java6.demoJV6.jpa.ProductJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteJPA favoriteJPA;

    @Autowired
    private UserJPA userJPA;

    @Autowired
    private ProductJPA productJPA;

    
 // Lấy danh sách các sản phẩm yêu thích của một user
    public List<FavoriteDTO> getFavoritesByUser(Integer userId) {
        UserEntity user = userJPA.findById(userId)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        List<FavoriteEntity> favorites = favoriteJPA.findByUser(user);
        
        return favorites.stream().map(favorite -> {
            ProductEntity product = favorite.getProduct();
            FavoriteDTO dto = new FavoriteDTO();
            
            // Sử dụng Long cho id vì bạn đã dùng Long cho id trong FavoriteEntity
            dto.setId(favorite.getId().intValue());  // Chuyển Long thành Integer
            
            dto.setUserId(favorite.getUser().getId());
            dto.setProductId(product.getId());
            dto.setProductName(product.getName());
            dto.setProductPrice((double) product.getPrice()); // Chuyển long sang double

            // Kiểm tra xem danh sách images có trống không và lấy tên ảnh đầu tiên
            if (product.getImages() != null && !product.getImages().isEmpty()) {
                // Giả sử 'getImages' là danh sách các ImageEntity
                dto.setProductImage(product.getImages().get(0).getName());  // Lấy tên ảnh đầu tiên
            } else {
                dto.setProductImage("default-image.jpg");  // Hình ảnh mặc định nếu không có ảnh
            }

            return dto;
        }).collect(Collectors.toList());
    }




 // Thêm một sản phẩm vào danh sách yêu thích của user và trả về FavoriteDTO
    public FavoriteDTO addFavorite(Integer userId, Integer productId) {
        UserEntity user = userJPA.findById(userId)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        ProductEntity product = productJPA.findById(productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        // Kiểm tra nếu đã có yêu thích sản phẩm này chưa
        Optional<FavoriteEntity> existingFavorite = favoriteJPA.findByUserAndProduct(user, product);
        if (existingFavorite.isPresent()) {
            throw new RuntimeException("Sản phẩm đã có trong danh sách yêu thích.");
        }

        // Lưu lại sản phẩm yêu thích
        FavoriteEntity favorite = new FavoriteEntity();
        favorite.setUser(user);
        favorite.setProduct(product);
        favorite.setCreatedAt(LocalDateTime.now());
        FavoriteEntity savedFavorite = favoriteJPA.save(favorite);

        // Tạo và trả về FavoriteDTO
        FavoriteDTO dto = new FavoriteDTO();
        dto.setId(savedFavorite.getId().intValue());
        dto.setUserId(user.getId());
        dto.setProductId(product.getId());
        dto.setProductName(product.getName());
        dto.setProductPrice((double) product.getPrice());

        if (product.getImages() != null && !product.getImages().isEmpty()) {
            dto.setProductImage(product.getImages().get(0).getName());
        } else {
            dto.setProductImage("default-image.jpg");
        }

        return dto;
    }


    // Xóa sản phẩm khỏi danh sách yêu thích của user
    public void removeFavorite(Integer userId, Integer productId) {
        UserEntity user = userJPA.findById(userId)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        ProductEntity product = productJPA.findById(productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        FavoriteEntity favorite = favoriteJPA.findByUserAndProduct(user, product)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm yêu thích"));

        favoriteJPA.delete(favorite); // Xóa sản phẩm yêu thích
    }
}
