package com.sookmyung.concon.User.controller;

import com.sookmyung.concon.User.dto.UserDetailConfigResponseDto;
import com.sookmyung.concon.User.dto.UserDetailResponseDto;
import com.sookmyung.concon.User.dto.UserModifyRequestDto;
import com.sookmyung.concon.User.dto.UserSimpleResponseDto;
import com.sookmyung.concon.User.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "유저 관련 기능")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserServiceImpl userService;

    // 나의 정보 조회
    @Operation(summary = "나의 정보 상세 조회")
    @GetMapping
    public ResponseEntity<UserDetailConfigResponseDto> getUserInfo(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.getUserInfo(token));
    }

    // id로 회원 정보 조회
    @Operation(summary = "유저 id로 유저 단일 상세조회")
    @GetMapping("/{user_id}")
    public ResponseEntity<UserDetailResponseDto> getUserInfoById(
            @PathVariable("user_id") Long userId) {
        return ResponseEntity.ok(userService.getUserInfoById(userId));
    }

    // 전체 회원 조회
    @Operation(summary = "유저 전체 조회")
    @GetMapping("/all")
    public ResponseEntity<List<UserSimpleResponseDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    // 회원 정보 수정
    @Operation(summary = "회원 정보 수정")
    @PutMapping
    public ResponseEntity<UserDetailConfigResponseDto> modifyUser(
            @RequestHeader("Authorization") String token,
            @RequestBody UserModifyRequestDto request) {
        return ResponseEntity.ok(userService.modifyUser(token, request));
    }

    // 회원 탈퇴
    @Operation(summary = "회원 탈퇴")
    @DeleteMapping
    public ResponseEntity<Object> deleteUser(
            @RequestHeader("Authorization") String token) {
        userService.deleteUser(token);
        return ResponseEntity.noContent().build();
    }
}
