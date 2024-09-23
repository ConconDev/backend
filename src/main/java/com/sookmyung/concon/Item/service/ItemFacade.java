package com.sookmyung.concon.Item.service;

import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.Item.dto.ItemDetailResponseDto;
import com.sookmyung.concon.Item.dto.ItemSimpleResponseDto;
import com.sookmyung.concon.Item.repository.ItemRepository;
import com.sookmyung.concon.Photo.service.PhotoFacade;
import com.sookmyung.concon.Review.dto.ReviewSimpleResponseDto;
import com.sookmyung.concon.Review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemFacade {
    private final ItemRepository itemRepository;
    private final PhotoFacade photoFacade;
    private final ReviewRepository reviewRepository;

    public Item findItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이템을 조회할 수 없습니다. "));
    }

    public Item findItemByName(String itemName) {
        return itemRepository.findByName(itemName)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이템을 조회할 수 없습니다. "));
    }

    public List<ItemSimpleResponseDto> findItemsByCategory(String category) {
        List<Item> byCategory = itemRepository.findByCategoryContaining(category);
        log.info("1번 아이템 조회" + byCategory.size());
        return byCategory.stream().map(this::toSimpleDto).toList();
    }

    public ItemSimpleResponseDto toSimpleDto(Item item) {
        String itemImageUrl = photoFacade.getItemPhotoUrl(item);
        return ItemSimpleResponseDto.toDto(item, itemImageUrl);
    }

    public ItemDetailResponseDto toDetailDto(Item item) {
        String itemImageUrl = photoFacade.getItemPhotoUrl(item);
        List<ReviewSimpleResponseDto> reviews = reviewRepository.findAllByItemId(item.getId(), PageRequest.of(0, 10))
                .stream().map(ReviewSimpleResponseDto::toDto).toList();
        return ItemDetailResponseDto.toDto(item, itemImageUrl, reviews);
    }
}
