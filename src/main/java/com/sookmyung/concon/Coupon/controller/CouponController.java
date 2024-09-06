package com.sookmyung.concon.Coupon.controller;

import com.sookmyung.concon.Coupon.dto.CouponCreateRequestDto;
import com.sookmyung.concon.Coupon.dto.CouponDetailResponseDto;
import com.sookmyung.concon.Coupon.dto.CouponSimpleResponseDto;
import com.sookmyung.concon.Coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {
    final private CouponService couponService;

    @PostMapping
    public ResponseEntity<CouponDetailResponseDto> createCoupon(
            @RequestBody CouponCreateRequestDto request) {
        return ResponseEntity.ok(couponService.saveCoupon(request));
    }

    @GetMapping
    public ResponseEntity<List<CouponSimpleResponseDto>> getCouponsByUserId(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(couponService.getAllMyCoupon(token));
    }

    // 쿠폰 삭제 API
    @DeleteMapping("/{couponId}")
    public ResponseEntity<Object> deleteCoupon(@PathVariable Long couponId) {
        couponService.deleteCoupon(couponId);
        return ResponseEntity.noContent().build();
    }

}
