package com.java6.demoJV6.jpa;

import com.java6.demoJV6.entity.FavoriteEntity;
import com.java6.demoJV6.entity.UserEntity;
import com.java6.demoJV6.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteJPA extends JpaRepository<FavoriteEntity, Integer> {

    // Lấy tất cả sản phẩm yêu thích của user
    List<FavoriteEntity> findByUser(UserEntity user);

    // Kiểm tra nếu user đã yêu thích sản phẩm này chưa
    Optional<FavoriteEntity> findByUserAndProduct(UserEntity user, ProductEntity product);
}
