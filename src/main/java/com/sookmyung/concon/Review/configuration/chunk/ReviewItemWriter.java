package com.sookmyung.concon.Review.configuration.chunk;

import com.sookmyung.concon.Review.entity.Review;
import com.sookmyung.concon.Review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewItemWriter implements ItemWriter<Review> {
    private final ReviewRepository reviewRepository;
    @Override
    public void write(Chunk<? extends Review> reviews) throws Exception {
        reviewRepository.saveAll(reviews);
    }
}
