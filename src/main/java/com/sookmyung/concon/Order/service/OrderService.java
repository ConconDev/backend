package com.sookmyung.concon.Order.service;

import com.sookmyung.concon.Order.dto.OrderCreateRequestDto;
import com.sookmyung.concon.Order.dto.OrderDetailResponseDto;
import com.sookmyung.concon.Order.dto.OrderModifyRequestDto;
import com.sookmyung.concon.Order.dto.OrderSimpleResponseDto;
import com.sookmyung.concon.Order.entity.Orders;
import com.sookmyung.concon.User.Entity.User;

import java.util.List;

public interface OrderService {
    OrderDetailResponseDto createOrder(OrderCreateRequestDto request);

    OrderDetailResponseDto getOrderByOrderId(Long orderId);

    List<OrderSimpleResponseDto> getAllOrders(String token, int page, int size);

    List<OrderSimpleResponseDto> getOrdersByUserId(Long userId, int page, int size);

    List<OrderSimpleResponseDto> getAllOrdersAvailable(String token, int page, int size);

    List<OrderSimpleResponseDto> getAllOrdersComplete(String token, int page, int size);

    List<OrderSimpleResponseDto> getAllOrdersByItemId(Long itemId, int page, int size);

    List<OrderSimpleResponseDto> getAllOrdersByItemKeyword(String itemKeyword, int page, int size);

    void deleteOrder(Long orderId);
}
