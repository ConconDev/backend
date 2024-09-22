package com.sookmyung.concon.KakaoLogin.service;

import com.sookmyung.concon.KakaoLogin.dto.KakaoTokenResponse;
import com.sookmyung.concon.KakaoLogin.dto.KakaoUserInfoResponse;
import com.sookmyung.concon.User.Entity.User;
import com.sookmyung.concon.User.dto.LoginRequestDto;
import com.sookmyung.concon.User.dto.UserCreateRequestDto;
import com.sookmyung.concon.User.repository.UserRepository;
import com.sookmyung.concon.User.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class KakaoService {
    private final WebClient webClient;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Value("${KAKAO_GRANT_TYPE}")
    private String GRANT_TYPE;

    @Value("${KAKAO_TOKEN_URI}")
    private String KAKAO_TOKEN_URI;

    @Value("${KAKAO_CLIENT_ID}")
    private String CLIENT_ID;

    @Value("${KAKAO_REDIRECT_URI}")
    private String REDIRECT_URI;

    @Value("${KAKAO_USER_INFO_URI}")
    private String USER_INFO_URI;

    public KakaoTokenResponse getToken(String code) {
        String uri = KAKAO_TOKEN_URI + "?grant_type=" + GRANT_TYPE + "&client_id=" + CLIENT_ID
                + "&redirect_uri=" + REDIRECT_URI + "&code=" + code;
        System.out.println(uri);

        Flux<KakaoTokenResponse> responsse = webClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(KakaoTokenResponse.class);

        return responsse.blockFirst();
    }

    public KakaoUserInfoResponse getUserInfo(String token) {
        String uri = USER_INFO_URI;
        Flux<KakaoUserInfoResponse> response = webClient.get()
                .uri(uri)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToFlux(KakaoUserInfoResponse.class);

        return response.blockFirst();
    }

    @Transactional
    public LoginRequestDto kakaoLogin(String code) {
        KakaoTokenResponse kakaoTokenResponse = getToken(code);
        KakaoUserInfoResponse userInfo = getUserInfo(kakaoTokenResponse.getAccess_token());

        String email = userInfo.getKakao_account().getEmail();
        String password = userInfo.getId().toString();
        UserCreateRequestDto dto = UserCreateRequestDto.builder()
                .email(email)
                .password(password).build();

        join(dto);
        return LoginRequestDto.builder()
                .email(email)
                .password(password)
                .build();
    }
    public Long join(UserCreateRequestDto request) {
        if (userRepository.existsUserByEmail(request.getEmail())) {
            return null;
        }
        User user = request.toEntity(bCryptPasswordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        return user.getId();
    }
}
