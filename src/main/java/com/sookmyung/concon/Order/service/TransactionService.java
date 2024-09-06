package com.sookmyung.concon.Order.service;

import com.sookmyung.concon.Alarm.eventPublisher.EventPublisher;
import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Coupon.dto.CouponSimpleResponseDto;
import com.sookmyung.concon.Order.dto.*;
import com.sookmyung.concon.Order.entity.OrderStatus;
import com.sookmyung.concon.Order.entity.Orders;
import com.sookmyung.concon.Order.exception.OrderInProgressException;
import com.sookmyung.concon.Order.repository.OrderRepository;
import com.sookmyung.concon.Order.repository.OrderRequestRedisRepository;
import com.sookmyung.concon.User.Entity.User;
import com.sookmyung.concon.User.Jwt.JwtUtil;
import com.sookmyung.concon.User.dto.UserSimpleResponseDto;
import com.sookmyung.concon.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {
    private static final String ORDER_REQUESTED = "orderRequested";
    private static final String ORDER_ACCEPTED = "orderAccepted";
    private static final String ORDER_CANCELED = "orderCanceled";
    private static final String ORDER_COMPLETED = "orderCompleted";

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final JwtUtil jwtUtil;

    private final OrderRequestRedisRepository orderRequestRedisRepository;
    private final EventPublisher eventPublisher;

    // 사용자 아이디로 사용자 찾기
    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 조회할 수 없습니다. "));
    }

    // order 아이디로 거래 찾기
    private Orders findOrdersById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 조회할 수 없습니다."));
    }

    // 토큰으로 사용자 찾기
    private User findUserByToken(String token) {
        return userRepository.findByEmail(jwtUtil.getEmail(token.split(" ")[1]))
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 조회할 수 없습니다."));
    }

    private static OrderDetailResponseDto toOrderDetailDto(Orders order) {
        Coupon coupon = order.getCoupon();
        return OrderDetailResponseDto.toDto(order,
                CouponSimpleResponseDto.toDto(coupon, coupon.getUsedDate() != null)
                , order.getBuyer() != null ? UserSimpleResponseDto.toDto(order.getBuyer()) : null, UserSimpleResponseDto.toDto(order.getSeller()));
    }

    // 거래 요청
    @Transactional
    public OrderRequestResponseDto requestOrder(Long orderId, String token) {
        Orders orders = findOrdersById(orderId);
        User buyer = findUserByToken(token);
        if (orders.getStatus() == OrderStatus.IN_PROGRESS) {
            throw new OrderInProgressException("이 주문은 이미 거래 중입니다. ");
        }
        orderRequestRedisRepository.save(orders.getId(), buyer.getId());

        // 판매자에게 알람
        Long sellerId = orders.getSeller().getId();
        OrderEventAlarmDto response = OrderEventAlarmDto.toDto(orders, buyer);
        eventPublisher.publishEvent(sellerId, ORDER_REQUESTED, response);
        return OrderRequestResponseDto.toDto(orders, buyer);
    }

    // 거래 요청 전체 조회
    @Transactional(readOnly = true)
    public List<UserSimpleResponseDto> getAllRequestOrder(Long orderId) {
        return orderRequestRedisRepository.findAllMembersById(orderId)
                .stream()
                .map(this::findUserById)
                .map(UserSimpleResponseDto::toDto)
                .toList();
    }

    // 거래 수락(거래 중)
    @Transactional
    public OrderDetailResponseDto acceptTransaction(TransactionAcceptRequestDto request) {
        Orders order = findOrdersById(request.getOrderId());
        User buyer = findUserById(request.getBuyerId());

        order.setBuyer(buyer);
        order.updateStatus(OrderStatus.IN_PROGRESS);

        orderRequestRedisRepository.deleteUser(request.getOrderId(), request.getBuyerId());

        // 구매자에게 알람
        OrderEventAlarmDto response = OrderEventAlarmDto.toDto(order, buyer);
        eventPublisher.publishEvent(buyer.getId(), ORDER_ACCEPTED, response);

        return toOrderDetailDto(order);
    }

    // 거래 중 취소
    @Transactional
    public OrderDetailResponseDto cancelTransaction(Long orderId) {
        Orders order = findOrdersById(orderId);
        User buyer = order.getBuyer();
        order.setBuyer(null);
        order.updateStatus(OrderStatus.AVAILABLE);

        // 둘 다에게 알람
        List<Long> userIds = List.of(order.getSeller().getId(), buyer.getId());
        OrderEventAlarmDto response = OrderEventAlarmDto.toDto(order, buyer);
        eventPublisher.publishEventToMultipleUsers(userIds, ORDER_CANCELED, response);

        return toOrderDetailDto(order);
    }

    // 거래 완료
    @Transactional
    public OrderDetailResponseDto completeTransaction(Long orderId) {
        Orders order = findOrdersById(orderId);
        order.updateStatus(OrderStatus.COMPLETED);
        order.setTransactionDate(LocalDate.now());

        Coupon coupon = order.getCoupon();
        coupon.changeUser(order.getBuyer());
        coupon.setBuyFlag(true);

        orderRequestRedisRepository.delete(orderId);

        // 구매자에게 알람
        OrderEventAlarmDto response = OrderEventAlarmDto.toDto(order, order.getBuyer());
        eventPublisher.publishEvent(order.getBuyer().getId(), ORDER_COMPLETED, response);

        return toOrderDetailDto(order);
    }
}
