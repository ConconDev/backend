package com.sookmyung.concon.Order.dto;

import com.sookmyung.concon.Coupon.dto.CouponSimpleResponseDto;
import com.sookmyung.concon.Order.entity.Orders;
import com.sookmyung.concon.Order.entity.OrderStatus;
import com.sookmyung.concon.User.dto.UserSimpleResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class OrderDetailResponseDto {
    private Long id;
    private CouponSimpleResponseDto coupon;
    private UserSimpleResponseDto buyer;
    private UserSimpleResponseDto seller;
    private LocalDate createDate;
    private LocalDate transactionDate;
    private OrderStatus status;


    public static OrderDetailResponseDto toDto(Orders order, CouponSimpleResponseDto coupon, UserSimpleResponseDto buyer, UserSimpleResponseDto seller) {
        return OrderDetailResponseDto.builder()
                .id(order.getId())
                .coupon(coupon)
                .buyer(buyer)
                .seller(seller)
                .createDate(order.getCreatedDate())
                .transactionDate(order.getTransactionDate())
                .status(order.getStatus())
                .build();
    }

}
