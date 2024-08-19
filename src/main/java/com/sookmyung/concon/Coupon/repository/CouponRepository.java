package com.sookmyung.concon.Coupon.repository;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.User.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> findByUser(User user);
}
