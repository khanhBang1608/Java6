package com.java6.demoJV6.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReviewResponseDTO {
    private String productName;
    private String sizeName;
    private int rating;
    private String comment;
    private String userName;
    private LocalDateTime reviewDate;
}
