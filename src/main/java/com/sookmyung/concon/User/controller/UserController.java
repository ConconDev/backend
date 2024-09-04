package com.sookmyung.concon.User.controller;

import com.sookmyung.concon.User.dto.UserDetailResponseDto;
import com.sookmyung.concon.User.dto.UserModifyRequestDto;
import com.sookmyung.concon.User.dto.UserSimpleResponseDto;
import com.sookmyung.concon.User.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    // 나의 정보 조회
    @GetMapping
    public ResponseEntity<UserDetailResponseDto> getUserInfo(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.getUserInfo(token));
    }

    // id로 회원 정보 조회
    @GetMapping("/{user_id}")
    public ResponseEntity<UserDetailResponseDto> getUserInfoById(
            @PathVariable("user_id") Long userId) {
        return ResponseEntity.ok(userService.getUserInfoById(userId));
    }

    // 전체 회원 조회
    @GetMapping("/all")
    public ResponseEntity<List<UserSimpleResponseDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    // 회원 정보 수정
    @PutMapping
    public ResponseEntity<Long> modifyUser(
            @RequestHeader("Authorization") String token,
            @RequestBody UserModifyRequestDto request) {
        return ResponseEntity.ok(userService.modifyUser(token, request));
    }

    // 회원 탈퇴
    @DeleteMapping
    public ResponseEntity<Object> deleteUser(
            @RequestHeader("Authorization") String token) {
        userService.deleteUser(token);
        return ResponseEntity.noContent().build();
    }
}
