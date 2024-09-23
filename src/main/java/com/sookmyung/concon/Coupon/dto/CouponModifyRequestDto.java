package com.sookmyung.concon.Coupon.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CouponModifyRequestDto {
    private Long couponId;
    private Long itemId;
    private double price;
    private LocalDate expireDate;
    private String memo;
    private String barcode;
}
