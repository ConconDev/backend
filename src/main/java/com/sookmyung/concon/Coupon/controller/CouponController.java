package com.sookmyung.concon.Coupon.controller;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping
    public Coupon createCoupon(
            @RequestParam("user_id") Long userId,
            @RequestParam("barcode") String barcode,
            @RequestParam("barcode_image") MultipartFile barcodeImage,
            @RequestParam("image") MultipartFile image,
            @RequestParam("item_id") Long itemId,
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("expiration_date") String expirationDate) {

        return couponService.saveCoupon(userId, barcode, barcodeImage, image, itemId, name, price, expirationDate);
    }
}
