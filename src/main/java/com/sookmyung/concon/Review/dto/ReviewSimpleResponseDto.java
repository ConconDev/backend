package com.sookmyung.concon.Review.dto;

import com.sookmyung.concon.Review.entity.Review;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewSimpleResponseDto {
    private Long reviewId;
    private double score;
    private String content;

    public static ReviewSimpleResponseDto toDto(Review review) {
        return ReviewSimpleResponseDto.builder()
                .reviewId(review.getId())
                .score(review.getScore())
                .content(review.getContent())
                .build();
    }
}
