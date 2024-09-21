package com.sookmyung.concon.Review.controller;

import com.sookmyung.concon.Review.entity.Review;
import com.sookmyung.concon.Review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // 후기 작성
    @PostMapping
    public ResponseEntity<Void> createReview(@RequestBody Review review) {
        reviewService.createReview(review);
        return ResponseEntity.ok().build();
    }

    // 상품 별 후기 조회
    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<Review>> getReviewsByItem(@PathVariable Long itemId) {
        List<Review> reviews = reviewService.getReviewsByItem(itemId);
        return ResponseEntity.ok(reviews);
    }

    // 내가 쓴 후기 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable Long userId) {
        List<Review> reviews = reviewService.getReviewsByUser(userId);
        return ResponseEntity.ok(reviews);
    }

    // 후기 상세 조회
    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(review);
    }

    // 후기 수정
    @PutMapping("/{reviewId}")
    public ResponseEntity<Void> updateReview(@PathVariable Long reviewId, @RequestBody Review updatedReview) {
        reviewService.updateReview(reviewId, updatedReview);
        return ResponseEntity.ok().build();
    }

    // 후기 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok().build();
    }

```

}