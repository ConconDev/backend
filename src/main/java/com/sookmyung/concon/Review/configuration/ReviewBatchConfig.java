package com.sookmyung.concon.Review.configuration;

import com.sookmyung.concon.Review.configuration.chunk.ReviewItemProcessor;
import com.sookmyung.concon.Review.configuration.chunk.ReviewItemReader;
import com.sookmyung.concon.Review.configuration.chunk.ReviewItemWriter;
import com.sookmyung.concon.Review.dto.ReviewRedisDto;
import com.sookmyung.concon.Review.entity.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@Slf4j
@RequiredArgsConstructor
public class ReviewBatchConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job reviewCreateJob(Step reviewCreateStep) {
        return new JobBuilder("reviewCreateJob", jobRepository)
                .start(reviewCreateStep)
                .build();
    }

    @Bean
    public Step reviewCreateStep(ReviewItemReader reader, ReviewItemProcessor processor,
                                 ReviewItemWriter writer) {
        return new StepBuilder("reviewCreateStep", jobRepository)
                .<ReviewRedisDto, Review>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }


}
