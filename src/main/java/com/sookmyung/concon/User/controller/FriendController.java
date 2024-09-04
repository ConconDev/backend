package com.sookmyung.concon.User.controller;

import com.sookmyung.concon.User.Entity.Friendship;
import com.sookmyung.concon.User.dto.FriendSimpleResponseDto;
import com.sookmyung.concon.User.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friend")
public class FriendController {
    private final FriendService friendService;
    // 친구 요청
    @PostMapping("/{friend_id}")
    public ResponseEntity<Long> friendRequest(
            @RequestHeader("Authorization") String token,
            @PathVariable("friend_id") Long friendId) {
        return ResponseEntity.ok(friendService.createFriendRequest(token, friendId));
    }

    // 친구 요청 조회(보낸 쪽)
    @GetMapping("/sender")
    public ResponseEntity<List<FriendSimpleResponseDto>> getSenderFriends(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(friendService.getSenderFriends(token));
    }

    // 친구 요청 조회(받는 쪽)
    @GetMapping("/receiver")
    public ResponseEntity<List<FriendSimpleResponseDto>> getReceiverFriends(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(friendService.getReceiverFriends(token));
    }

    // 친구 요청 취소
    @DeleteMapping("/cancel/{friendship_id}")
    public ResponseEntity<Object> cancelFriendRequest(
            @PathVariable("friendship_id") Long friendshipId) {
        friendService.cancelFriendRequest(friendshipId);
        return ResponseEntity.noContent().build();
    }

    // 친구 거절
    @PutMapping("/deny/{friendship_id}")
    public ResponseEntity<FriendSimpleResponseDto> denyFriendRequest(
            @PathVariable("friendship_id") Long friendshipId) {
        return ResponseEntity.ok(friendService.denyFriendRequest(friendshipId));
    }

    // 친구 수락
    @PostMapping("/accept/{friendship_id}")
    public ResponseEntity<List<Friendship>> acceptFriendRequest(
            @PathVariable("friendship_id") Long friendshipId) {
        return ResponseEntity.ok(friendService.acceptFriendRequest(friendshipId));
    }

    // 친구 조회
    @GetMapping
    public ResponseEntity<List<FriendSimpleResponseDto>> getFriends(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(friendService.getFriends(token));
    }

    // 친구 삭제
    @DeleteMapping("/{friendship_id}")
    public ResponseEntity<Object> deleteFriend(
            @PathVariable("friendship_id") Long friendshipId) {
        friendService.deleteFriend(friendshipId);
        return ResponseEntity.noContent().build();
    }
}
