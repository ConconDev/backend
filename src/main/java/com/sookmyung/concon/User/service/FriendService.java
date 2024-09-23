package com.sookmyung.concon.User.service;

import com.sookmyung.concon.Photo.service.PhotoManager;
import com.sookmyung.concon.User.Entity.Friendship;
import com.sookmyung.concon.User.Entity.FriendshipStatus;
import com.sookmyung.concon.User.Entity.User;
import com.sookmyung.concon.User.Jwt.JwtUtil;
import com.sookmyung.concon.User.dto.FriendSimpleResponseDto;
import com.sookmyung.concon.User.dto.UserSimpleResponseDto;
import com.sookmyung.concon.User.repository.FriendshipRepository;
import com.sookmyung.concon.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final OrderUserFacade orderUserFacade;
    private final FriendshipRepository friendshipRepository;

    private static final String PREFIX = "user:";
    private final PhotoManager photoManager;

    private Friendship findFriendshipById(Long friendshipId) {
        return friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new IllegalArgumentException("해당 요청을 조회할 수 없습니다. "));
    }

    private String getUserPhoto(User user) {
        return photoManager.getPhoto(makePrefix(user), user.getProfilePhotoName(), user.getProfileCreatedDate());
    }

    private String makePrefix(User user) {
        return PREFIX + user.getId();
    }

    // 친구 요청
    @Transactional
    public Long createFriendRequest(String token, Long friendId) {
        User sender = orderUserFacade.findUserByToken(token);
        User receiver = orderUserFacade.findUserById(friendId);

        // 중복 요청 제거
        if (friendshipRepository.existsBySenderAndReceiver(sender, receiver)) {
            throw new IllegalArgumentException("이미 친구 요청을 보냈습니다. ");
        }

        Friendship friendship = Friendship.builder()
                .sender(sender)
                .receiver(receiver)
                .status(FriendshipStatus.WAITING)
                .build();
        friendshipRepository.save(friendship);
        return friendship.getId();
    }

    // 친구 요청 조회(보낸 쪽)
    @Transactional(readOnly = true)
    public List<FriendSimpleResponseDto> getSenderFriends(String token) {
        User user = orderUserFacade.findUserByToken(token);
        List<Friendship> friendships = friendshipRepository.findBySenderAndStatus(user, FriendshipStatus.WAITING);
        return friendships.stream().map(friendship -> {
            User receiver = friendship.getReceiver();
            return FriendSimpleResponseDto.toDto(receiver, getUserPhoto(receiver), friendship);
        }).toList();
    }

    // 친구 요청 조회(받는 쪽)
    @Transactional(readOnly = true)
    public List<FriendSimpleResponseDto> getReceiverFriends(String token) {
        User user = orderUserFacade.findUserByToken(token);
        List<Friendship> friendships = friendshipRepository.findByReceiverAndStatus(user, FriendshipStatus.WAITING);
        return friendships.stream().map(friendship -> {
            User sender = friendship.getSender();
            return FriendSimpleResponseDto.toDto(sender, getUserPhoto(user), friendship);
        }).toList();
    }

    // 친구 요청 취소
    @Transactional
    public void cancelFriendRequest(Long friendshipId) {
        Friendship friendship = findFriendshipById(friendshipId);
        friendshipRepository.delete(friendship);
    }

    // 친구 요청 거절
    @Transactional
    public FriendSimpleResponseDto denyFriendRequest(Long friendshipId) {
        Friendship friendship = findFriendshipById(friendshipId);
        if (friendship.getStatus() != FriendshipStatus.WAITING) {
            throw new IllegalArgumentException("요청 대기 상태가 아닙니다. ");
        }
        friendship.updateStatus(FriendshipStatus.DENIED);
        User sender = friendship.getSender();
        return FriendSimpleResponseDto.toDto(sender, getUserPhoto(sender), friendship);
    }



    // 친구 수락
    @Transactional
    public List<Friendship> acceptFriendRequest(Long friendshipId) {
        Friendship friendship = findFriendshipById(friendshipId);
        if (friendship.getStatus() != FriendshipStatus.WAITING) {
            throw new IllegalArgumentException("요청 대기 상태가 아닙니다. ");
        }
        friendship.updateStatus(FriendshipStatus.ACCEPT);

        Friendship friendshipAccepted = Friendship.builder()
                .sender(friendship.getReceiver())
                .receiver(friendship.getSender())
                .status(FriendshipStatus.ACCEPT)
                .build();

        friendshipRepository.save(friendshipAccepted);

        List<Friendship> friendships = new ArrayList<>();
        friendships.add(friendship);
        friendships.add(friendshipAccepted);

        return friendships;
    }

    // 친구 조회
    @Transactional(readOnly = true)
    public List<FriendSimpleResponseDto> getFriends(String token) {
        User user = orderUserFacade.findUserByToken(token);
        List<Friendship> friendships = friendshipRepository.findBySenderAndStatus(user, FriendshipStatus.ACCEPT);
        return friendships.stream().map(friendship -> {
            User friend = friendship.getReceiver();
            return FriendSimpleResponseDto.toDto(friend, getUserPhoto(friend), friendship);
        }).toList();
    }

    // 친구 삭제
    @Transactional
    public void deleteFriend(Long friendshipId) {
        Friendship friendship = findFriendshipById(friendshipId);
        Friendship friendshipReverse = friendshipRepository.findBySenderAndReceiver(friendship.getReceiver(), friendship.getSender())
                .orElseThrow(() -> new IllegalArgumentException("해당 친구를 조회할 수 없습니다. "));
        friendshipRepository.deleteAll(Arrays.asList(friendship, friendshipReverse));
    }
}
