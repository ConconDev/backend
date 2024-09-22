package com.sookmyung.concon.User.service;

import com.sookmyung.concon.Item.service.ItemService;
import com.sookmyung.concon.Photo.service.PhotoFacade;
import com.sookmyung.concon.Photo.service.PhotoManager;
import com.sookmyung.concon.User.Entity.User;
import com.sookmyung.concon.User.dto.*;
import com.sookmyung.concon.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

// tODO : paging 처리
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String PREFIX = "user/";
    private final OrderUserFacade orderUserFacade;
    private final UserRepository userRepository;
    private final ItemService itemService;
    private final PhotoManager photoManager;
    private final PhotoFacade photoFacade;

    // 나의 정보 조회
    @Override
    @Transactional(readOnly = true)
    public UserDetailConfigResponseDto getUserInfo(String token) {
        User user = orderUserFacade.findUserByToken(token);
        return UserDetailConfigResponseDto.toDto(user, getUserPhoto(user));
    }

    private String getUserPhoto(User user) {
        return photoFacade.getUserPhotoUrl(user);
    }

    private String makePrefix(User user) {
        return PREFIX + user.getId();
    }

    // id로 회원 정보 조회
    // TODO : Review 추가하기
    @Override
    @Transactional(readOnly = true)
    public UserDetailResponseDto getUserInfoById(Long userId) {
        return orderUserFacade.toUserDetailResponseDto(userId);
    }

    // 전체 회원 조회
    @Override
    @Transactional(readOnly = true)
    public List<UserSimpleResponseDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map((user) -> UserSimpleResponseDto.toDto(user, getUserPhoto(user)))
                .toList();
    }

    // 이름으로 검색
    @Override
    @Transactional(readOnly = true)
    public List<UserSimpleResponseDto> getUsersByKeyword(String keyword) {
        return userRepository.findByUsernameContaining(keyword)
                .stream()
                .map((user) -> UserSimpleResponseDto.toDto(user, getUserPhoto(user)))
                .toList();
    }


    // 회원 정보 수정
    @Override
    @Transactional
    public UserModifyResponseDto modifyUser(String token, UserModifyRequestDto request) {
        User user = orderUserFacade.findUserByToken(token);
        user.update(request);
        String photoModifyUrl = "";
        if (request.getFileName() != null && !request.getFileName().isEmpty()) {
            String fileName = request.getFileName();
            // TODO : modify
            LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            photoModifyUrl = photoManager.updatePhoto(makePrefix(user),
                            user.getProfilePhotoName(), user.getProfileCreatedDate(),
                            fileName, now);
            user.updatePhoto(fileName, now);
        }
        // TODO : 사진 수정
        return UserModifyResponseDto.toDto(UserDetailConfigResponseDto.toDto(user, "로직 추가 예정"), photoModifyUrl);
    }

    // 거래용 랜덤 5명의 판매 정보 가져오기
    // TODO : review 추가하기
    @Override
    @Transactional
    public List<UserDetailResponseDto> get5RandomUser() {

        Pageable pageable = PageRequest.of(0, 5);
        List<User> randomUsers = userRepository.findRandomUsers(pageable);
        return orderUserFacade.toUserDetailResponseDtos(randomUsers);
    }

    // 아이템 이름으로 랜덤 5명의 판매 정보 가져오기
    @Override
    @Transactional(readOnly = true)
    public List<UserDetailResponseDto> get5RandomUserByItemName(String itemName) {
        Long itemId = itemService.getItemByName(itemName).getItemId();
        Pageable pageable = PageRequest.of(0, 5);
        List<User> randomUsers = userRepository.findRandomUsersByItem(itemId, pageable);
        return orderUserFacade.toUserDetailResponseDtos(randomUsers);
    }

    // 회원 탈퇴
    @Override
    @Transactional
    public void deleteUser(String token) {
        User user = orderUserFacade.findUserByToken(token);
        userRepository.delete(user);
    }
}
