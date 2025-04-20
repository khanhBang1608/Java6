package com.java6.demoJV6.services;

import com.java6.demoJV6.dto.ReviewRequestDTO;
import com.java6.demoJV6.dto.ReviewResponseDTO;
import com.java6.demoJV6.entity.*;
import com.java6.demoJV6.jpa.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewJPA reviewRepository;
    private final OrderDetailJPA orderDetailJPA;

    public List<ReviewResponseDTO> getReviewsByProduct(int productId) {
        List<ReviewEntity> allReviews = reviewRepository.findByProductId(productId);

        return allReviews.stream()
                .map(r -> {
                    ProductSizeEntity size = r.getOrderDetail().getProductSize();

                    String userName = (r.getOrderDetail().getOrder() != null && r.getOrderDetail().getOrder().getUser() != null)
                            ? r.getOrderDetail().getOrder().getUser().getName()
                            : "Unknown User";

                    return new ReviewResponseDTO(
                            r.getOrderDetail().getProductSize().getProduct().getName(),
                            size.getSize().getName(),
                            r.getRating(),
                            r.getComment(),
                            userName,
                            r.getReviewDate()
                    );
                }).toList();
    }

    public void addReview(ReviewRequestDTO dto) {
        OrderDetailEntity od = orderDetailJPA.findById(dto.getOrderDetailId())
                .orElseThrow(() -> new RuntimeException("OrderDetail not found"));

        UserEntity user = od.getOrder().getUser();

        if (user == null) {
            throw new RuntimeException("User not found for the order");
        }

        System.out.println("User found: " + user.getId() + ", Name: " + user.getName());

        ReviewEntity review = new ReviewEntity();
        review.setOrderDetail(od);
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setReviewDate(LocalDateTime.now());
        review.setUser(user);  // Đảm bảo user được gán chính xác

        reviewRepository.save(review);
    }

}
