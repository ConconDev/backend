package com.sookmyung.concon.Coupon.dto;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.User.Entity.User;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CouponCreateRequestDto {
    private String barcode;
    private String barcodeImage;
    private String imageFileName;
    private String name;
    private Double price;
    private LocalDate expirationDate; // 'yyyy-MM-dd' 형식

    public Coupon toEntity(User user, Item item) {
        return Coupon.builder()
                .user(user)
                .item(item)
                .barcode(barcode)
                .barcodeImageFileName(barcodeImage)
                .imageUrl(imageFileName)
                .name(name)
                .price(price)
                .expirationDate(expirationDate)
                .buyFlag(false)
                .usedDate(null)
                .build();
    }
}