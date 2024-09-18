package com.sookmyung.concon.User.service;

import com.sookmyung.concon.User.Entity.User;
import com.sookmyung.concon.User.dto.UserCreateRequestDto;
import com.sookmyung.concon.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    // 회원 가입
    // TODO : 이미지 추가
    public Long join(UserCreateRequestDto request) {
        if (userRepository.existsUserByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이메일이 중복되었습니다. ");
        }
        User user = request.toEntity(bCryptPasswordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        return user.getId();
    }
}
