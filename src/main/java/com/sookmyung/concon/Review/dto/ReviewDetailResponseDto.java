package com.sookmyung.concon.Review.dto;

import com.sookmyung.concon.Item.dto.ItemSimpleResponseDto;
import com.sookmyung.concon.Review.entity.Review;
import com.sookmyung.concon.User.dto.UserSimpleResponseDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewDetailResponseDto {
    private Long reviewId;
    private UserSimpleResponseDto user;
    private ItemSimpleResponseDto item;
    private double score;
    private String content;

    public static ReviewDetailResponseDto toDto(Review review, UserSimpleResponseDto user, ItemSimpleResponseDto item) {
        return ReviewDetailResponseDto.builder()
                .reviewId(review.getId())
                .user(user)
                .item(item)
                .score(review.getScore())
                .content(review.getContent())
                .build();
    }

}
