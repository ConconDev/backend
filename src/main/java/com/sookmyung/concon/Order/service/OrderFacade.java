package com.sookmyung.concon.Order.service;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Coupon.dto.CouponSimpleResponseDto;
import com.sookmyung.concon.Coupon.service.CouponFacade;
import com.sookmyung.concon.Order.dto.OrderDetailResponseDto;
import com.sookmyung.concon.Order.dto.OrderSimpleResponseDto;
import com.sookmyung.concon.Order.entity.Orders;
import com.sookmyung.concon.Order.repository.OrderRepository;
import com.sookmyung.concon.User.dto.UserIdResponseDto;
import com.sookmyung.concon.User.dto.UserSimpleResponseDto;
import com.sookmyung.concon.User.service.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderFacade {
    private final CouponFacade couponFacade;
    private final UserFacade userFacade;
    private final OrderRepository orderRepository;


    public Coupon findCouponById(Long couponId) {
        return couponFacade.findByCouponId(couponId);
    }

    // order 아이디로 거래 찾기
    public Orders findOrdersById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 조회할 수 없습니다. "));
    }

    public OrderDetailResponseDto toDetailDto(Orders orders) {
        CouponSimpleResponseDto couponDto = couponFacade.toSimpleDto(orders.getCoupon());
        UserSimpleResponseDto buyerDto = userFacade.toSimpleDto(orders.getBuyer());
        UserSimpleResponseDto sellerDto = userFacade.toSimpleDto(orders.getSeller());

        return OrderDetailResponseDto.toDto(orders, couponDto, buyerDto, sellerDto);
    }

    public OrderSimpleResponseDto toSimpleDto(Orders orders) {
        CouponSimpleResponseDto couponDto = couponFacade.toSimpleDto(orders.getCoupon());
        UserIdResponseDto sellerDto = UserIdResponseDto.toDto(orders.getSeller());

        return OrderSimpleResponseDto.toDto(orders, couponDto, sellerDto);
    }
}
