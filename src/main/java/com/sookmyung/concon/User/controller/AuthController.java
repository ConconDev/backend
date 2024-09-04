package com.sookmyung.concon.User.controller;

import com.sookmyung.concon.User.dto.LoginRequestDto;
import com.sookmyung.concon.User.dto.UserCreateRequestDto;
import com.sookmyung.concon.User.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequestDto request) {
        return ResponseEntity.ok("JWT Token");
    }

    @PostMapping("/signUp")
    public ResponseEntity<Long> signUp(
            @RequestBody UserCreateRequestDto request) {
        return ResponseEntity.ok(authService.join(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("JWT Logout");
    }
}
