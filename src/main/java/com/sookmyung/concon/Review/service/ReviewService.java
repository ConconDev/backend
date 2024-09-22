package com.sookmyung.concon.Review.service;

import com.sookmyung.concon.Review.entity.Review;
import com.sookmyung.concon.Review.repository.ReviewRepository;
import com.sookmyung.concon.User.Entity.User;
import com.sookmyung.concon.Item.Entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RedisTemplate<String, Review> redisTemplate;

    // 후기 작성
    @Async
    public void createReview(Review review) {
        // Redis에 캐싱
        redisTemplate.opsForValue().set("review:" + review.getReviewId(), review);

        // 배치 처리로 RDB에 저장
        saveReviewToDatabase(review);
    }

    @Transactional
    private void saveReviewToDatabase(Review review) {
        reviewRepository.save(review);
    }

    // 상품 별 후기 조회
    public List<Review> getReviewsByItem(Long itemId) {
        // Redis에서 조회
        return reviewRepository.findAllByItemId(itemId);
    }

    // 내가 쓴 후기 조회
    public List<Review> getReviewsByUser(Long userId) {
        return reviewRepository.findAllByUserId(userId);
    }

    // 후기 수정
    @Async
    public void updateReview(Long reviewId, Review updatedReview) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        // 후기 내용 업데이트
        review.setScore(updatedReview.getScore());
        review.setContent(updatedReview.getContent());

        // Redis 업데이트
        redisTemplate.opsForValue().set("review:" + reviewId, review);

        // RDB 업데이트
        reviewRepository.save(review);
    }


    // 후기 삭제
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);

        // Redis 삭제
        redisTemplate.delete("review:" + reviewId);
    }

    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }
}