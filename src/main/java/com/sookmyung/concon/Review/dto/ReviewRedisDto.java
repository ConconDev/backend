package com.sookmyung.concon.Review.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewRedisDto {
    private ReviewCreateRequestDto request;
    private String token;

    public static ReviewRedisDto toDto(ReviewCreateRequestDto request, String token) {
        return ReviewRedisDto.builder()
                .request(request)
                .token(token)
                .build();
    }

}
