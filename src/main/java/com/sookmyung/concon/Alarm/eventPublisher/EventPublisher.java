package com.sookmyung.concon.Alarm.eventPublisher;

import com.sookmyung.concon.Alarm.repository.EmitterMemoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventPublisher {
    private final EmitterMemoryRepository emitterMemoryRepository;

    public void publishEvent(Long userId, String eventName, Object data) {
        SseEmitter emitter = emitterMemoryRepository.getByUserId(userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name(eventName)
                        .data(data));
            } catch (IOException e) {
                emitterMemoryRepository.deleteByUserId(userId);
                log.error(e.getMessage());
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    // 다중 사용자에게 알람
    // 특정 조건의 사용자에게 알람
    // 모든 사용자에게 알람
}
