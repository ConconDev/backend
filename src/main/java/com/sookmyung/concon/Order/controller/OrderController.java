package com.sookmyung.concon.Order.controller;

import com.sookmyung.concon.Order.dto.*;
import com.sookmyung.concon.Order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(request));
    }

    // 거래 아이디로 단일 조회
    @GetMapping("/{order-id}")
    public ResponseEntity<OrderDetailResponseDto> getOrderByOrderId(
            @PathVariable("order-id") Long orderId) {
        return ResponseEntity.ok(orderService.getOrderByOrderId(orderId));
    }

    // 나의 판매 상품 전체 조회
    @GetMapping("/all")
    public ResponseEntity<List<OrderSimpleResponseDto>> getAllOrders(
            @RequestHeader("Authorization") String token,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(orderService.getAllOrders(token, page, size));
    }

    // 나의 판매 상품 조회(진행중)
    @GetMapping("/all/available")
    public ResponseEntity<List<OrderSimpleResponseDto>> getAllOrdersAvailable(
            @RequestHeader("Authorization") String token,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(orderService.getAllOrdersAvailable(token, page, size));
    }

    // 나의 판매 상품 조회(완료)
    @GetMapping("/all/complete")
    public ResponseEntity<List<OrderSimpleResponseDto>> getAllOrdersComplete(
            @RequestHeader("Authorization") String token,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(orderService.getAllOrdersComplete(token, page, size));
    }

    // 아이템 종류로 조회 (판매중)
    @GetMapping("/item/{item-id}")
    public ResponseEntity<List<OrderSimpleResponseDto>> getAllOrdersByItemId(
            @PathVariable("item-id") Long itemId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(orderService.getAllOrdersByItemId(itemId, page, size));
    }

    // 거래 수정
    @PutMapping
    public ResponseEntity<OrderDetailResponseDto> modifyOrder(
            @RequestBody OrderModifyRequestDto request) {
        return ResponseEntity.ok(orderService.modifyOrder(request));
    }

    // 거래 삭제
    @DeleteMapping("{order-id}")
    public ResponseEntity<Object> deleteOrder(
            @PathVariable("order-id") Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
