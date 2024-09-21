package com.sookmyung.concon.Coupon.service;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Coupon.dto.CouponCreateRequestDto;
import com.sookmyung.concon.Coupon.dto.CouponDetailResponseDto;
import com.sookmyung.concon.Coupon.dto.CouponSimpleResponseDto;
import com.sookmyung.concon.Coupon.repository.CouponRepository;
import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.Item.repository.ItemRepository;
import com.sookmyung.concon.User.Entity.User;
import com.sookmyung.concon.User.Jwt.JwtUtil;
import com.sookmyung.concon.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    private final JwtUtil jwtUtil;

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학생을 조회할 수 없습니다. "));
    }

    private Item findItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이템을 조회할 수 없습니다. "));
    }

    // 토큰으로 사용자 찾기
    private User findUserByToken(String token) {
        return userRepository.findByEmail(jwtUtil.getEmail(token.split(" ")[1]))
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 조회할 수 없습니다."));
    }

    @Transactional
    public CouponDetailResponseDto saveCoupon(String token, CouponCreateRequestDto request) {

        User user = findUserByToken(token);
        Item item = findItemById(request.getItemId());
        Coupon coupon = request.toEntity(user, item);

        couponRepository.save(coupon);

        // TODO : 이미지 처리
        return CouponDetailResponseDto.toDto(coupon, null, null);
    }

    // 나의 모든 쿠폰 조회
    @Transactional(readOnly = true)
    public List<CouponSimpleResponseDto> getAllMyCoupon(String token) {
        User user = findUserByToken(token);
        return couponRepository.findByUser(user).stream()
                .map(coupon -> CouponSimpleResponseDto.toDto(coupon, coupon.getUsedDate() != null))
                .toList();
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