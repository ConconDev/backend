package com.sookmyung.concon.Coupon.controller;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Coupon.dto.CouponRequestDto;
import com.sookmyung.concon.Coupon.dto.CouponResponseDto;
import com.sookmyung.concon.Coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {
    final private CouponService couponService;

    @PostMapping
    public CouponResponseDto createCoupon(@ModelAttribute CouponRequestDto requestDto) {
        Coupon coupon = couponService.saveCoupon(
                requestDto.getUserId(),
                requestDto.getBarcode(),
                requestDto.getBarcodeImage(),
                requestDto.getImage(),
                requestDto.getItemId(),
                requestDto.getName(),
                requestDto.getPrice(),
                requestDto.getExpirationDate()
        );

        return convertToResponseDto(coupon);
    }

    @GetMapping("/user/{userId}")
    public List<CouponResponseDto> getCouponsByUserId(
            @PathVariable Long userId,
            @RequestParam(required = false) Boolean usedFlag) {

        if (usedFlag == null) {
            usedFlag = false;
        }

        List<Coupon> coupons = couponService.getCouponsByUserIdAndUsedFlag(userId, usedFlag);
        return coupons.stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }

    // 쿠폰 삭제 API
    @DeleteMapping("/{couponId}")
    public void deleteCoupon(@PathVariable Long couponId) {
        couponService.deleteCoupon(couponId);
    }

    private CouponResponseDto convertToResponseDto(Coupon coupon) {
        CouponResponseDto dto = new CouponResponseDto();
        dto.setId(coupon.getId());
        dto.setUserId(coupon.getUserId());
        dto.setItemId(coupon.getItem() != null ? coupon.getItem().getId() : null);
        dto.setBarcode(coupon.getBarcode());
        dto.setBarcodeImagePath(coupon.getBarcodeImagePath());
        dto.setImagePath(coupon.getImagePath());
        dto.setName(coupon.getName());
        dto.setPrice(coupon.getPrice());
        dto.setExpirationDate(coupon.getExpirationDate());
        dto.setUsedDate(coupon.getUsedDate());
        dto.setUsedFlag(coupon.getUsedFlag());
        dto.setBuyFlag(coupon.getBuyFlag());
        return dto;
    }
}
