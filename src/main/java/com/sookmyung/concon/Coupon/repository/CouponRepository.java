package com.sookmyung.concon.Coupon.repository;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> findByUserIdAndUsedFlag(Long userId, Boolean usedFlag);
    List<Coupon> findByUserId(Long userId);
}
