package com.sookmyung.concon.User.service;

import com.sookmyung.concon.User.Entity.Friendship;
import com.sookmyung.concon.User.Entity.FriendshipStatus;
import com.sookmyung.concon.User.Entity.User;
import com.sookmyung.concon.User.Jwt.JwtUtil;
import com.sookmyung.concon.User.dto.UserSimpleResponseDto;
import com.sookmyung.concon.User.repository.FriendshipRepository;
import com.sookmyung.concon.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;
    private final JwtUtil jwtUtil;

    public User findUserByToken(String token) {
        return userRepository.findByEmail(jwtUtil.getEmail(token.split(" ")[1]))
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다. "));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 조회할 수 없습니다. "));
    }

    private Friendship findFriendshipById(Long friendshipId) {
        return friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new IllegalArgumentException("해당 요청을 조회할 수 없습니다. "));
    }

    // 친구 요청
    public void friendRequest(String token, Long friendId) {
        User user = findUserByToken(token);
        User friend = findUserById(friendId);
        Friendship friendship = Friendship.builder()
                .user(user)
                .friend(friend)
                .status(FriendshipStatus.WAITING)
                .build();
        friendshipRepository.save(friendship);
    }

    // 친구 요청 조회(보낸 쪽)
    // TODO : 사진 수정
    public List<UserSimpleResponseDto> sentFriendRequest(String token) {
        User user = findUserByToken(token);
        List<Friendship> friendships = friendshipRepository.findByUserAndStatus(user, FriendshipStatus.WAITING);
        return friendships.stream().map(friendship -> {
            User friend = friendship.getFriend();
            return UserSimpleResponseDto.toDto(friend, null);
        }).toList();
    }

    // 친구 요청 조회(받는 쪽)
    // TODO : 사진 수정
    public List<UserSimpleResponseDto> receivedFriendRequest(String token) {
        User user = findUserByToken(token);
        List<Friendship> friendships = friendshipRepository.findByFriendAndStatus(user, FriendshipStatus.WAITING);
        return friendships.stream().map(friendship -> {
            User friend = friendship.getFriend();
            return UserSimpleResponseDto.toDto(friend, null);
        }).toList();
    }

    // 친구 요청 취소
    public void cancelFriendRequest(Long friendshipId) {
        Friendship friendship = findFriendshipById(friendshipId);
        friendshipRepository.delete(friendship);
    }

    // 친구 요청 거절
    public Friendship denyFriendRequest(Long friendshipId) {
        Friendship friendship = findFriendshipById(friendshipId);
        friendship.updateStatus(FriendshipStatus.DENIED);
        return friendship;
    }

    // 친구 수락
    public List<Friendship> acceptFriendRequest(Long friendshipId) {
        Friendship friendship = findFriendshipById(friendshipId);
        friendship.updateStatus(FriendshipStatus.ACCEPT);
        Friendship friendshipAccepted = Friendship.builder()
                .user(friendship.getFriend())
                .friend(friendship.getUser())
                .status(FriendshipStatus.ACCEPT)
                .build();

        friendshipRepository.save(friendship);
        friendshipRepository.save(friendshipAccepted);

        return Arrays.asList(friendship, friendshipAccepted);
    }

    // 친구 조회
    public List<UserSimpleResponseDto> getFriends(String token) {
        User user = findUserByToken(token);
        List<Friendship> friendships = friendshipRepository.findByUserAndStatus(user, FriendshipStatus.ACCEPT);
        return friendships.stream().map(friendship -> {
            User friend = friendship.getFriend();
            return UserSimpleResponseDto.toDto(friend, null);
        }).toList();
    }

    // 친구 삭제
    public void deleteFriendship(Long friendshipId) {
        Friendship friendship = findFriendshipById(friendshipId);
        Friendship friendshipReverse = friendshipRepository.findByUserAndFriend(friendship.getFriend(), friendship.getUser())
                .orElseThrow(() -> new IllegalArgumentException("해당 친구를 조회할 수 없습니다. "));
        friendshipRepository.deleteAll(Arrays.asList(friendship, friendshipReverse));
    }
}
