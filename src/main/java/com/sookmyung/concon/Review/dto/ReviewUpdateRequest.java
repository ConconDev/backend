package com.sookmyung.concon.Review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewUpdateRequest {
    private Long reviewId;
    private double score;
    private String content;
}
