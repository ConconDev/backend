package com.sookmyung.concon.Item.controller;

import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.Item.dto.*;
import com.sookmyung.concon.Item.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "아이템 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class ItemController {
    private final ItemService itemService;

    @Operation(summary = "item 전체 조회")
    @GetMapping("/all")
    public ResponseEntity<List<ItemSimpleResponseDto>> getAllItem() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @Operation(summary = "id로 item 단일 조회")
    @GetMapping("/{item_id}")
    public ResponseEntity<ItemDetailResponseDto> getItemById(
            @PathVariable("item_id") Long itemId) {
        return ResponseEntity.ok(itemService.getItemById(itemId));
    }

    @Operation(summary = "이름으로 item 조회")
    @GetMapping
    public ResponseEntity<ItemDetailResponseDto> getItemByName(
            @RequestParam("name") String name) {
        return ResponseEntity.ok(itemService.getItemByName(name));
    }

    @Operation(summary = "이름 키워드로 item 검색")
    @GetMapping("/search")
    public ResponseEntity<List<ItemSimpleResponseDto>> searchItem(
            @RequestParam("name") String name) {
        return ResponseEntity.ok(itemService.getItemsByKeyword(name));
    }

    @Operation(summary = "카테고리로 item 검색")
    @GetMapping("category")
    public ResponseEntity<List<ItemSimpleResponseDto>> getItemByCategory(
            @RequestParam("category") String category) {
        return ResponseEntity.ok(itemService.getItemByCategory(category));
    }

//    @Operation(summary = "item 생성")
//    @PostMapping
//    public ResponseEntity<ItemCreateResponseDto> addItem(
//            @RequestBody ItemCreateDto request) {
//        return ResponseEntity.ok(itemService.saveItem(request));
//    }

    @Operation(summary = "id로 item 삭제")
    @DeleteMapping("/{item_id}")
    public @ResponseBody ResponseEntity<Item> deleteItemById(
            @PathVariable("item_id") Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "item video 추가")
    @PostMapping("/video")
    public ResponseEntity<?> createItemVideo(
            @RequestBody ItemVideoCreateRequestDto request) {
        return ResponseEntity.ok(itemService.updateVideoUrl(request));
    }
}
