package com.sookmyung.concon.KakaoLogin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/kakao")
public class KakaoOAuthController {
    @Value("${KAKAO_TOKEN_URL}")
    private String KAKAO_TOKEN_URL;

    @Value("${CLIENT_ID}")
    private String CLIENT_ID;

    @Value("${REDIRECT_URI}")
    private String REDIRECT_URI;

//    @PostMapping
//    public ResponseEntity<?> exchangeCodeForToken(
//            @RequestBody Map<String, String> request) {
//        String authCode = request.get("authCode");
//    }
}
