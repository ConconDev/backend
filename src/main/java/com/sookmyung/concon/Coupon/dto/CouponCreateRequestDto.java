package com.sookmyung.concon.Coupon.dto;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.Item.dto.ItemSimpleResponseDto;
import com.sookmyung.concon.User.Entity.User;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class CouponCreateRequestDto {
    private Long itemId;
    private String barcode;
    private String barcodeImageName;
    private String imageFileName;
    private Double price;
    private LocalDate expirationDate; // 'yyyy-MM-dd' 형식
    private String memo;

    public Coupon toEntity(User user, Item item, LocalDateTime now) {
        return Coupon.builder()
                .user(user)
                .item(item)
                .barcode(barcode)
                .barcodeImageFileName(barcodeImageName)
                .barcodeImageCreatedDate(now)
                .itemImageUrl(item.getImageUrl())
                .imageFileName(imageFileName)
                .imageCreateDate(now)
                .itemPrice(item.getPrice())
                .remainingPrice(price)
                .expirationDate(expirationDate)
                .buyFlag(false)
                .memo(memo)
                .build();
    }
}