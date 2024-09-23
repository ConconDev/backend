package com.sookmyung.concon.Kakao.controller;

import com.sookmyung.concon.Kakao.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/kakao")
@RequiredArgsConstructor
public class KakaoOAuthController {

    @Value("${KAKAO_CLIENT_ID}")
    private String CLIENT_ID;

    @Value("${KAKAO_REDIRECT_URI}")
    private String REDIRECT_URI;

    @GetMapping
    public String index(Model model) {
        System.out.println("접근 완료");
        model.addAttribute("kakaoClientId", CLIENT_ID);
        model.addAttribute("kakaoRedirectUri", REDIRECT_URI);
        return "loginForm";
    }
}
