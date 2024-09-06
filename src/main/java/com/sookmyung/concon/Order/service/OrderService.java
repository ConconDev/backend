package com.sookmyung.concon.Order.service;

import com.sookmyung.concon.Alarm.eventPublisher.EventPublisher;
import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Coupon.dto.CouponSimpleResponseDto;
import com.sookmyung.concon.Coupon.repository.CouponRepository;
import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.Item.repository.ItemRepository;
import com.sookmyung.concon.Order.dto.*;
import com.sookmyung.concon.Order.entity.OrderStatus;
import com.sookmyung.concon.Order.entity.Orders;
import com.sookmyung.concon.Order.exception.OrderInProgressException;
import com.sookmyung.concon.Order.repository.OrderRequestRedisRepository;
import com.sookmyung.concon.Order.repository.OrderRepository;
import com.sookmyung.concon.User.Entity.User;
import com.sookmyung.concon.User.Jwt.JwtUtil;
import com.sookmyung.concon.User.dto.UserIdResponseDto;
import com.sookmyung.concon.User.dto.UserSimpleResponseDto;
import com.sookmyung.concon.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final JwtUtil jwtUtil;

    private final OrderRequestRedisRepository orderRequestRedisRepository;
    private final EventPublisher eventPublisher;


    private static final String ORDER_REQUESTED = "orderRequested";
    private static final String ORDER_ACCEPTED = "orderAccepted";
    private static final String ORDER_CANCELED = "orderCanceled";
    private static final String ORDER_COMPLETED = "orderCompleted";

    // 메소드 추출
    // 쿠폰 아이디로 찾기
    private Coupon findCouponById(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 조회할 수 없습니다. "));
    }

    // 사용자 아이디로 사용자 찾기
    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 조회할 수 없습니다. "));
    }

    // 토큰으로 사용자 찾기
    private User findUserByToken(String token) {
        return userRepository.findByEmail(jwtUtil.getEmail(token).split(" ")[1])
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 조회할 수 없습니다."));
    }

    // order 아이디로 거래 찾기
    private Orders findOrdersById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 조회할 수 없습니다."));
    }

    private static OrderDetailResponseDto toOrderDetailDto(Orders order) {
        Coupon coupon = order.getCoupon();
        return OrderDetailResponseDto.toDto(order,
                CouponSimpleResponseDto.toDto(coupon, coupon.getUsedDate() != null)
                , UserSimpleResponseDto.toDto(order.getBuyer()), UserSimpleResponseDto.toDto(order.getSeller()));
    }

    private static List<OrderSimpleResponseDto> toOrderSimpleDtoList(List<Orders> orders) {
        return orders.stream().map(order ->
                OrderSimpleResponseDto.toDto(order,
                        CouponSimpleResponseDto.toDto(order.getCoupon(),
                                order.getCoupon().getUsedDate() != null),
                        UserIdResponseDto.toDto(order.getSeller()))).toList();
    }

    // 판매 생성
    // TODO : 사진 수정
    @Transactional
    public OrderDetailResponseDto createOrder(OrderCreateRequestDto request) {
        Coupon coupon = findCouponById(request.getCouponId());
        User seller = findUserById(request.getSellerId());
        String imageUrl = coupon.getItem().getImageUrl();
        Orders orders = request.toEntity(coupon, seller, imageUrl);
        orderRepository.save(orders);
        return OrderDetailResponseDto.toDto(orders,
                CouponSimpleResponseDto.toDto(coupon, false),
                null, UserSimpleResponseDto.toDto(seller));
    }

    // 상품 아이디로 단일 조회
    @Transactional(readOnly = true)
    public OrderDetailResponseDto getOrderByOrderId(Long orderId) {
        Orders order = findOrdersById(orderId);
        return toOrderDetailDto(order);
    }

    // 나의 판매 상품 전체 조회
    @Transactional(readOnly = true)
    public List<OrderSimpleResponseDto> getAllOrders(String token, int page, int size) {
        User seller = findUserByToken(token);
        Pageable pageable = PageRequest.of(page, size);
        List<Orders> orders = orderRepository.findBySeller(seller, pageable);
        return toOrderSimpleDtoList(orders);
    }

    // 나의 판매 상품 조회(진행중)
    @Transactional(readOnly = true)
    public List<OrderSimpleResponseDto> getAllOrdersAvailable(String token, int page, int size) {
        User seller = findUserByToken(token);
        Pageable pageable = PageRequest.of(page, size);
        List<Orders> orders = orderRepository.findBySellerAndStatus(seller, OrderStatus.AVAILABLE, pageable);
        return toOrderSimpleDtoList(orders);

    }

    // 나의 판매 상품 조회(완료)
    @Transactional(readOnly = true)
    public List<OrderSimpleResponseDto> getAllOrdersComplete(String token, int page, int size) {
        User seller = findUserByToken(token);
        Pageable pageable = PageRequest.of(page, size);
        List<Orders> orders = orderRepository.findBySellerAndStatus(seller, OrderStatus.COMPLETED, pageable);
        return toOrderSimpleDtoList(orders);

    }

//     아이템 종류로 조회 (판매중)
    @Transactional(readOnly = true)
    public List<OrderSimpleResponseDto> getAllOrdersByItemId(Long itemId, int page, int size) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 품목을 조회할 수 없습니다."));
        Pageable pageable = PageRequest.of(page, size);
        List<Orders> orders = orderRepository.findAllByItemAndStatus(item, OrderStatus.AVAILABLE, pageable);
        return toOrderSimpleDtoList(orders);
    }

    // 거래 요청
    @Transactional
    public void requestOrder(Long orderId, String token) {
        Orders orders = findOrdersById(orderId);
        User buyer = findUserByToken(token);
        if (orders.getStatus() == OrderStatus.IN_PROGRESS) {
            throw new OrderInProgressException("이 주문은 이미 거래 중입니다. ");
        }
        orderRequestRedisRepository.save(orders.getId(), buyer.getId());

        // 판매자에게 알람
        Long sellerId = orders.getSeller().getId();
        OrderEventAlarmDto response = OrderEventAlarmDto.toDto(orders);
        eventPublisher.publishEvent(sellerId, ORDER_REQUESTED, response);
    }

    // 거래 요청 전체 조회
    @Transactional(readOnly = true)
    public List<UserSimpleResponseDto> getAllRequestOrder(Long orderId) {
        return orderRequestRedisRepository.findById(orderId)
                .stream().map(Long::parseLong)
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
        OrderEventAlarmDto response = OrderEventAlarmDto.toDto(order);
        eventPublisher.publishEvent(buyer.getId(), ORDER_ACCEPTED, response);

        return toOrderDetailDto(order);
    }

    // 거래 중 취소
    @Transactional
    public OrderDetailResponseDto cancelTransaction(Long orderId) {
        Orders order = findOrdersById(orderId);
        order.setBuyer(null);
        order.updateStatus(OrderStatus.AVAILABLE);

        // 둘 다에게 알람
        List<Long> userIds = List.of(order.getSeller().getId(), order.getBuyer().getId());
        OrderEventAlarmDto response = OrderEventAlarmDto.toDto(order);
        eventPublisher.publishEventToMultipleUsers(userIds, ORDER_CANCELED, response);

        return toOrderDetailDto(order);
    }

    // 거래 완료
    @Transactional
    public OrderDetailResponseDto completeTransaction(Long orderId) {
        Orders order = findOrdersById(orderId);
        order.setBuyer(null);
        order.updateStatus(OrderStatus.COMPLETED);
        order.setTransactionDate(LocalDate.now());

        Coupon coupon = order.getCoupon();
        coupon.changeUser(order.getBuyer());
        coupon.setBuyFlag(true);

        orderRequestRedisRepository.delete(orderId);

        // 구매자에게 알람
        OrderEventAlarmDto response = OrderEventAlarmDto.toDto(order);
        eventPublisher.publishEvent(order.getBuyer().getId(), ORDER_COMPLETED, response);

        return toOrderDetailDto(order);
    }

    // 거래 수정
    @Transactional
    public OrderDetailResponseDto modifyOrder(OrderModifyRequestDto request) {
        Orders order = findOrdersById(request.getOrderId());
        order.update(request.getTitle(), request.getContent());
        return toOrderDetailDto(order);
    }

    // 거래 삭제
    @Transactional
    public void deleteOrder(Long orderId) {
        Orders order = findOrdersById(orderId);
        orderRepository.delete(order);
    }
}
