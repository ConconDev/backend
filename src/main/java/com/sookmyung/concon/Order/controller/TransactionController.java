package com.sookmyung.concon.Order.controller;

import com.sookmyung.concon.Order.dto.OrderDetailResponseDto;
import com.sookmyung.concon.Order.dto.OrderRequestResponseDto;
import com.sookmyung.concon.Order.dto.TransactionAcceptRequestDto;
import com.sookmyung.concon.Order.service.TransactionService;
import com.sookmyung.concon.User.dto.UserSimpleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    // 거래 요청
    @PostMapping("/request/{order-id}")
    public ResponseEntity<OrderRequestResponseDto> requestOrder(
            @PathVariable("order-id") Long orderId,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(transactionService.requestOrder(orderId, token));
    }

    // 거래 요청 전체 조회
    @GetMapping("/request/{order-id}")
    public ResponseEntity<List<UserSimpleResponseDto>> getAllRequestOrder(
            @PathVariable("order-id") Long orderId) {
        return ResponseEntity.ok(transactionService.getAllRequestOrder(orderId));
    }

    // 거래 수락 (거래 중)
    @PostMapping("/accept")
    public ResponseEntity<OrderDetailResponseDto> acceptTransaction(
            @RequestBody TransactionAcceptRequestDto request) {
        return ResponseEntity.ok(transactionService.acceptTransaction(request));
    }

    // 거래 중 취소
    @PostMapping("/cancel/{order-id}")
    public ResponseEntity<OrderDetailResponseDto> cancelTransaction(
            @PathVariable("order-id") Long orderId) {
        return ResponseEntity.ok(transactionService.cancelTransaction(orderId));
    }

    // 거래 완료
    @PostMapping("/complete/{order-id}")
    public ResponseEntity<OrderDetailResponseDto> completeTransaction(
            @PathVariable("order-id") Long orderId) {
        return ResponseEntity.ok(transactionService.completeTransaction(orderId));
    }
}
