package com.sookmyung.concon.Order.dto;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Order.entity.Order;
import com.sookmyung.concon.User.Entity.User;

public class OrderCreateRequestDto {
    private Long couponId;
    private Long sellerId;
    private String title;
    private String content;
    private int price;

    public Order toEntity(OrderCreateRequestDto dto, Coupon coupon, User seller) {
        return Order.builder()
                .coupon(coupon)
                .seller(seller)
                .title(title)
                .content(content)
                .price(price)
                .build();
    }
}
