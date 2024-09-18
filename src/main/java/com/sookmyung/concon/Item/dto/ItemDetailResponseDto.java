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
    private int kcal;
    private String info;
    private String brand;
    private String imageUrl;

    public static ItemDetailResponseDto toDto(Item item) {
        return ItemDetailResponseDto.builder()
                .itemId(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .kcal(item.getKcal())
                .info(item.getInfo())
                .brand(item.getBrand())
                .imageUrl(item.getImageUrl())
                .build();
    }
}
