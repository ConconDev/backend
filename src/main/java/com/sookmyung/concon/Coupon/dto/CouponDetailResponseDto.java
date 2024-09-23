package com.sookmyung.concon.Coupon.dto;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Item.dto.ItemSimpleResponseDto;
import com.sookmyung.concon.Review.dto.ReviewSimpleResponseDto;
import com.sookmyung.concon.User.dto.UserSimpleResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

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
    private Boolean buyFlag;
    private String memo;
    private List<ReviewSimpleResponseDto> reviews;

    // TODO : Exception 처리
    public static CouponDetailResponseDto toDto(Coupon coupon, UserSimpleResponseDto user,
                                                ItemSimpleResponseDto item, String barcodeImageUrl,
                                                String couponImageUrl, List<ReviewSimpleResponseDto> reviews) {
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
                .buyFlag(coupon.isBuyFlag())
                .memo(coupon.getMemo())
                .reviews(reviews)
                .build();
    }

}