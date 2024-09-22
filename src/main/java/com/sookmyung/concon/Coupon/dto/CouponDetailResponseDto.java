package com.sookmyung.concon.Coupon.dto;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Item.dto.ItemSimpleResponseDto;
import com.sookmyung.concon.User.dto.UserSimpleResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class CouponDetailResponseDto {

    private Long couponId;
    private UserSimpleResponseDto user;
    private ItemSimpleResponseDto item;

    private String barcode;
    private String barcodeImageUrl;
    private String itemImageUrl;
    private String couponImageUrl;

    private Double price;
    private LocalDate expirationDate;
    private LocalDate usedDate;
    private String category;
    private Boolean buyFlag;
    private String memo;

    // TODO : Exception 처리
    public static CouponDetailResponseDto toDto(Coupon coupon, UserSimpleResponseDto user,
                                                ItemSimpleResponseDto item, String barcodeImageUrl,
                                                String couponImageUrl) {
        return CouponDetailResponseDto.builder()
                .couponId(coupon.getId())
                .user(user)
                .item(item)
                .barcode(coupon.getBarcode())
                .barcodeImageUrl(barcodeImageUrl)
                .itemImageUrl(item.getImageUrl())
                .couponImageUrl(couponImageUrl)
                .price(coupon.getRemainingPrice())
                .expirationDate(coupon.getExpirationDate())
                .usedDate(coupon.getUsedDate())
                .category(coupon.getCategory())
                .buyFlag(coupon.isBuyFlag())
                .memo(coupon.getMemo())
                .build();
    }

}