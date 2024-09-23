package com.sookmyung.concon.Order.service;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Coupon.dto.CouponSimpleResponseDto;
import com.sookmyung.concon.Coupon.repository.CouponRepository;
import com.sookmyung.concon.Coupon.service.CouponFacade;
import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.Item.dto.ItemSimpleResponseDto;
import com.sookmyung.concon.Item.repository.ItemRepository;
import com.sookmyung.concon.Order.dto.*;
import com.sookmyung.concon.Order.entity.OrderStatus;
import com.sookmyung.concon.Order.entity.Orders;
import com.sookmyung.concon.Order.repository.OrderRepository;
import com.sookmyung.concon.Photo.service.PhotoFacade;
import com.sookmyung.concon.Photo.service.PhotoService;
import com.sookmyung.concon.User.Entity.User;
import com.sookmyung.concon.User.dto.UserSimpleResponseDto;
import com.sookmyung.concon.User.service.OrderUserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// TODO : 사진
@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final CouponFacade couponFacade;
    private final ItemRepository itemRepository;
    private final OrderUserFacade orderUserFacade;

    private final PhotoFacade photoFacade;
    private final OrderFacade orderFacade;

    // 메소드 추출

    // 판매 생성
    // TODO : 사진 수정
    @Transactional
    public OrderDetailResponseDto createOrder(OrderCreateRequestDto request) {
        Coupon coupon = orderFacade.findCouponById(request.getCouponId());
        Item item = coupon.getItem();
        User seller = orderUserFacade.findUserById(request.getSellerId());
        Orders orders = request.toEntity(coupon, seller);
        orderRepository.save(orders);
        return OrderDetailResponseDto.toDto(orders,
                CouponSimpleResponseDto.toDto(coupon,
                        ItemSimpleResponseDto.toDto(item, photoFacade.getItemPhotoUrl(item)),
                        coupon.getUsedDate() != null),
                UserSimpleResponseDto.toDto(seller, photoFacade.getUserPhotoUrl(seller)),
                null);
    }

    // 거래 아이디로 단일 조회
    @Transactional(readOnly = true)
    public OrderDetailResponseDto getOrderByOrderId(Long orderId) {
        Orders order = orderFacade.findOrdersById(orderId);
        return orderFacade.toDetailDto(order);
    }

    // 나의 판매 상품 전체 조회
    @Transactional(readOnly = true)
    public List<OrderSimpleResponseDto> getAllOrders(String token, int page, int size) {
        User seller = orderUserFacade.findUserByToken(token);
        Pageable pageable = PageRequest.of(page, size);
        List<Orders> orders = orderRepository.findBySeller(seller, pageable);
        return orderUserFacade.toOrderSimpleDtoList(orders);
    }

    // user의 판매 상품 전체 조회
    @Transactional(readOnly = true)
    public List<OrderSimpleResponseDto> getOrdersByUserId(Long userId, int page, int size) {
        return orderUserFacade.getOrdersByUserId(userId, page, size);
    }

    // 나의 판매 상품 조회(진행중)
    @Transactional(readOnly = true)
    public List<OrderSimpleResponseDto> getAllOrdersAvailable(String token, int page, int size) {
        User seller = orderUserFacade.findUserByToken(token);
        Pageable pageable = PageRequest.of(page, size);
        List<Orders> orders = orderRepository.findBySellerAndStatus(seller, OrderStatus.AVAILABLE, pageable);
        return orderUserFacade.toOrderSimpleDtoList(orders);
    }

    // 나의 판매 상품 조회(완료)
    @Transactional(readOnly = true)
    public List<OrderSimpleResponseDto> getAllOrdersComplete(String token, int page, int size) {
        User seller = orderUserFacade.findUserByToken(token);
        Pageable pageable = PageRequest.of(page, size);
        List<Orders> orders = orderRepository.findBySellerAndStatus(seller, OrderStatus.COMPLETED, pageable);
        return orderUserFacade.toOrderSimpleDtoList(orders);

    }

//     아이템 종류로 조회 (판매중)
    @Transactional(readOnly = true)
    public List<OrderSimpleResponseDto> getAllOrdersByItemId(Long itemId, int page, int size) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 품목을 조회할 수 없습니다."));
        Pageable pageable = PageRequest.of(page, size);
        List<Orders> orders = orderRepository.findAllByItemAndStatus(item, OrderStatus.AVAILABLE, pageable);
        return orderUserFacade.toOrderSimpleDtoList(orders);
    }

    // 상품 구매
    public void buyOrder(Long orderId, String token) {
        User buyer = orderUserFacade.findUserByToken(token);
        Orders order = orderFacade.findOrdersById(orderId);


    }

    public void buyRedirect() {

    }

    // 거래 삭제
    @Transactional
    public void deleteOrder(Long orderId) {
        Orders order = orderFacade.findOrdersById(orderId);
        orderRepository.delete(order);
    }
}
