package com.sookmyung.concon.Order.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRequestRedisRepository {
    private final RedisTemplate<String, String> orderRedisTemplate;
    private static final String ORDER_KEY = "orderId:";

    public void save(Long orderId, Long userId) {
        ListOperations<String, String> listOperations = orderRedisTemplate.opsForList();
        listOperations.leftPush(ORDER_KEY + orderId, userId.toString());
    }



    public List<String> findById(Long orderId) {
        ListOperations<String, String> listOperations = orderRedisTemplate.opsForList();
        return listOperations.range(ORDER_KEY + orderId, 0, -1);
    }

    public void deleteUser(Long orderId, Long userId) {
        orderRedisTemplate.opsForList().remove(ORDER_KEY + orderId, 0, userId + "");
    }

    public void delete(Long orderId) {
        orderRedisTemplate.delete(ORDER_KEY + orderId);
    }
}
