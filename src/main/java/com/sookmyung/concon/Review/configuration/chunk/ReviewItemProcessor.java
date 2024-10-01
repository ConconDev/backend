package com.sookmyung.concon.Review.configuration.chunk;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Coupon.service.CouponFacade;
import com.sookmyung.concon.Review.dto.ReviewCreateRequestDto;
import com.sookmyung.concon.Review.dto.ReviewRedisDto;
import com.sookmyung.concon.Review.entity.Review;
import com.sookmyung.concon.User.Entity.User;
import com.sookmyung.concon.User.service.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ReviewItemProcessor implements ItemProcessor<ReviewRedisDto, Review> {
    private final UserFacade userFacade;
    private final CouponFacade couponFacade;

    @Override
    public Review process(ReviewRedisDto dto) throws Exception {

        User user = userFacade.findUserByToken(dto.getToken());
        ReviewCreateRequestDto request = dto.getRequest();
        Coupon coupon = couponFacade.findByCouponId(request.getCouponId());
        return request.toEntity(user, coupon);
    }
}
