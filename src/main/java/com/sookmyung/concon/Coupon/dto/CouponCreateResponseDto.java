package com.sookmyung.concon.Coupon.dto;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Item.dto.ItemSimpleResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class CouponCreateResponseDto {
    private Long couponId;
    private ItemSimpleResponseDto item;
    private LocalDate expirationDate;
    private Double price;
    private String category;
    private boolean isUsed;
    private boolean isBuyFlag;
    private String barcodeImageUploadUrl;
    private String couponImageUploadUrl;

    public static CouponCreateResponseDto toDto(Coupon coupon, ItemSimpleResponseDto item,
                                         String barcodeImageUploadUrl, String couponImageUploadUrl) {
        return CouponCreateResponseDto.builder()
                .couponId(coupon.getId())
                .item(item)
                .expirationDate(coupon.getExpirationDate())
                .price(coupon.getRemainingPrice())
                .category(coupon.getCategory())
                .isUsed(false)
                .isBuyFlag(coupon.isBuyFlag())
                .barcodeImageUploadUrl(barcodeImageUploadUrl)
                .couponImageUploadUrl(couponImageUploadUrl)
                .build();
    }


}
