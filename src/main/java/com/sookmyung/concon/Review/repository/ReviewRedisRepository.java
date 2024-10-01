package com.sookmyung.concon.Review.repository;

import com.sookmyung.concon.Review.dto.ReviewRedisDto;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ReviewRedisRepository {
    private RedisTemplate<String, Object> redisTemplate;
    private static final String REVIEW_KEY = "reviews";

    public void save(ReviewRedisDto dto) {
        String reviewId = UUID.randomUUID().toString();
        redisTemplate.opsForHash().put(REVIEW_KEY, reviewId, dto);
    }

    public int size() {
        return redisTemplate.opsForHash().size(REVIEW_KEY).intValue();
    }

    public List<ReviewRedisDto> getAndClearReviewsDtoList() {
        List<ReviewRedisDto> reviews = redisTemplate.opsForHash().values(REVIEW_KEY)
                .stream()
                .map(obj -> (ReviewRedisDto) obj)
                .toList();

        redisTemplate.delete(REVIEW_KEY);

        return reviews;
    }
}
