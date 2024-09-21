package com.sookmyung.concon.Coupon.controller;

import com.sookmyung.concon.Coupon.dto.CouponCreateRequestDto;
import com.sookmyung.concon.Coupon.dto.CouponCreateResponseDto;
import com.sookmyung.concon.Coupon.dto.CouponDetailResponseDto;
import com.sookmyung.concon.Coupon.dto.CouponSimpleResponseDto;
import com.sookmyung.concon.Coupon.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "쿠폰")
@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {
    final private CouponService couponService;

    @Operation(summary = "쿠폰 생성")
    @PostMapping
    public ResponseEntity<CouponDetailResponseDto> createCoupon(
            @RequestHeader("Authorization") String token,
            @RequestBody CouponCreateRequestDto request) {
        return ResponseEntity.ok(couponService.saveCoupon(token, request));
    }

    @Operation(summary = "나의 쿠폰 전체 조회")
    @GetMapping
    public ResponseEntity<List<CouponSimpleResponseDto>> getCouponsByUserId(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(couponService.getAllMyCoupon(token));
    }

    // 쿠폰 삭제 API
    @Operation(summary = "쿠폰 삭제")
    @DeleteMapping("/{couponId}")
    public ResponseEntity<Object> deleteCoupon(@PathVariable Long couponId) {
        couponService.deleteCoupon(couponId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "coupon id로 상세조회")
    @GetMapping("/{coupon-id}")
    public ResponseEntity<CouponDetailResponseDto> getCouponDetail(
            @RequestHeader("Authorization") String token,
            @PathVariable("coupon-id") Long couponId) {
        return ResponseEntity.ok(couponService.getCouponDetail(token, couponId));
    }
}
