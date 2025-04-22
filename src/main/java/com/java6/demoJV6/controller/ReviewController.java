package com.java6.demoJV6.controller;

import com.java6.demoJV6.dto.ReviewRequestDTO;
import com.java6.demoJV6.dto.ReviewResponseDTO;
import com.java6.demoJV6.entity.OrderDetailEntity;
import com.java6.demoJV6.entity.ReviewEntity;
import com.java6.demoJV6.jpa.OrderDetailJPA;
import com.java6.demoJV6.jpa.ReviewJPA;
import com.java6.demoJV6.services.OrderDetailServices;
import com.java6.demoJV6.services.ReviewService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/user/reviews")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ReviewController {

	@Autowired
    ReviewService reviewService;
	
	@Autowired
	ReviewJPA reviewRepository;
	
	@Autowired
	OrderDetailServices orderDetailServices;


    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByProduct(@PathVariable Integer productId) {
        List<ReviewResponseDTO> reviews = reviewService.getReviewsByProduct(productId);
        return ResponseEntity.ok(reviews);
    }
    
    @PostMapping
    public ResponseEntity<?> addReview(@RequestBody ReviewRequestDTO dto) {
        reviewService.addReview(dto);
        return ResponseEntity.ok("Review submitted successfully!");
    }

    @GetMapping("/checkUser/{userId}/checkProduct/{productId}")
    public ResponseEntity<?> hasPurchased(
            @PathVariable("userId") Integer userId,
            @PathVariable("productId") Integer productId) {

        try {
            OrderDetailEntity od = orderDetailServices.findByUserAndProduct(userId, productId);
            if (od != null) {
                boolean hasReviewed = reviewRepository.existsByOrderDetail_Order_User_IdAndOrderDetail_ProductSize_Product_Id(userId, productId);
                Map<String, Object> response = new HashMap<>();
                response.put("orderDetailId", od.getId());
                response.put("hasPurchased", true);
                response.put("hasReviewed", hasReviewed);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.ok(Map.of(
                        "hasPurchased", false,
                        "hasReviewed", false
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Server error", "message", e.getMessage()));
        }
    }

    @GetMapping("/checkReview/{orderDetailId}")
    public ResponseEntity<Map<String, Object>> checkReview(@PathVariable("orderDetailId") int orderDetailId) {
        boolean hasReviewed = reviewService.isReviewed(orderDetailId);
        Map<String, Object> response = new HashMap<>();
        response.put("hasReviewed", hasReviewed);
        return ResponseEntity.ok(response);
    }
    @GetMapping()
    public ResponseEntity<List<ReviewResponseDTO>> getAllReviewByUserId(@RequestParam  int userId) {
        List<ReviewEntity> reviews = reviewService.getAllReviewsByUserId(userId);
        
        // Convert ReviewEntity to ReviewResponseDTO
        List<ReviewResponseDTO> reviewResponseDTOs = reviews.stream()
            .map(review -> new ReviewResponseDTO(
                review.getOrderDetail().getProductSize().getProduct().getName(), // Assuming ReviewEntity has a reference to Product
                review.getOrderDetail().getProductSize().getSize().getName(),     // Assuming ReviewEntity has a reference to Size
                review.getRating(),
                review.getComment(),
                review.getUser().getName(), // Assuming ReviewEntity has a reference to User
                review.getReviewDate()
            ))
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(reviewResponseDTOs);
    }

   
}