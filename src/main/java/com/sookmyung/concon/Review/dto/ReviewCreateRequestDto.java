package com.sookmyung.concon.Review.dto;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Review.entity.Review;
import com.sookmyung.concon.User.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreateRequestDto {
    private Long couponId;
    private double score;
    private String content;

    public Review toEntity(User user, Coupon coupon) {
        return Review.builder()
                .coupon(coupon)
                .score(score)
                .content(content)
                .user(user)
                .build();
    }
}
