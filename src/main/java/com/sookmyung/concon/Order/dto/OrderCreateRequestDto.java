package com.sookmyung.concon.Order.dto;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Order.entity.Orders;
import com.sookmyung.concon.Order.entity.OrderStatus;
import com.sookmyung.concon.User.Entity.User;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class OrderCreateRequestDto {
    private Long couponId;
    private Long sellerId;
    private String title;
    private String content;
    private double price;

    public Orders toEntity(Coupon coupon, User seller, String imageUrl) {
        return Orders.builder()
                .coupon(coupon)
                .seller(seller)
                .title(title)
                .content(content)
                .price(price)
                .createdDate(LocalDate.now())
                .status(OrderStatus.AVAILABLE)
                .imageUrl(imageUrl)
                .build();
    }
}
