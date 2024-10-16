package com.sookmyung.concon.Coupon.dto;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Item.dto.ItemSimpleResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class CouponSimpleResponseDto {
    private Long couponId;
    private ItemSimpleResponseDto item;
    private LocalDate expirationDate;
    private Double price;
    private boolean isUsed;
    private boolean isBuyFlag;
    private boolean isSellFlag;
    private String memo;

    public static CouponSimpleResponseDto toDto(Coupon coupon, ItemSimpleResponseDto item, boolean isUsed) {
        return CouponSimpleResponseDto.builder()
                .couponId(coupon.getId())
                .item(item)
                .expirationDate(coupon.getExpirationDate())
                .price(coupon.getRemainingPrice())
                .isUsed(isUsed)
                .isSellFlag(coupon.isSellFlag())
                .isBuyFlag(coupon.isBuyFlag())
                .memo(coupon.getMemo())
                .build();
    }
}