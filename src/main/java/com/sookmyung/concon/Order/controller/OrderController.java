package com.sookmyung.concon.Order.controller;

import com.sookmyung.concon.Order.dto.OrderCreateRequestDto;
import com.sookmyung.concon.Order.dto.OrderDetailResponseDto;
import com.sookmyung.concon.Order.dto.OrderSimpleResponseDto;
import com.sookmyung.concon.Order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // 판매 생성
    @PostMapping
    public ResponseEntity<OrderDetailResponseDto> createOrders(
            @RequestHeader("Authorization") String token,
            @RequestBody OrderCreateRequestDto request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    // 나의 판매 상품 전체 조회
    @GetMapping("/all")
    public ResponseEntity<List<OrderSimpleResponseDto>> getAllOrders(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(orderService.getAllOrders(token));
    }

    // 나의 판매 상품 조회(진행중)
    @GetMapping("/all/available")
    public ResponseEntity<List<OrderSimpleResponseDto>> getAllOrdersAvailable(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(orderService.getAllOrdersAvailable(token));
    }

    // 나의 판매 상품 조회(완료)
    @GetMapping("/all/complete")
    public ResponseEntity<List<OrderSimpleResponseDto>> getAllOrdersComplete(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(orderService.getAllOrdersComplete(token));
    }

    // 아이템 종류로 조회 (판매중)
    @GetMapping("/item/{item_id}")
    public ResponseEntity<List<OrderSimpleResponseDto>> getAllOrdersByItemId(
            @PathVariable("item_id") Long itemId) {
        return ResponseEntity.ok(orderService.getAllOrdersByItemId(itemId));
    }

    // 거래 요청 & 수락

    // 거래 수정

    // 거래 삭제
}
