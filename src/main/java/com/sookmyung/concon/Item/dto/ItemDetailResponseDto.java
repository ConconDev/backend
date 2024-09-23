package com.sookmyung.concon.Item.dto;

import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.Review.dto.ReviewSimpleResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ItemDetailResponseDto {
    private Long itemId;
    private String name;
    private double price;
    private String description;
    private String brand;
    private String imageUrl;
    private String videoUrl;
    private String category;
    private List<ReviewSimpleResponseDto> reviews;

    public static ItemDetailResponseDto toDto(Item item, String imageUrl, List<ReviewSimpleResponseDto> reviews) {
        return ItemDetailResponseDto.builder()
                .itemId(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .description(item.getDescription())
                .brand(item.getBrand())
                .imageUrl(imageUrl)
                .videoUrl(item.getVideoUrl())
                .category(item.getCategory())
                .reviews(reviews)
                .build();
    }
}