package com.sookmyung.concon.Item.dto;

import com.sookmyung.concon.Item.Entity.Item;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemVideoCreateResponseDto {
    private Long itemId;
    private String videoUploadUrl;

    public static ItemVideoCreateResponseDto toDto(Item item, String videoUploadUrl) {
        return ItemVideoCreateResponseDto.builder()
                .itemId(item.getId())
                .videoUploadUrl(videoUploadUrl)
                .build();
    }
}
