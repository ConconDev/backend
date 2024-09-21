package com.sookmyung.concon.Coupon.dto;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class CouponDetailResponseDto {

    private Long id;
    private Long userId;
    private Long itemId;
    private String barcode;

    private String imageUrl;
    private String barcodeImageUrl;

    private String name;
    private Double price;
    private LocalDate expirationDate;
    private LocalDate usedDate;
    private Boolean buyFlag;

    public static CouponDetailResponseDto toDto(Coupon coupon, String imageUrl, String barcodeImageUrl) {
        return CouponDetailResponseDto.builder()
                .id(coupon.getId())
                .userId(coupon.getUser().getId())
                .itemId(coupon.getItem().getId())
                .barcode(coupon.getBarcode())
                .imageUrl(imageUrl)
                .barcodeImageUrl(barcodeImageUrl)
                .name(coupon.getName())
                .price(coupon.getPrice())
                .expirationDate(coupon.getExpirationDate())
                .usedDate(coupon.getUsedDate())
                .buyFlag(coupon.isBuyFlag())
                .build();
    }

}