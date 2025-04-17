package com.java6.demoJV6.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java6.demoJV6.dto.CartDTO;
import com.java6.demoJV6.dto.CartDetailDTO;
import com.java6.demoJV6.dto.ProductDTO;
import com.java6.demoJV6.dto.ProductSizeDTO;
import com.java6.demoJV6.entity.CartDetailEntity;
import com.java6.demoJV6.entity.CartEntity;
import com.java6.demoJV6.entity.ProductSizeEntity;
import com.java6.demoJV6.jpa.CartDetailJPA;

@Service
public class CartDetailService {

    @Autowired
    private CartDetailJPA cartDetailJPA;

    /**
     * Thêm sản phẩm vào giỏ hàng.
     * Nếu sản phẩm đã tồn tại trong giỏ thì tăng số lượng.
     */
    public CartDetailEntity addToCart(CartEntity cart, ProductSizeEntity productSize, int quantity) {
        // Kiểm tra xem sản phẩm có size này đã tồn tại trong giỏ hàng chưa
        Optional<CartDetailEntity> existingCartDetail = cartDetailJPA.findByCartAndProductSize(cart, productSize);

        if (existingCartDetail.isPresent()) {
            // Nếu sản phẩm đã tồn tại, cộng dồn số lượng
            CartDetailEntity cartDetail = existingCartDetail.get();
            cartDetail.setQuantity(cartDetail.getQuantity() + quantity); // Cộng thêm số lượng
            return cartDetailJPA.save(cartDetail); // Cập nhật giỏ hàng
        }

        // Nếu chưa tồn tại, tạo mới CartDetail và thêm vào giỏ
        CartDetailEntity cartDetail = new CartDetailEntity();
        cartDetail.setCart(cart);
        cartDetail.setProductSize(productSize);
        cartDetail.setQuantity(quantity);

        return cartDetailJPA.save(cartDetail); // Thêm mới vào giỏ hàng
    }

    public CartDetailDTO toDTO(CartDetailEntity entity) {
        CartDetailDTO dto = new CartDetailDTO();
        dto.setId(entity.getId());
        dto.setQuantity(entity.getQuantity());

        // CartDTO
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(entity.getCart().getId());
        dto.setCart(cartDTO);

        // ProductDTO
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(entity.getProductSize().getProduct().getId());
        productDTO.setName(entity.getProductSize().getProduct().getName());
        productDTO.setImageNames(
        	    entity.getProductSize() != null && 
        	    entity.getProductSize().getProduct() != null && 
        	    entity.getProductSize().getProduct().getImages() != null && 
        	    !entity.getProductSize().getProduct().getImages().isEmpty() ? 
        	    entity.getProductSize().getProduct().getImages().stream().map(image -> image.getName()).toList() : 
        	    new ArrayList<>()
        	);

        dto.setProduct(productDTO);

        // ✅ ProductSizeDTO
        ProductSizeDTO sizeDTO = new ProductSizeDTO();
        sizeDTO.setId(entity.getProductSize().getId());
        sizeDTO.setSizeName(entity.getProductSize().getSize().getName()); 
        sizeDTO.setStock(entity.getProductSize().getStock());
        dto.setProductSize(sizeDTO);
        return dto;
    }

}
