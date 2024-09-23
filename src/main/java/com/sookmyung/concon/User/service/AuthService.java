package com.sookmyung.concon.User.service;

import com.sookmyung.concon.Kakao.dto.KakaoTokenResponse;
import com.sookmyung.concon.Kakao.service.KakaoService;
import com.sookmyung.concon.User.Entity.LoginType;
import com.sookmyung.concon.User.Entity.User;
import com.sookmyung.concon.User.dto.LoginRequestDto;
import com.sookmyung.concon.User.dto.LoginResponseDto;
import com.sookmyung.concon.User.dto.UserCreateRequestDto;
import com.sookmyung.concon.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Slf4j
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

//    @Value("${KAKAO_CLIENT_ID}")
//    private String CLIENT_ID;
//
//    @Value("${KAKAO_REDIRECT_URI}")
//    private String REDIRECT_URI;
//
//    @Value("${KAKAO_LOGOUT_REDIRECT_URI}")
//    private String REDIRECT_LOGOUT_URI;

    @Value("${allowed.origins}")
    private String[] origins;

    // 회원 가입
    public Long join(UserCreateRequestDto request) {
        if (userRepository.existsUserByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이메일이 중복되었습니다. ");
        }
        User user = request.toEntity(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setLoginType(LoginType.REGULAR);

        userRepository.save(user);
        return user.getId();
    }

    public LoginResponseDto login(KakaoTokenResponse tokenResponse, String email, String password) {
        LoginRequestDto request = LoginRequestDto.builder()
                .email(email)
                .password(password)
                .build();
        String uri = origins[0] + "/api/auth/login";
        System.out.println(uri);
        Optional<LoginResponseDto> loginResponse = webClient.post()
                .uri(origins[0] + "/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        log.info("카카오 로그인 리다이랙트 성공");
                        String token = response.headers().header("Authorization").get(0);
                        log.info(token);
                        return Mono.just(new LoginResponseDto(tokenResponse, token));
                    } else throw new RuntimeException("로그인 리다이랙트 실패 " +  response.statusCode());
                })
                .blockOptional();

        if (loginResponse.isPresent()) {
            log.info("로그인 성공!!!!!!");

            LoginResponseDto response = loginResponse.get();
            User user = userRepository.findByEmail(email).orElseThrow(RuntimeException::new);
            String refreshToken = response.getKakaoTokenResponse().getRefresh_token();
            if (!user.getRefreshToken().equals(refreshToken)) {
                user.setRefreshToken(refreshToken);
            }

            return loginResponse.get();
        } else {
            throw new IllegalArgumentException("로그인 실패");
        }
    }

    // 회원 탈퇴
    @Transactional
    public void deleteUser(String token) {
        User user = orderUserFacade.findUserByToken(token);

        if (user.is_verified()) {

            KakaoTokenResponse accessToken = kakaoService.getToken(kakaoService.getCode());

            webClient.post()
                    .uri(UNLINK_URI)
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve();

        }
        userRepository.delete(user);
    }

    public void deleteUserCallback() {
        String code = kakaoService.getCode();
        KakaoTokenResponse tokenDto = kakaoService.getToken(code);
        String accessToken = tokenDto.getAccess_token();

    }
}
