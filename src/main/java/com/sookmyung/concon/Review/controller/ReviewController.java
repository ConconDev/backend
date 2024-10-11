package com.sookmyung.concon.Review.controller;

import com.sookmyung.concon.Review.configuration.chunk.ReviewItemReader;
import com.sookmyung.concon.Review.dto.ReviewCreateRequestDto;
import com.sookmyung.concon.Review.dto.ReviewRedisDto;
import com.sookmyung.concon.Review.dto.ReviewUpdateRequestDto;
import com.sookmyung.concon.Review.entity.Review;
import com.sookmyung.concon.Review.repository.ReviewRedisRepository;
import com.sookmyung.concon.Review.service.ReviewBatchService;
import com.sookmyung.concon.Review.service.ReviewService;
import com.sookmyung.concon.User.Jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "후기")
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final JobLauncher jobLauncher;
    private final Job reviewCreateJob;
    private final ReviewItemReader itemReader;
    private final ReviewRedisRepository reviewRedisRepository;
    private final ReviewService reviewService;
    private final ReviewBatchService reviewBatchService;

    @Operation(summary = "리뷰 10개 생성 시 한꺼번에 저장 batch")
    @PostMapping("/reviews/batch")
    public ResponseEntity<?> createReviewBatch(
            @RequestBody ReviewCreateRequestDto request,
            @RequestHeader("Authorization") String token) {

        try {
            reviewBatchService.createReview(request, token);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "후기 작성")
    @PostMapping
    public ResponseEntity<?> createReview(
            @RequestBody ReviewCreateRequestDto reviewRequest,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(reviewService.createReview(reviewRequest, token));
    }

    @Operation(summary = "후기 id로 상세 조회")
    @GetMapping("/{reviewId}")
    public ResponseEntity<?> getReviewById(@PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.getReviewById(reviewId));
    }

    @Operation(summary = "상품 별 후기 조회")
    @GetMapping("/item/{itemId}")
    public ResponseEntity<?> getReviewsByItem(@PathVariable Long itemId) {
        return ResponseEntity.ok(reviewService.getReviewsByItem(itemId));
    }

    @Operation(summary = "나의 모든 후기 조회")
    @GetMapping("/my")
    public ResponseEntity<?> getMyReviews(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(reviewService.getMyReviews(token));
    }

    @Operation(summary = "유저 별 후기 조회")
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getReviewsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
    }

    @Operation(summary = "후기 전체 조회")
    @GetMapping("/all")
    public ResponseEntity<?> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @Operation(summary = "후기 수정")
    @PutMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(@RequestBody ReviewUpdateRequestDto updateRequest) {
        return ResponseEntity.ok(reviewService.updateReview(updateRequest));
    }

    @Operation(summary = "후기 삭제")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}