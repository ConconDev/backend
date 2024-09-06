package com.sookmyung.concon.Alarm.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class EmitterMemoryRepository {
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void saveByUserId(Long userId, SseEmitter emitter) {
        emitters.put(userId, emitter);
    }

    public void deleteByUserId(Long userId) {
        emitters.remove(userId);
    }

    public SseEmitter getByUserId(Long userId) {
        return emitters.get(userId);
    }
}
