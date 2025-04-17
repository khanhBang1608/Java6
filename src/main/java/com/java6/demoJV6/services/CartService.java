package com.java6.demoJV6.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.java6.demoJV6.dto.CartDTO;
import com.java6.demoJV6.dto.CartDetailDTO;
import com.java6.demoJV6.dto.ProductSizeDTO;
import com.java6.demoJV6.dto.SizeDTO;
import com.java6.demoJV6.entity.*;
import com.java6.demoJV6.jpa.CartDetailJPA;
import com.java6.demoJV6.jpa.ProductSizeJPA;
import com.java6.demoJV6.jpa.UserJPA;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java6.demoJV6.jpa.CartJPA;

@Service
public class CartService {

    @Autowired
    private CartJPA cartJPA;

    @Autowired
    private CartDetailJPA cartDetailJPA;

    @Autowired
    private ProductSizeJPA productSizeJPA;

    @Autowired
    private UserJPA userJPA;

    public CartEntity save(CartEntity cart) {
        return cartJPA.save(cart);
    }

    public CartEntity getCartByUser(UserEntity user) {
        return cartJPA.findByUser(user)
                .orElseGet(() -> {
                    CartEntity newCart = new CartEntity();
                    newCart.setUser(user);
                    return cartJPA.save(newCart);
                });
    }

    public List<SizeDTO> getAvailableSizes(Integer productId) {
        List<ProductSizeEntity> productSizes = productSizeJPA.findByProductId(productId);

        return productSizes.stream()
                .map(productSize -> new SizeDTO(productSize.getSize().getId(), productSize.getSize().getName()))
                .collect(Collectors.toList());
    }

    public CartDTO getCartByUserId(Integer userId) {
        UserEntity user = userJPA.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CartEntity cart = cartJPA.findByUser(user).orElse(null);

        if (cart == null) {
            return new CartDTO();
        }

        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setUserId(userId);

        List<CartDetailDTO> cartDetails = cart.getCartDetails().stream()
                .map(detail -> {
                    ProductEntity product = detail.getProductSize().getProduct();
                    SizeEntity size = detail.getProductSize().getSize();

                    // Lấy các size khả dụng của sản phẩm
                    List<SizeDTO> availableSizes = getAvailableSizes(product.getId());

                    String productImage = (product.getImages() != null && !product.getImages().isEmpty())
                            ? product.getImages().get(0).getName()
                            : null;

                    SizeDTO sizeDTO = new SizeDTO(size.getId(), size.getName());

                    return new CartDetailDTO(
                            detail.getId(),
                            product.getId(),
                            product.getName(),
                            productImage,
                            detail.getQuantity(),
                            sizeDTO,
                            detail.getProductSize().getStock(),
                            product.getPrice(),
                            availableSizes
                    );
                })
                .collect(Collectors.toList());

        cartDTO.setCartDetails(cartDetails);
        return cartDTO;
    }



    public void addToCart(UserEntity user, Integer productSizeId, Integer quantity) {
        CartEntity cart = cartJPA.findByUser(user).orElse(null);
        if (cart == null) {
            cart = new CartEntity();
            cart.setUser(user);
            cartJPA.save(cart);
        }

        CartDetailEntity cartDetail = new CartDetailEntity();
        cartDetail.setCart(cart);
        cartDetail.setProductSize(productSizeJPA.findById(productSizeId).orElse(null));
        cartDetail.setQuantity(quantity);
        cartDetailJPA.save(cartDetail);
    }

    public void removeCartDetailById(UserEntity user, Integer cartDetailId) {
        CartEntity cart = cartJPA.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng"));

        CartDetailEntity detail = cartDetailJPA.findById(cartDetailId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm trong giỏ hàng"));

        if (!detail.getCart().getId().equals(cart.getId())) {
            throw new RuntimeException("Chi tiết giỏ hàng không thuộc người dùng này");
        }
        cartDetailJPA.delete(detail);
    }

    public void updateSizeInCart(UserEntity user, Integer cartDetailId, Integer newSizeId) {
        CartEntity cart = getCartByUser(user);

        CartDetailEntity cartDetail = cartDetailJPA.findById(cartDetailId)
                .orElseThrow(() -> new RuntimeException("Cart detail not found"));

        if (!cartDetail.getCart().getId().equals(cart.getId())) {
            throw new RuntimeException("Cart detail không thuộc người dùng");
        }
        Integer productId = cartDetail.getProductSize().getProduct().getId();
        ProductSizeEntity newProductSize = productSizeJPA.findByProduct_IdAndSize_Id(productId, newSizeId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy productSize với size mới"));
        cartDetail.setProductSize(newProductSize);

        cartDetailJPA.save(cartDetail);
    }

    public void updateQuantityInCart(UserEntity user, Integer cartDetailId, Integer newQuantity) {
        if (newQuantity <= 0) {
            throw new RuntimeException("Số lượng phải lớn hơn 0");
        }

        CartEntity cart = getCartByUser(user);

        CartDetailEntity cartDetail = cartDetailJPA.findById(cartDetailId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết giỏ hàng"));

        if (!cartDetail.getCart().getId().equals(cart.getId())) {
            throw new RuntimeException("Chi tiết giỏ hàng không thuộc người dùng này");
        }

        ProductSizeEntity productSize = cartDetail.getProductSize();
        if (newQuantity > productSize.getStock()) {
            throw new RuntimeException("Số lượng vượt quá số lượng tồn kho");
        }

        cartDetail.setQuantity(newQuantity);
        cartDetailJPA.save(cartDetail);
    }



    @Transactional
    public void clearCart(UserEntity user) {
        CartEntity cart = getCartByUser(user);
        cartDetailJPA.deleteAllByCart(cart);
    }

}
