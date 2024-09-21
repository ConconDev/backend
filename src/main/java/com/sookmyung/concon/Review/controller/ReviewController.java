package com.sookmyung.concon.Review.controller;

import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.Item.service.ItemService;
import com.sookmyung.concon.Review.dto.ReviewRequest;
import com.sookmyung.concon.Review.dto.ReviewUpdateRequest;
import com.sookmyung.concon.Review.entity.Review;
import com.sookmyung.concon.Review.service.ReviewService;
import com.sookmyung.concon.User.Entity.User;
import com.sookmyung.concon.User.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Operation(summary = "후기 작성")
    @PostMapping
    public ResponseEntity<Void> createReview(@RequestBody ReviewRequest reviewRequest) {
        Long userId = reviewRequest.getUserId();
        Long itemId = reviewRequest.getItemId();
        double score = reviewRequest.getScore();
        String content = reviewRequest.getContent();

        Review review = Review.builder()
                .userId(userId)
                .itemId(itemId)
                .score(score)
                .content(content)
                .build();

        reviewService.createReview(review);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "상품 별 후기 조회")
    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<Review>> getReviewsByItem(@PathVariable Long itemId) {
        List<Review> reviews = reviewService.getReviewsByItem(itemId);
        return ResponseEntity.ok(reviews);
    }

    @Operation(summary = "내가 쓴 후기 조회")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable Long userId) {
        List<Review> reviews = reviewService.getReviewsByUser(userId);
        return ResponseEntity.ok(reviews);
    }

    @Operation(summary = "후기 상세 조회")
    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(review);
    }

    @Operation(summary = "후기 수정")
    @PutMapping("/{reviewId}")
    public ResponseEntity<Void> updateReview(@PathVariable Long reviewId, @RequestBody ReviewUpdateRequest updateRequest) {
        double score = updateRequest.getScore();
        String content = updateRequest.getContent();

        Review updatedReview = new Review();
        updatedReview.setScore(score);
        updatedReview.setContent(content);

        reviewService.updateReview(reviewId, updatedReview);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "후기 삭제")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok().build();
    }
}