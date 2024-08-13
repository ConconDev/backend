package com.sookmyung.concon.Coupon.repository;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

}
