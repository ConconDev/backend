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
    private Long orderId;
    private CouponSimpleResponseDto coupon;
    private UserIdResponseDto seller;
    private UserIdResponseDto buyer;

    private OrderStatus status;

    public static OrderSimpleResponseDto toDto(Orders orders, CouponSimpleResponseDto coupon,
                                               UserIdResponseDto seller, UserIdResponseDto buyer) {
        return OrderSimpleResponseDto.builder()
                .orderId(orders.getId())
                .coupon(coupon)
                .seller(seller)
                .buyer(buyer)
                .status(orders.getStatus())
                .build();
    }

}
