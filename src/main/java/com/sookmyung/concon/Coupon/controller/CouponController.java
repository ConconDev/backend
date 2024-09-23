package com.sookmyung.concon.Coupon.controller;

import com.sookmyung.concon.Coupon.dto.*;
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
    public ResponseEntity<CouponCreateResponseDto> createCoupon(
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
    @DeleteMapping("/{coupon-id}")
    public ResponseEntity<Object> deleteCoupon(
            @PathVariable("coupon-id") Long couponId) {
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

    @Operation(summary = "사용 완료", description = "사용 날짜 생성")
    @PostMapping("/use/{coupon-id}")
    public ResponseEntity<?> useCoupon(
            @PathVariable("coupon-id") Long couponId) {
        return ResponseEntity.ok(couponService.useCoupon(couponId));
    }

    @Operation(summary = "사용 완료 취소, 테스트용")
    @PostMapping("/use-cancel/{coupon-id}")
    public ResponseEntity<?> cancelCoupon(
            @PathVariable("coupon-id") Long couponId) {
        return ResponseEntity.ok(couponService.cancelCoupon(couponId));
    }

    @Operation(summary = "잔여 가격 수정")
    @PostMapping("/remain-price")
    public ResponseEntity<?> remainCouponPrice(CouponChangePriceRequestDto request) {
        return ResponseEntity.ok(couponService.changeRemainPrice(request));
    }
}