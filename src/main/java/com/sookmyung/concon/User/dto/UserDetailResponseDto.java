package com.sookmyung.concon.User.dto;

import com.sookmyung.concon.Order.dto.OrderSimpleResponseDto;
import com.sookmyung.concon.Review.dto.ReviewSimpleResponseDto;
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
    private String QRImageUrl;
    private String color;
    private List<OrderSimpleResponseDto> orders;

    public static UserDetailResponseDto toDto(User user, List<OrderSimpleResponseDto> orders, String profileUrl, String QRImageUrl) {
        return UserDetailResponseDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .profileUrl(profileUrl)
                .QRImageUrl(QRImageUrl)
                .color(user.getColor())
                .orders(orders)
                .build();
    }
}