package com.sookmyung.concon.Coupon.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CouponModifyRequestDto {
    private Long couponId;
    private double price;
    private LocalDate expireDate;
    private String memo;
}
