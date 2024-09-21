package com.sookmyung.concon.Item.dto;

import com.sookmyung.concon.Item.Entity.Item;
import lombok.Builder;
import lombok.Getter;

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

    public static ItemDetailResponseDto toDto(Item item, String imageUrl, String videoUrl) {
        return ItemDetailResponseDto.builder()
                .itemId(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .description(item.getDescription())
                .brand(item.getBrand())
                .imageUrl(imageUrl)
                .videoUrl(videoUrl)
                .category(item.getCategory())
                .build();
    }
}
