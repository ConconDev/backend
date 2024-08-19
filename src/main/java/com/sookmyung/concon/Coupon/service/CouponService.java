package com.sookmyung.concon.Coupon.service;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Coupon.dto.CouponRequestDto;
import com.sookmyung.concon.Coupon.dto.CouponDetailResponseDto;
import com.sookmyung.concon.Coupon.dto.CouponSimpleResponseDto;
import com.sookmyung.concon.Coupon.repository.CouponRepository;
import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.Item.repository.ItemRepository;
import com.sookmyung.concon.User.Entity.User;
import com.sookmyung.concon.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학생을 조회할 수 없습니다. "));
    }

    private Item findItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이템을 조회할 수 없습니다. "));
    }

    // TODO : 수정 예정
    private User findUserByToken(String token) {
        return null;
    }

    public CouponDetailResponseDto saveCoupon(CouponRequestDto request) {

        User user = findUserById(request.getUserId());
        Item item = findItemById(request.getItemId());
        Coupon coupon = request.toEntity(user, item);

        // 기본 값 설정
        coupon.setBuyFlag(false);

        couponRepository.save(coupon);

        // TODO : 이미지 처리
        return CouponDetailResponseDto.toDto(coupon, null, null);
    }

    // 나의 모든 쿠폰 조회
    public List<CouponSimpleResponseDto> getAllMyCoupon(String token) {
        User user = findUserByToken(token);
        return couponRepository.findByUser(user).stream()
                .map(coupon -> CouponSimpleResponseDto.toDto(coupon, coupon.getUsedDate() != null))
                .toList();
    }

    // 예시로 플래그 값을 업데이트하는 메서드
    public Coupon updateCouponStatus(Long couponId, Boolean buyFlag) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new IllegalArgumentException("Invalid coupon ID"));
        coupon.setBuyFlag(buyFlag);
        return couponRepository.save(coupon);
    }

    // 쿠폰 삭제 메서드
    public void deleteCoupon(Long couponId) {
        if (!couponRepository.existsById(couponId)) {
            throw new IllegalArgumentException("Invalid coupon ID");
        }
        couponRepository.deleteById(couponId);
    }
}
