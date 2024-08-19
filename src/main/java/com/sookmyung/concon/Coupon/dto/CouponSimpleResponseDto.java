package com.sookmyung.concon.Coupon.dto;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class CouponSimpleResponseDto {
    private String name;
    private LocalDate expirationDate;
    private Double price;
    private String category;
    private boolean isUsed;
    private boolean isBuyFlag;

    public static CouponSimpleResponseDto toDto(Coupon coupon, boolean isUsed) {
        return CouponSimpleResponseDto.builder()
                .name(coupon.getName())
                .expirationDate(coupon.getExpirationDate())
                .category(coupon.getCategory())
                .isUsed(isUsed)
                .isBuyFlag(coupon.isBuyFlag())
                .build();
    }
}
