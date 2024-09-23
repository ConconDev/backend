package com.sookmyung.concon.Review.repository;

import com.sookmyung.concon.Review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

     @Query("SELECT r FROM Review r " +
             "WHERE r.coupon.item.id = :itemId")
    List<Review> findAllByItemId(Long itemId, Pageable pageable);

    // @Query("SELECT r FROM Review r WHERE r.user.id = ?1")
    List<Review> findAllByUserId(Long userId, Pageable pageable);


    Optional<Review> findFirstByCouponId(Long couponId);

}