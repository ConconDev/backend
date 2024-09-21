package com.sookmyung.concon.Coupon.service;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Coupon.dto.CouponCreateRequestDto;
import com.sookmyung.concon.Coupon.dto.CouponCreateResponseDto;
import com.sookmyung.concon.Coupon.dto.CouponDetailResponseDto;
import com.sookmyung.concon.Coupon.dto.CouponSimpleResponseDto;
import com.sookmyung.concon.Coupon.repository.CouponRepository;
import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.Item.dto.ItemSimpleResponseDto;
import com.sookmyung.concon.Item.repository.ItemRepository;
import com.sookmyung.concon.Item.service.ItemFacade;
import com.sookmyung.concon.Photo.service.PhotoFacade;
import com.sookmyung.concon.Photo.service.PhotoManager;
import com.sookmyung.concon.User.Entity.User;
import com.sookmyung.concon.User.Jwt.JwtUtil;
import com.sookmyung.concon.User.dto.UserSimpleResponseDto;
import com.sookmyung.concon.User.repository.UserRepository;
import com.sookmyung.concon.User.service.OrderUserFacade;
import com.sookmyung.concon.User.service.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final ItemFacade itemFacade;
    private final OrderUserFacade orderUserFacade;
    private final CouponFacade couponFacade;
    private final PhotoFacade photoFacade;
    private final UserFacade userFacade;


    // 쿠폰 생성
    @Transactional
    public CouponCreateResponseDto saveCoupon(String token,
                                              CouponCreateRequestDto request) {
        User user = orderUserFacade.findUserByToken(token);
        Item item = itemFacade.findItemById(request.getItemId());

        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        Coupon coupon = request.toEntity(user, item, now);
        couponRepository.save(coupon);

        return couponFacade.toCreateDto(coupon, now);
    }

    // 나의 모든 쿠폰 조회
    @Transactional(readOnly = true)
    public List<CouponSimpleResponseDto> getAllMyCoupon(String token) {
        User user = orderUserFacade.findUserByToken(token);
        return couponRepository.findByUser(user).stream()
                .map(couponFacade::toSimpleDto)
                .toList();
    }
    @Transactional(readOnly = true)
    public CouponDetailResponseDto getCouponDetail(String token, Long couponId) {
        User user = orderUserFacade.findUserByToken(token);
        Coupon coupon = couponFacade.findByCouponId(couponId);
        if (!coupon.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("해당 쿠폰의 상세내역을 조회할 권한이 없습니다. ");
        }
        return couponFacade.toDetailDto(coupon, user, coupon.getItem());
    }

    // 예시로 플래그 값을 업데이트하는 메서드
    @Transactional
    public Coupon updateCouponStatus(Long couponId, Boolean buyFlag) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new IllegalArgumentException("Invalid coupon ID"));
        coupon.setBuyFlag(buyFlag);
        return couponRepository.save(coupon);
    }

    // 쿠폰 삭제 메서드
    @Transactional
    public void deleteCoupon(Long couponId) {
        if (!couponRepository.existsById(couponId)) {
            throw new IllegalArgumentException("Invalid coupon ID");
        }
        couponRepository.deleteById(couponId);
    }
}