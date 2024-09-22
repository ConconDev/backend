package com.sookmyung.concon.Item.dto;

import com.sookmyung.concon.Item.Entity.Item;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemCreateResponseDto {
    private Long itemId;
    private String imageCreateUrl;

    public static ItemCreateResponseDto toDto(Item item, String imageCreateUrl) {
        return ItemCreateResponseDto.builder()
                .itemId(item.getId())
                .imageCreateUrl(imageCreateUrl)
                .build();
    }
}
