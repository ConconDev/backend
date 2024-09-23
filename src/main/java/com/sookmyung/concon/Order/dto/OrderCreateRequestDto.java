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

    public Orders toEntity(Coupon coupon, User seller) {
        return Orders.builder()
                .coupon(coupon)
                .itemPhotoUrl(coupon.getItemImageUrl())
                .seller(seller)
                .createdDate(LocalDate.now())
                .status(OrderStatus.AVAILABLE)
                .build();
    }
}
