package com.sookmyung.concon.Order.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class OrderRequestRedisRepository {
    private final RedisTemplate<String, String> orderRedisTemplate;
    private static final String ORDER_KEY = "orderId:";

    public void save(Long orderId, Long userId) {
        ZSetOperations<String, String> zSetOperations = orderRedisTemplate.opsForZSet();
        zSetOperations.add(ORDER_KEY + orderId, userId.toString(), System.currentTimeMillis());
    }

    /*
    Java에서 Set으로 받지만, 이는 순서가 보장되는 LinkedHashSet과 유사한 방식으로 동작
    Redis에서 정렬된 순서대로 요소를 반환하고, 이 순서가 Java의 Set 구현체에서도 유지
     */
    public List<Long> findAllMembersById(Long orderId) {
        ZSetOperations<String, String> zSetOperations = orderRedisTemplate.opsForZSet();
        Set<String> member = zSetOperations.range(ORDER_KEY + orderId, 0, -1);
        return member != null ? member.stream().map(Long::parseLong).toList() : List.of();
    }

    public void deleteUser(Long orderId, Long userId) {
        orderRedisTemplate.opsForZSet().remove(ORDER_KEY + orderId, userId + "");
    }

    public void delete(Long orderId) {
        orderRedisTemplate.delete(ORDER_KEY + orderId);
    }
}
