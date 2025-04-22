package com.java6.demoJV6.controller;

import com.java6.demoJV6.dto.ReviewResponseDTO;
import com.java6.demoJV6.services.ReviewService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class Review2Controller {

	@Autowired
    ReviewService reviewService;


    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByProduct(@PathVariable Integer productId) {
        List<ReviewResponseDTO> reviews = reviewService.getReviewsByProduct(productId);
        return ResponseEntity.ok(reviews);
    }
    @GetMapping("/product/{productId}/summary")
    public ResponseEntity<Map<String, Object>> getReviewSummary(@PathVariable Integer productId) {
        List<ReviewResponseDTO> reviews = reviewService.getReviewsByProduct(productId);

        double averageRating = 0.0;
        int totalReviews = reviews.size();

        if (totalReviews > 0) {
            double totalRating = reviews.stream().mapToDouble(ReviewResponseDTO::getRating).sum();
            averageRating = totalRating / totalReviews;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("averageRating", averageRating);
        result.put("totalReviews", totalReviews);

        return ResponseEntity.ok(result);
    }


}