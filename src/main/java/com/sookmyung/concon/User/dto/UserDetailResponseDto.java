package com.sookmyung.concon.User.dto;

import com.sookmyung.concon.Order.dto.OrderSimpleResponseDto;
import com.sookmyung.concon.Review.entity.Review;
import com.sookmyung.concon.User.Entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Builder
@Getter
@Slf4j
public class UserDetailResponseDto {
    private Long userId;
    private String username;
    private String profileUrl;
    private String color;
    private List<OrderSimpleResponseDto> orders;

    //TODO : 리뷰 추가
    private List<Review> reviews;

    public static UserDetailResponseDto toDto(User user, List<OrderSimpleResponseDto> orders, List<Review> reviews, String profileUrl) {
        log.debug("Reviews in toDto: {}", reviews);
        return UserDetailResponseDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .profileUrl(profileUrl)
                .color(user.getColor())
                .orders(orders)
                .reviews(reviews)
                .build();
    }
}