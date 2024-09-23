package com.sookmyung.concon.User.dto;

import com.sookmyung.concon.Kakao.dto.KakaoTokenResponse;
import lombok.Data;

@Data
public class LoginResponseDto {
    private String token;
    private KakaoTokenResponse kakaoTokenResponse;
    public LoginResponseDto(KakaoTokenResponse kakaoTokenResponse, String token) {
        this.token = token;
        this.kakaoTokenResponse = kakaoTokenResponse;
    }
}
