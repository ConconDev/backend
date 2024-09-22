package com.sookmyung.concon.BootPay.controller;

import com.sookmyung.concon.BootPay.dto.BootPayConfirmRequestDTO;
import com.sookmyung.concon.BootPay.service.BootPayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "본인인증")
@RestController
@RequestMapping("/api/bootpay")
@RequiredArgsConstructor
public class BootPayController {
    private final BootPayService bootPayService;

    @Operation(summary = "토큰 가져올 수 있는지 확인")
    @PostMapping("/check")
    public ResponseEntity<?> priceCheck(
            @RequestHeader("Authorization") String token,
            @RequestBody BootPayConfirmRequestDTO request) {
        return ResponseEntity.ok(bootPayService.priceCheck(token, request));
    }

    @GetMapping("/certificate/{receipt_id}")
    public ResponseEntity<?> getCertificate(
            @RequestHeader("Authorization") String token,
            @PathVariable("receipt_id") String receiptId) {
        bootPayService.certificate(token, receiptId);
        return ResponseEntity.ok().build();
    }
}
