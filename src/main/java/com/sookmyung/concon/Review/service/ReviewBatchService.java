package com.sookmyung.concon.Review.service;

import com.sookmyung.concon.Review.configuration.chunk.ReviewItemReader;
import com.sookmyung.concon.Review.dto.ReviewCreateRequestDto;
import com.sookmyung.concon.Review.dto.ReviewRedisDto;
import com.sookmyung.concon.Review.repository.ReviewRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewBatchService {
    private final ReviewRedisRepository reviewRedisRepository;
    private final ReviewItemReader reviewItemReader;
    private final JobLauncher jobLauncher;
    private final Job reviewCreateJob;

    public void createReview(ReviewCreateRequestDto request, String token) throws Exception{
        ReviewRedisDto dto = ReviewRedisDto.toDto(request, token);
        reviewRedisRepository.save(dto);

        if (reviewRedisRepository.size() >= 10) {
            runBatchJob();
        }
    }
    private void runBatchJob() throws Exception {
        List<ReviewRedisDto> reviewRedisDtoList = reviewRedisRepository.getAndClearReviewsDtoList();
        reviewItemReader.setReviewRedisDtoList(reviewRedisDtoList);

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(reviewCreateJob, jobParameters);
    }
}
