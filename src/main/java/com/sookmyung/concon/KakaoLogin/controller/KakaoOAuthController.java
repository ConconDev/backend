package com.sookmyung.concon.KakaoLogin.controller;

import com.sookmyung.concon.KakaoLogin.dto.KakaoTokenResponse;
import com.sookmyung.concon.KakaoLogin.dto.KakaoUserInfoResponse;
import com.sookmyung.concon.KakaoLogin.service.KakaoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/api/kakao")
@RequiredArgsConstructor
public class KakaoOAuthController {
    private final KakaoService kakaoService;

    @Value("${KAKAO_CLIENT_ID}")
    private String CLIENT_ID;

    @Value("${KAKAO_REDIRECT_URI}")
    private String REDIRECT_URI;

//    @Value("${KAKAO_REST_API_KEY}")
//    private String REST_API_KEY;

    @GetMapping
    public String index(Model model) {
        System.out.println("접근 완료");
        model.addAttribute("kakaoClientId", CLIENT_ID);
        model.addAttribute("kakaoRedirectUri", REDIRECT_URI);
        return "loginForm";
    }

    @Operation(description = "회원이 소셜 로그인을 마치면 자동으로 실행되는 API입니다. " +
            "인가 코드를 이용해 토큰을 받고, 해당 토큰으로 사용자 정보를 조회합니다." +
            "사용자 정보를 이용하여 서비스에 회원가입합니다")
    @GetMapping("/oauth")
    public String kakaoOauth(@RequestParam("code") String code) {
        KakaoTokenResponse kakaoTokenResponse = kakaoService.getToken(code);
        KakaoUserInfoResponse userInfo = kakaoService.getUserInfo(kakaoTokenResponse.getAccess_token());
        return "Okay";
    }

    @GetMapping("/callback")
    public ResponseEntity<?> callback(String code) {
        return ResponseEntity.ok(code);
    }
}
