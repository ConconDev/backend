package com.sookmyung.concon.User.service;

import com.sookmyung.concon.Kakao.dto.KakaoTokenResponse;
import com.sookmyung.concon.Kakao.service.KakaoService;
import com.sookmyung.concon.User.Entity.User;
import com.sookmyung.concon.User.dto.UserCreateRequestDto;
import com.sookmyung.concon.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final OrderUserFacade orderUserFacade;
    private final KakaoService kakaoService;
    private final WebClient webClient;

    @Value("${KAKAO_UNLINK_URI}")
    private String UNLINK_URI;

    // 회원 가입
    public Long join(UserCreateRequestDto request) {
        if (userRepository.existsUserByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이메일이 중복되었습니다. ");
        }
        User user = request.toEntity(bCryptPasswordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        return user.getId();
    }

    // 회원 탈퇴
    @Transactional
    public void deleteUser(String token) {
        User user = orderUserFacade.findUserByToken(token);

        if (user.is_verified()) {
            String code = kakaoService.getCode();
            KakaoTokenResponse tokenDto = kakaoService.getToken(code);
            String accessToken = tokenDto.getAccess_token();
            webClient.post()
                    .uri(UNLINK_URI)
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve();
        }
        userRepository.delete(user);
    }
}
