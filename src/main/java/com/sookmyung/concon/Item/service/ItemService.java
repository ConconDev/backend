package com.sookmyung.concon.Item.service;

import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.Item.dto.ItemDetailResponseDto;
import com.sookmyung.concon.Item.dto.ItemSimpleResponseDto;
import com.sookmyung.concon.Item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    // 모든 아이템 조회
    public List<ItemSimpleResponseDto> getAllItems() {
        return itemRepository.findAll().stream()
                .map(ItemSimpleResponseDto::toDto)
                .toList();
    }

    // 아이디로 아이템 조회
    public ItemDetailResponseDto getItemById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이템이 존재하지 않습니다."));
        return ItemDetailResponseDto.toDto(item);
    }

    // Item 이름으로 조회
    public ItemDetailResponseDto getItemByName(String name) {
        Item item = itemRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("해당 item을 찾을 수 없습니다. "));
        return ItemDetailResponseDto.toDto(item);
    }

    // 아이템 저장 (test 용)
    public ItemDetailResponseDto saveItem(Item item) {
        Item savedItem = itemRepository.save(item);
        return ItemDetailResponseDto.toDto(savedItem);
    }

    // 아이템 삭제 (test 용)
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}