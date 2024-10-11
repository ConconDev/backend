package com.sookmyung.concon.Review.configuration.chunk;

import com.sookmyung.concon.Review.dto.ReviewCreateRequestDto;
import com.sookmyung.concon.Review.dto.ReviewRedisDto;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewItemReader implements ItemReader<ReviewRedisDto> {
    private List<ReviewRedisDto> reviewRedisDtoList;
    private int nextIndex;

    public void setReviewRedisDtoList(List<ReviewRedisDto> reviewRedisDtoList) {
        this.reviewRedisDtoList = reviewRedisDtoList;
        this.nextIndex = 0;
    }

    @Override
    public ReviewRedisDto read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (nextIndex < reviewRedisDtoList.size()) {
            return reviewRedisDtoList.get(nextIndex++);
        }
        return null;
    }
}
