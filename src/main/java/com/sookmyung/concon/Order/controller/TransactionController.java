package com.sookmyung.concon.Order.controller;

import com.sookmyung.concon.Order.dto.OrderDetailResponseDto;
import com.sookmyung.concon.Order.dto.OrderRequestResponseDto;
import com.sookmyung.concon.Order.dto.TransactionAcceptRequestDto;
import com.sookmyung.concon.Order.service.TransactionService;
import com.sookmyung.concon.User.dto.UserSimpleResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "거래 API")
@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    // 거래 요청
    @Operation(summary = "거래 요청", description = "거래를 원하는 상품에게 거래 요청 -> 거래 요청 대기 목록으로 들어감")
    @PostMapping("/request/{order-id}")
    public ResponseEntity<OrderRequestResponseDto> requestOrder(
            @PathVariable("order-id") Long orderId,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(transactionService.requestOrder(orderId, token));
    }

    // 거래 요청 전체 조회
    @Operation(summary = "거래 요청 전체 조회", description = "상품을 클릭하면 해당 상품 거래 요청 대기목록을 확인할 수 있음")
    @GetMapping("/request/{order-id}")
    public ResponseEntity<List<UserSimpleResponseDto>> getAllRequestOrder(
            @PathVariable("order-id") Long orderId) {
        return ResponseEntity.ok(transactionService.getAllRequestOrder(orderId));
    }

    // 거래 수락 (거래 중)
    @Operation(summary = "거래 수락 -> 거래 중", description = "대기 목록 중 하나를 누르면 거래를 수락하고 거래 중으로 바뀜, 거래 중이면 다른 사람은 거래 요청을 할 수 없음")
    @PostMapping("/accept")
    public ResponseEntity<OrderDetailResponseDto> acceptTransaction(
            @RequestBody TransactionAcceptRequestDto request) {
        return ResponseEntity.ok(transactionService.acceptTransaction(request));
    }

    // 거래 중 취소
    @Operation(summary = "거래 중 취소", description = "거래 중, 입금을 안하거나 다른 사유가 있어서 거래 취소를 할 수 있음")
    @PostMapping("/cancel/{order-id}")
    public ResponseEntity<OrderDetailResponseDto> cancelTransaction(
            @PathVariable("order-id") Long orderId) {
        return ResponseEntity.ok(transactionService.cancelTransaction(orderId));
    }

    // 거래 완료
    @Operation(summary = "거래 완료", description = "거래 중, 입금을 확인하고 거래가 완료되면 거래 상태를 완료로 전환")
    @PostMapping("/complete/{order-id}")
    public ResponseEntity<OrderDetailResponseDto> completeTransaction(
            @PathVariable("order-id") Long orderId) {
        return ResponseEntity.ok(transactionService.completeTransaction(orderId));
    }


    @PostMapping("/transaction/{order-id}")
    public ResponseEntity<?> transaction(
            @PathVariable("order-id") Long orderId) {
        return ResponseEntity.ok(transactionService.transaction(orderId));
    }
}
