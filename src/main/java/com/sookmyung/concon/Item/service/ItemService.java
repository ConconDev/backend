package com.sookmyung.concon.Item.service;

import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.Item.dto.*;
import com.sookmyung.concon.Item.repository.ItemRepository;
import com.sookmyung.concon.Photo.service.PhotoFacade;
import com.sookmyung.concon.Photo.service.PhotoManager;
import com.sookmyung.concon.Photo.service.PhotoService;
import com.sookmyung.concon.User.Entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemFacade itemFacade;
    private final PhotoFacade photoFacade;

    // 모든 아이템 조회
    @Transactional(readOnly = true)
    public List<ItemSimpleResponseDto> getAllItems() {
        return itemRepository.findAll().stream()
                .map(itemFacade::toSimpleDto)
                .toList();
    }

    // 키워드로 아이템 검색
    @Transactional(readOnly = true)
    public List<ItemSimpleResponseDto> getItemsByKeyword(String keyword) {
        return itemRepository.findByNameContaining(keyword)
                .stream().map(itemFacade::toSimpleDto).toList();
    }

    // 아이디로 아이템 조회
    @Transactional(readOnly = true)
    public ItemDetailResponseDto getItemById(Long itemId) {
        Item item = itemFacade.findItemById(itemId);
        return itemFacade.toDetailDto(item);
    }

    // Item 이름으로 조회
    @Transactional(readOnly = true)
    public ItemDetailResponseDto getItemByName(String name) {
        Item item = itemFacade.findItemByName(name);
        return itemFacade.toDetailDto(item);
    }

    // 카테고리로 아이템 조회
    @Transactional(readOnly = true)
    public List<ItemSimpleResponseDto> getItemByCategory(String category) {
        return itemFacade.findItemsByCategory(category);
    }

    // 아이템 저장 (test 용)
//    @Transactional
//    public ItemCreateResponseDto saveItem(ItemCreateDto request) {
//
//        Item item = request.toEntity();
//        itemRepository.save(item);
//        String createUrl = photoFacade.uploadItemPhoto(item, request.getImageName());
//        item.uploadImagePath(createUrl);
//
//        return ItemCreateResponseDto.toDto(item, createUrl);
//    }

    // 동영상 저장
    @Transactional
    public ItemDetailResponseDto updateVideoUrl(ItemVideoCreateRequestDto request) {
        Item item = itemFacade.findItemById(request.getItemId());

        item.updateVideo(request.getVideoUrl());

        return itemFacade.toDetailDto(item);
    }

    // 아이템 삭제 (test 용)
    @Transactional
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}