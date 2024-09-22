package com.sookmyung.concon.Item.service;

import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.Item.dto.ItemDetailResponseDto;
import com.sookmyung.concon.Item.dto.ItemSimpleResponseDto;
import com.sookmyung.concon.Item.repository.ItemRepository;
import com.sookmyung.concon.Photo.service.PhotoFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemFacade {
    private final ItemRepository itemRepository;
    private final PhotoFacade photoFacade;

    public Item findItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이템을 조회할 수 없습니다. "));
    }

    public Item findItemByName(String itemName) {
        return itemRepository.findByName(itemName)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이템을 조회할 수 없습니다. "));
    }

    public ItemSimpleResponseDto toSimpleDto(Item item) {
        String itemImageUrl = photoFacade.getItemPhotoUrl(item);
        return ItemSimpleResponseDto.toDto(item, itemImageUrl);
    }

    public ItemDetailResponseDto toDetailDto(Item item) {
        String itemImageUrl = photoFacade.getItemPhotoUrl(item);
        String itemVideoUrl = photoFacade.getItemVideoUrl(item);
        return ItemDetailResponseDto.toDto(item, itemImageUrl, itemVideoUrl);
    }
}
