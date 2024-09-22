package com.sookmyung.concon.BootPay.controller;

import com.sookmyung.concon.BootPay.service.BootPayService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "본인인증")
@RestController
@RequiredArgsConstructor
public class BootPayController {
    private final BootPayService bootPayService;

    @GetMapping("/certificate/{receipt_id}")
    public ResponseEntity<Object> getCertificate(
            @RequestHeader("Authorization") String token,
            @PathVariable("receipt_id") String receiptId) {
        return ResponseEntity.ok(bootPayService.certificate(token, receiptId));
    }
}
