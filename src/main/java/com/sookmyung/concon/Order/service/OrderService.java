package com.sookmyung.concon.Order.service;

import com.sookmyung.concon.Alarm.eventPublisher.EventPublisher;
import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Coupon.dto.CouponSimpleResponseDto;
import com.sookmyung.concon.Coupon.repository.CouponRepository;
import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.Item.repository.ItemRepository;
import com.sookmyung.concon.Order.dto.OrderCreateRequestDto;
import com.sookmyung.concon.Order.dto.OrderDetailResponseDto;
import com.sookmyung.concon.Order.dto.OrderSimpleResponseDto;
import com.sookmyung.concon.Order.dto.TransactionAcceptRequestDto;
import com.sookmyung.concon.Order.entity.OrderStatus;
import com.sookmyung.concon.Order.entity.Orders;
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
                null, UserSimpleResponseDto.toDto(seller, null));
    }

    // 나의 판매 상품 전체 조회
    @Transactional(readOnly = true)
    public List<OrderSimpleResponseDto> getAllOrders(String token, int page, int size) {
        User seller = findUserByToken(token);
        Pageable pageable = PageRequest.of(page, size);
        List<Orders> orders = orderRepository.findBySeller(seller, pageable);
        return orders.stream().map(order ->
            OrderSimpleResponseDto.toDto(order,
                    CouponSimpleResponseDto.toDto(order.getCoupon(),
                            order.getCoupon().getUsedDate() != null),
                    UserIdResponseDto.toDto(order.getSeller()))).toList();
    }

    // 나의 판매 상품 조회(진행중)
    @Transactional(readOnly = true)
    public List<OrderSimpleResponseDto> getAllOrdersAvailable(String token, int page, int size) {
        User seller = findUserByToken(token);
        Pageable pageable = PageRequest.of(page, size);
        List<Orders> orders = orderRepository.findBySellerAndStatus(seller, OrderStatus.AVAILABLE, pageable);
        return orders.stream().map(order ->
                OrderSimpleResponseDto.toDto(order,
                        CouponSimpleResponseDto.toDto(order.getCoupon(),
                                order.getCoupon().getUsedDate() != null),
                        UserIdResponseDto.toDto(order.getSeller()))).toList();

    }

    // 나의 판매 상품 조회(완료)
    @Transactional(readOnly = true)
    public List<OrderSimpleResponseDto> getAllOrdersComplete(String token, int page, int size) {
        User seller = findUserByToken(token);
        Pageable pageable = PageRequest.of(page, size);
        List<Orders> orders = orderRepository.findBySellerAndStatus(seller, OrderStatus.COMPLETED, pageable);
        return orders.stream().map(order ->
                OrderSimpleResponseDto.toDto(order,
                        CouponSimpleResponseDto.toDto(order.getCoupon(),
                                order.getCoupon().getUsedDate() != null),   // null 이면 false, null 이 아니면 true
                        UserIdResponseDto.toDto(order.getSeller()))).toList();

    }

//     아이템 종류로 조회 (판매중)
    @Transactional(readOnly = true)
    public List<OrderSimpleResponseDto> getAllOrdersByItemId(Long itemId, int page, int size) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 품목을 조회할 수 없습니다."));
        Pageable pageable = PageRequest.of(page, size);
        List<Orders> orders = orderRepository.findAllByItemAndStatus(item, OrderStatus.AVAILABLE, pageable);
        return orders.stream().map(order ->
                OrderSimpleResponseDto.toDto(order,
                        CouponSimpleResponseDto.toDto(order.getCoupon(),
                                order.getCoupon().getUsedDate() != null),
                        UserIdResponseDto.toDto(order.getSeller()))).toList();
    }

    // 거래 요청
    @Transactional
    public void requestOrder(Long orderId, String token) {
        Orders orders = findOrdersById(orderId);
        User user = findUserByToken(token);
        orderRequestRedisRepository.save(orders.getId(), user.getId());
    }

    // 거래 요청 전체 조회
    @Transactional(readOnly = true)
    public List<UserSimpleResponseDto> getAllRequestOrder(Long orderId) {
        return orderRequestRedisRepository.findById(orderId)
                .stream().map(Long::parseLong)
                .map(this::findUserById)
                .map(user -> UserSimpleResponseDto.toDto(user, null))
                .toList();
    }

    // 거래 수락(거래 중)
//    public OrderDetailResponseDto acceptTransaction(TransactionAcceptRequestDto request) {
//        Orders order = findOrdersById(request.getOrderId());
//        User buyer = findUserById(request.getBuyerId());
//    }

    // 거래 완료



    // 거래 수정

    // 거래 삭제
}
