package com.sookmyung.concon.Alarm.service;

import com.sookmyung.concon.Alarm.repository.EmitterMemoryRepository;
import com.sookmyung.concon.User.Entity.User;
import com.sookmyung.concon.User.Jwt.JwtUtil;
import com.sookmyung.concon.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final EmitterMemoryRepository emitterMemoryRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // 토큰으로 사용자 찾기
    private User findUserByToken(String token) {
        return userRepository.findByEmail(jwtUtil.getEmail(token).split(" ")[1])
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 조회할 수 없습니다."));
    }

    public SseEmitter subscribe(String token) {
        Long userId = findUserByToken(token).getId();
        SseEmitter emitter = new SseEmitter(1000L * 60 * 60);
        emitterMemoryRepository.saveByUserId(userId, emitter);

        setEmitterCallbacks(emitter, userId);
        return emitter;
    }

    private void setEmitterCallbacks(SseEmitter emitter, Long userId) {
        emitter.onCompletion(() -> onCompletion(userId));
        emitter.onTimeout(() -> onTimeout(userId));
        emitter.onError((e) -> onError(userId, e));
    }

    private void onCompletion(Long userId) {
        emitterMemoryRepository.deleteByUserId(userId);
        // 추가적인 완료 처리 로직
        System.out.println("Emitter completed for user: " + userId);
    }

    private void onTimeout(Long userId) {
        emitterMemoryRepository.deleteByUserId(userId);
        // 추가적인 타임아웃 처리 로직
        System.out.println("Emitter timed out for user: " + userId);
    }

    private void onError(Long userId, Throwable error) {
        emitterMemoryRepository.deleteByUserId(userId);
        // 에러 처리 로직
        System.err.println("Error occurred for user: " + userId);
        error.printStackTrace();
    }
}
