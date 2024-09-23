package com.sookmyung.concon.Kakao.controller;

import com.sookmyung.concon.Kakao.dto.KakaoTokenResponse;
import com.sookmyung.concon.Kakao.service.KakaoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/kakao")
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoService kakaoService;

    @Operation(summary = "카카오 accessToken 발급", description = "탈퇴 시, 사용자 정보 불러오기 때 사용")
    @GetMapping("/access-token")
    public ResponseEntity<?> getAccessToken() {
        log.info("접속 완료");
        String code = kakaoService.getCode();
        log.info("코드 받기 완료" + code);
        KakaoTokenResponse token = kakaoService.getToken(code);
        log.info("토큰 받기 완료");
        return ResponseEntity.ok(token);
    }
}
