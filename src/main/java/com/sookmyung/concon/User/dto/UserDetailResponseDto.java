package com.sookmyung.concon.User.dto;

import com.sookmyung.concon.Order.dto.OrderSimpleResponseDto;
import com.sookmyung.concon.Review.entity.Review;
import com.sookmyung.concon.User.Entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class UserDetailResponseDto {
    private Long userId;
    private String username;
    private String profileUrl;
    private String color;
    private List<OrderSimpleResponseDto> orders;

    //TODO : 리뷰 추가
    private List<Review> reviews;

    public static UserDetailResponseDto toDto(User user, List<OrderSimpleResponseDto> orders, List<Review> reviews) {
        return UserDetailResponseDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .profileUrl(user.getProfileUrl())
                .color(user.getColor())
                .orders(orders)
                .reviews(reviews)
                .build();
    }
}
/*
1. 거래를 위해 기프티콘을 등록해 둔 유저의 닉네임,
아바타 색상, 판매 상품 정보(최근 등록순으로 2개),
리뷰 정보(없으면 null, 있으면 최근 등록순 2개)

를 전체 유저 중 랜덤으로 5개

2. 판매 등록해 둔 기프티콘의 카테고리 기준으로
유저를 분리해서 1번과 동일한 정보 제공
(예를 들어 카페, 디저트를 터치했을 때
해당 기프티콘을 판매 등록해 둔 유저의 정보만 분류해서 랜덤으로 5명의 정보 제공)
 */
