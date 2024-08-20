package com.sookmyung.concon.Order.service;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Coupon.dto.CouponSimpleResponseDto;
import com.sookmyung.concon.Coupon.repository.CouponRepository;
import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.Item.repository.ItemRepository;
import com.sookmyung.concon.Order.dto.OrderCreateRequestDto;
import com.sookmyung.concon.Order.dto.OrderDetailResponseDto;
import com.sookmyung.concon.Order.dto.OrderSimpleResponseDto;
import com.sookmyung.concon.Order.entity.OrderStatus;
import com.sookmyung.concon.Order.entity.Orders;
import com.sookmyung.concon.Order.repository.OrderRepository;
import com.sookmyung.concon.User.Entity.User;
import com.sookmyung.concon.User.Jwt.JwtUtil;
import com.sookmyung.concon.User.dto.UserIdResponseDto;
import com.sookmyung.concon.User.dto.UserSimpleResponseDto;
import com.sookmyung.concon.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    private Coupon findCouponById(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 조회할 수 없습니다. "));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 조회할 수 없습니다. "));
    }

    private User findUserByToken(String token) {
        return userRepository.findByEmail(jwtUtil.getEmail(token))
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 조회할 수 없습니다."));
    }

    // 판매 생성
    // TODO : 사진 수정
    public OrderDetailResponseDto createOrder(OrderCreateRequestDto request) {
        Coupon coupon = findCouponById(request.getCouponId());
        User seller = findUserById(request.getSellerId());
        Orders orders = request.toEntity(coupon, seller);
        orderRepository.save(orders);
        return OrderDetailResponseDto.toDto(orders,
                CouponSimpleResponseDto.toDto(coupon, false),
                null, UserSimpleResponseDto.toDto(seller, null));
    }

    // 나의 판매 상품 전체 조회
    public List<OrderSimpleResponseDto> getAllOrders(String token) {
        User seller = findUserByToken(token);
        List<Orders> orders = orderRepository.findBySeller(seller);
        return orders.stream().map(order ->
            OrderSimpleResponseDto.toDto(order,
                    CouponSimpleResponseDto.toDto(order.getCoupon(),
                            order.getCoupon().getUsedDate() != null),
                    UserIdResponseDto.toDto(order.getSeller()))).toList();
    }

    // 나의 판매 상품 조회(진행중)
    public List<OrderSimpleResponseDto> getAllOrdersAvailable(String token) {
        User seller = findUserByToken(token);
        List<Orders> orders = orderRepository.findBySellerAndStatus(seller, OrderStatus.AVAILABLE);
        return orders.stream().map(order ->
                OrderSimpleResponseDto.toDto(order,
                        CouponSimpleResponseDto.toDto(order.getCoupon(),
                                order.getCoupon().getUsedDate() != null),
                        UserIdResponseDto.toDto(order.getSeller()))).toList();

    }

    // 나의 판매 상품 조회(완료)
    public List<OrderSimpleResponseDto> getAllOrdersComplete(String token) {
        User seller = findUserByToken(token);
        List<Orders> orders = orderRepository.findBySellerAndStatus(seller, OrderStatus.COMPLETED);
        return orders.stream().map(order ->
                OrderSimpleResponseDto.toDto(order,
                        CouponSimpleResponseDto.toDto(order.getCoupon(),
                                order.getCoupon().getUsedDate() != null),
                        UserIdResponseDto.toDto(order.getSeller()))).toList();

    }

//     아이템 종류로 조회 (판매중)
    public List<OrderSimpleResponseDto> getAllOrdersByItemId(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 품목을 조회할 수 없습니다."));
        List<Orders> orders = orderRepository.findByItemAndStatus(item, OrderStatus.AVAILABLE);
        return orders.stream().map(order ->
                OrderSimpleResponseDto.toDto(order,
                        CouponSimpleResponseDto.toDto(order.getCoupon(),
                                order.getCoupon().getUsedDate() != null),
                        UserIdResponseDto.toDto(order.getSeller()))).toList();
    }

    // 거래 요청 & 수락

    // 거래 수정

    // 거래 삭제
}
