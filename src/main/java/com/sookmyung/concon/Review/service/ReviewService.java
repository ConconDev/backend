package com.sookmyung.concon.Review.service;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Coupon.repository.CouponRepository;
import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.Item.service.ItemFacade;
import com.sookmyung.concon.Review.dto.ReviewCreateRequestDto;
import com.sookmyung.concon.Review.dto.ReviewDetailResponseDto;
import com.sookmyung.concon.Review.dto.ReviewSimpleResponseDto;
import com.sookmyung.concon.Review.dto.ReviewUpdateRequestDto;
import com.sookmyung.concon.Review.entity.Review;
import com.sookmyung.concon.Review.repository.ReviewRepository;
import com.sookmyung.concon.User.Entity.User;
import com.sookmyung.concon.User.service.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final UserFacade userFacade;
    private final ReviewRepository reviewRepository;
    private final CouponRepository couponRepository;
    private final ItemFacade itemFacade;

    private final Pageable PAGEABLE = PageRequest.of(0, 10);

    public ReviewDetailResponseDto toDetail(Review review) {
        return ReviewDetailResponseDto.toDto(review, userFacade.toSimpleDto(review.getUser()),
                itemFacade.toSimpleDto(review.getCoupon().getItem()));
    }

    private Review findReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 조회할 수 없습니다. "));
    }

    // 후기 작성
    @Transactional
    public ReviewDetailResponseDto createReview(ReviewCreateRequestDto reviewRequest, String token) {
        User user = userFacade.findUserByToken(token);
        Coupon coupon = couponRepository.findById(reviewRequest.getCouponId())
                .orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 조회할 수 없습니다. "));
        Review review = reviewRequest.toEntity(user, coupon);

        reviewRepository.save(review);

        return toDetail(review);
    }

    // 리뷰 id로 단일 조회
    @Transactional(readOnly = true)
    public ReviewDetailResponseDto getReviewById(Long reviewId) {
        return toDetail(reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found")));
    }

    // 상품 별 후기 조회
    @Transactional(readOnly = true)
    public List<ReviewSimpleResponseDto> getReviewsByItem(Long itemId) {
        return reviewRepository.findAllByItemId(itemId, PAGEABLE)
                .stream().map(ReviewSimpleResponseDto::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<ReviewSimpleResponseDto> getMyReviews(String token) {
        User user = userFacade.findUserByToken(token);
        return reviewRepository.findAllByUserId(user.getId(), PAGEABLE)
                .stream().map(ReviewSimpleResponseDto::toDto).toList();
    }

    // 유저 별 후기 조회
    @Transactional(readOnly = true)
    public List<ReviewSimpleResponseDto> getReviewsByUser(Long userId) {
        return reviewRepository.findAllByUserId(userId, PAGEABLE)
                .stream().map(ReviewSimpleResponseDto::toDto).toList();
    }

    // 리뷰 전체 조회
    @Transactional(readOnly = true)
    public List<ReviewSimpleResponseDto> getAllReviews() {
        return reviewRepository.findAll(PAGEABLE)
                .stream().map(ReviewSimpleResponseDto::toDto).toList();
    }

    // 후기 수정
    @Transactional
    public ReviewDetailResponseDto updateReview(ReviewUpdateRequestDto updateRequest) {
        Review review = findReviewById(updateRequest.getReviewId());
        review.update(updateRequest.getScore(), updateRequest.getContent());
        return toDetail(reviewRepository.save(review));
    }

    // 후기 삭제
    @Transactional
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}