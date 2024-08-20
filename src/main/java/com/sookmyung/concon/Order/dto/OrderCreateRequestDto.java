package com.sookmyung.concon.Order.dto;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Order.entity.Orders;
import com.sookmyung.concon.Order.entity.OrderStatus;
import com.sookmyung.concon.User.Entity.User;

import java.time.LocalDate;

public class OrderCreateRequestDto {
    private Long couponId;
    private Long sellerId;
    private String title;
    private String content;
    private double price;

    public Orders toEntity(OrderCreateRequestDto dto, Coupon coupon, User seller) {
        return Orders.builder()
                .coupon(coupon)
                .seller(seller)
                .title(title)
                .content(content)
                .price(price)
                .createdDate(LocalDate.now())
                .statue(OrderStatus.AVAILABLE)
                .build();
    }
}
