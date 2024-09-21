package com.sookmyung.concon.Item.service;

import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.Item.dto.ItemCreateDto;
import com.sookmyung.concon.Item.dto.ItemCreateResponseDto;
import com.sookmyung.concon.Item.dto.ItemDetailResponseDto;
import com.sookmyung.concon.Item.dto.ItemSimpleResponseDto;
import com.sookmyung.concon.Item.repository.ItemRepository;
import com.sookmyung.concon.Photo.service.PhotoFacade;
import com.sookmyung.concon.Photo.service.PhotoManager;
import com.sookmyung.concon.Photo.service.PhotoService;
import com.sookmyung.concon.User.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
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

    // 아이템 저장 (test 용)
    @Transactional
    public ItemCreateResponseDto saveItem(ItemCreateDto request) {

        Item item = request.toEntity();
        itemRepository.save(item);
        String createUrl = photoFacade.uploadItemPhoto(item, request.getImageName());
        item.uploadImagePath(createUrl);

        return ItemCreateResponseDto.toDto(item, createUrl);
    }

    // 아이템 삭제 (test 용)
    @Transactional
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
