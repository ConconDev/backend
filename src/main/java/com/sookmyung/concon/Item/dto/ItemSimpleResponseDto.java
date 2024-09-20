package com.sookmyung.concon.Item.dto;

import com.sookmyung.concon.Item.Entity.Item;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
    public class ItemSimpleResponseDto {
    private Long itemId;
    private String name;
    private String brand;

    public static ItemSimpleResponseDto toDto(Item item) {
        return ItemSimpleResponseDto.builder()
                .itemId(item.getId())
                .name(item.getName())
                .brand(item.getBrand())
                .build();
    }
}
