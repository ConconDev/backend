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
    private boolean isSellFlag;
    private boolean isBuyFlag;
    private String barcodeImageUploadUrl;
    private String couponImageUploadUrl;
    private String memo;

    public static CouponCreateResponseDto toDto(Coupon coupon, ItemSimpleResponseDto item,
                                         String barcodeImageUploadUrl, String couponImageUploadUrl) {
        return CouponCreateResponseDto.builder()
                .couponId(coupon.getId())
                .item(item)
                .expirationDate(coupon.getExpirationDate())
                .price(coupon.getRemainingPrice())
                .isUsed(false)
                .isSellFlag(coupon.isSellFlag())
                .isBuyFlag(coupon.isBuyFlag())
                .barcodeImageUploadUrl(barcodeImageUploadUrl)
                .couponImageUploadUrl(couponImageUploadUrl)
                .memo(coupon.getMemo())
                .build();
    }


}
