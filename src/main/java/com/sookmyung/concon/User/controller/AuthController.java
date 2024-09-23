package com.sookmyung.concon.User.controller;

import com.sookmyung.concon.Kakao.dto.KakaoTokenResponse;
import com.sookmyung.concon.Kakao.service.KakaoService;
import com.sookmyung.concon.User.dto.LoginRequestDto;
import com.sookmyung.concon.User.dto.UserCreateRequestDto;
import com.sookmyung.concon.User.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "로그인/아웃 & 회원가입")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    private final KakaoService kakaoService;

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequestDto request) {
        return ResponseEntity.ok("JWT Token");
    }

    @Operation(summary = "카카오 로그인")
    @PostMapping("/kakao/login")
    public ResponseEntity<String> kakaoLogin(
            @RequestParam("email") String email,
            @RequestParam("password") String password) {

        log.info("/kakao/login 리다이랙트는 성공");
        String redirectUrl = UriComponentsBuilder.fromPath("/api/auth/kakao/login")
                .queryParam("email", email)
                .queryParam("password", password)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", redirectUrl);

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @Operation(summary = "카카오 로그인")
    @GetMapping("/kakao/callback")
    public ResponseEntity<LoginRequestDto> kakaoCallback(String code) {
        KakaoTokenResponse token = kakaoService.getToken(code);
        LoginRequestDto loginRequestDto = kakaoService.kakaoLogin(token);

        String redirectUrl = UriComponentsBuilder.fromPath("/api/auth/kakao/login")
                .queryParam("email", loginRequestDto.getEmail())
                .queryParam("password", loginRequestDto.getPassword())
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", redirectUrl);

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
//
//    @Operation(summary = "카카오 콜백 함수")
//    @GetMapping("/kakao/callback")
//    public ResponseEntity<?> kakaoCallback(
//            @RequestParam("code") String code) {
//
//        KakaoTokenResponse token = kakaoService.getToken(code);
//        LoginRequestDto loginRequestDto = kakaoService.kakaoLogin(token);
//        kakaoLogin(loginRequestDto.getEmail(), loginRequestDto.getPassword());
//        log.info("로그인 성공");
//
//        return ResponseEntity.ok(token);
//    }


    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok("JWT Token");
    }

    @Operation(summary = "회원가입")
    @PostMapping("/signUp")
    public ResponseEntity<Long> signUp(
            @RequestBody UserCreateRequestDto request) {
        return ResponseEntity.ok(authService.join(request));
    }

    // 회원 탈퇴
    @Operation(summary = "회원 탈퇴")
    @DeleteMapping
    public ResponseEntity<Object> deleteUser(
            @RequestHeader("Authorization") String token) {
        authService.deleteUser(token);
        return ResponseEntity.noContent().build();
    }
}
