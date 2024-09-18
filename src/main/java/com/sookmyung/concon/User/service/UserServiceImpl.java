package com.sookmyung.concon.User.service;

import com.sookmyung.concon.User.Entity.User;
import com.sookmyung.concon.User.Jwt.JwtUtil;
import com.sookmyung.concon.User.dto.UserDetailConfigResponseDto;
import com.sookmyung.concon.User.dto.UserDetailResponseDto;
import com.sookmyung.concon.User.dto.UserModifyRequestDto;
import com.sookmyung.concon.User.dto.UserSimpleResponseDto;
import com.sookmyung.concon.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final OrderUserFacade orderUserFacade;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // 나의 정보 조회
    // TODO : 프로필 사진 수정
    @Transactional(readOnly = true)
    public UserDetailConfigResponseDto getUserInfo(String token) {
        User user = orderUserFacade.findUserByToken(token);
        return UserDetailConfigResponseDto.toDto(user);
    }

    // id로 회원 정보 조회
    // TODO : Review 추가하기
    @Transactional(readOnly = true)
    public UserDetailResponseDto getUserInfoById(Long userId) {
        User user = orderUserFacade.findUserById(userId);
        return UserDetailResponseDto.toDto(user, orderUserFacade.getOrdersByUserId(user.getId(), 5, 5), null);
    }

    // 전체 회원 조회
    @Transactional(readOnly = true)
    public List<UserSimpleResponseDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserSimpleResponseDto::toDto)
                .toList();
    }


    // 회원 정보 수정
    @Override
    @Transactional
    public UserDetailConfigResponseDto modifyUser(String token, UserModifyRequestDto request) {
        User user = orderUserFacade.findUserByToken(token);
        request.update(user, null);
        return UserDetailConfigResponseDto.toDto(user);
    }

    // 거래용 랜덤 5명의 판매 정보 가져오기
    // TODO : review 추가하기
    @Override
    @Transactional(readOnly = true)
    public List<UserDetailResponseDto> get5RandomUser() {
        List<User> randomUsers = userRepository.findRandomUsers();
        List<UserDetailResponseDto> response = new ArrayList<>();
        for (User user : randomUsers) {
            UserDetailResponseDto dto = UserDetailResponseDto.toDto(user,
                    orderUserFacade.get2TopOrdersByUser(user), null);
            response.add(dto);
        }
        return response;
    }

    // 회원 탈퇴
    @Override
    @Transactional
    public void deleteUser(String token) {
        User user = orderUserFacade.findUserByToken(token);
        userRepository.delete(user);
    }
}
