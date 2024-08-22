package com.sookmyung.concon.User.service;

import com.sookmyung.concon.User.Entity.User;
import com.sookmyung.concon.User.Jwt.JwtUtil;
import com.sookmyung.concon.User.dto.UserCreateRequestDto;
import com.sookmyung.concon.User.dto.UserDetailResponseDto;
import com.sookmyung.concon.User.dto.UserModifyRequestDto;
import com.sookmyung.concon.User.dto.UserSimpleResponseDto;
import com.sookmyung.concon.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findUserByToken(String token) {
        return userRepository.findByEmail(jwtUtil.getEmail(token))
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다. "));
    }

    // 회원 가입
    public Long join(UserCreateRequestDto request) {
        User user = request.toEntity(bCryptPasswordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return user.getId();
    }

    // 나의 정보 조회
    // TODO : 프로필 사진 수정
    public UserDetailResponseDto getUserInfo(String token) {
        User user = findUserByToken(token);
        return UserDetailResponseDto.toDto(user, null);
    }

    // id로 회원 정보 조회
    public UserDetailResponseDto getUserInfoById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다. "));
        return UserDetailResponseDto.toDto(user, null);
    }

    // 전체 회원 조회
    public List<UserSimpleResponseDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> UserSimpleResponseDto.toDto(user, null))
                .toList();
    }


    // 회원 정보 수정
    public Long modifyUser(String token, UserModifyRequestDto request) {
        User user = findUserByToken(token);
        user.update(request.getUsername(), request.getGender(), request.getAge());
        return user.getId();
    }

    // 회원 탈퇴
    public void deleteUser(String token) {
        User user = findUserByToken(token);
        userRepository.delete(user);
    }
}
