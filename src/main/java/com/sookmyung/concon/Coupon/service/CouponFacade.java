package com.sookmyung.concon.Coupon.service;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Coupon.dto.CouponCreateResponseDto;
import com.sookmyung.concon.Coupon.dto.CouponDetailResponseDto;
import com.sookmyung.concon.Coupon.dto.CouponSimpleResponseDto;
import com.sookmyung.concon.Coupon.repository.CouponRepository;
import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.Item.dto.ItemSimpleResponseDto;
import com.sookmyung.concon.Photo.service.PhotoFacade;
import com.sookmyung.concon.Review.dto.ReviewSimpleResponseDto;
import com.sookmyung.concon.Review.repository.ReviewRepository;
import com.sookmyung.concon.User.Entity.User;
import com.sookmyung.concon.User.dto.UserSimpleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponFacade {

    private final PhotoFacade photoFacade;
    private final CouponRepository couponRepository;
    private final ReviewRepository reviewRepository;

    public CouponDetailResponseDto toDetailDto(Coupon coupon, User user, Item item) {
        String couponImageUrl = photoFacade.getCouponPhotoUrl(coupon);
        String barcodeImageUrl = photoFacade.getCouponBarcodePhotoUrl(coupon);
        UserSimpleResponseDto userDto = UserSimpleResponseDto.toDto(user, photoFacade.getUserPhotoUrl(user));
        ItemSimpleResponseDto itemDto = ItemSimpleResponseDto.toDto(item, photoFacade.getItemPhotoUrl(item));
        List<ReviewSimpleResponseDto> reviews = reviewRepository.findAllByItemId(item.getId(), PageRequest.of(0, 10))
                .stream().map(ReviewSimpleResponseDto::toDto).toList();

        return CouponDetailResponseDto.toDto(coupon, userDto, itemDto, barcodeImageUrl, couponImageUrl, reviews);
    }

    public CouponSimpleResponseDto toSimpleDto(Coupon coupon) {
        Item item = coupon.getItem();
        ItemSimpleResponseDto itemDto = ItemSimpleResponseDto.toDto(item, photoFacade.getItemPhotoUrl(item));
        boolean isUsed = coupon.getUsedDate() != null;

        return CouponSimpleResponseDto.toDto(coupon, itemDto, isUsed);
    }

    public CouponCreateResponseDto toCreateDto(Coupon coupon, LocalDateTime now) {
        Item item = coupon.getItem();
        ItemSimpleResponseDto itemDto = ItemSimpleResponseDto.toDto(item, photoFacade.getItemPhotoUrl(item));

        String barcodeImageUrl = photoFacade.uploadCouponBarcodePhoto(coupon, coupon.getBarcodeImageFileName(), now);
        String couponImageUrl = photoFacade.uploadCouponPhoto(coupon, coupon.getImageFileName(), now);

        return CouponCreateResponseDto.toDto(coupon, itemDto, barcodeImageUrl, couponImageUrl);
    }

    public Coupon findByCouponId(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 조회할 수 없습니다."));
    }

}
