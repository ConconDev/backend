package com.sookmyung.concon.Coupon.dto;

import lombok.Getter;

@Getter
public class CouponChangePriceRequestDto {
    private Long couponId;
    private Double remainPrice;
}
