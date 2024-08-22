package com.sookmyung.concon.Order.dto;

import com.sookmyung.concon.Coupon.dto.CouponSimpleResponseDto;
import com.sookmyung.concon.Order.entity.OrderStatus;
import com.sookmyung.concon.Order.entity.Orders;
import com.sookmyung.concon.User.dto.UserIdResponseDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderSimpleResponseDto {
    private Long id;
    private CouponSimpleResponseDto coupon;
    private UserIdResponseDto seller;
    private String title;
    private double price;
    private OrderStatus status;

    public static OrderSimpleResponseDto toDto(Orders orders, CouponSimpleResponseDto coupon, UserIdResponseDto seller) {
        return OrderSimpleResponseDto.builder()
                .id(orders.getId())
                .coupon(coupon)
                .seller(seller)
                .title(orders.getTitle())
                .price(orders.getPrice())
                .status(orders.getStatus())
                .build();
    }

}
