package com.sookmyung.concon.User.service;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Coupon.dto.CouponSimpleResponseDto;
import com.sookmyung.concon.Order.dto.OrderCreateRequestDto;
import com.sookmyung.concon.Order.dto.OrderDetailResponseDto;
import com.sookmyung.concon.Order.dto.OrderSimpleResponseDto;
import com.sookmyung.concon.Order.entity.Orders;
import com.sookmyung.concon.Order.repository.OrderRepository;
import com.sookmyung.concon.Order.service.OrderFacade;
import com.sookmyung.concon.Order.service.OrderService;
import com.sookmyung.concon.Photo.service.PhotoManager;
import com.sookmyung.concon.User.Entity.User;
import com.sookmyung.concon.User.Jwt.JwtUtil;
import com.sookmyung.concon.User.dto.UserDetailResponseDto;
import com.sookmyung.concon.User.dto.UserIdResponseDto;
import com.sookmyung.concon.User.dto.UserSimpleResponseDto;
import com.sookmyung.concon.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderUserFacade {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderFacade orderFacade;
    private final JwtUtil jwtUtil;

    private final static String PREFIX = "item/";
    private final PhotoManager photoManager;


    private String makePrefix(Orders orders) {
        return PREFIX + orders.getId();
    }

    /*
    dto 변환 메소드 추출
     */


    // List<OrderSimpleResponseDto> 변환 메소드
    public List<OrderSimpleResponseDto> toOrderSimpleDtoList(List<Orders> orders) {
        return orders.stream().map(orderFacade::toSimpleDto).toList();

    }

    @Transactional(readOnly = true)
    public List<UserDetailResponseDto> toUserDetailResponseDtos(List<User> randomUsers) {
        List<UserDetailResponseDto> response = new ArrayList<>();
        for (User user : randomUsers) {
            UserDetailResponseDto dto = toUserDetailResponseDto(user.getId());
            response.add(dto);
        }
        return response;
    }

    @Transactional(readOnly = true)
    public UserDetailResponseDto toUserDetailResponseDto(Long userId) {
        User user = findUserById(userId);
        List<OrderSimpleResponseDto> top2OrderByUser =
                        orderRepository.findTop2BySellerOrderByCreatedDateDesc(user)
                                .stream().map(orderFacade::toSimpleDto).toList();

        String photoUrl = photoManager.getPhoto("user/" + user.getId(), user.getProfilePhotoName(), user.getProfileCreatedDate());
        String QRPhotoUrl = photoManager.getPhoto("user/" + user.getId() + "/QR", user.getQRImageName(), user.getQRImageCreateDate());
        return UserDetailResponseDto.toDto(user, top2OrderByUser, photoUrl, QRPhotoUrl);
    }

    @Transactional(readOnly = true)
    public List<OrderSimpleResponseDto> getOrdersByUserId(Long userId, int page, int size) {
        User seller = findUserById(userId);
        Pageable pageable = PageRequest.of(page, size);
        List<Orders> orders = orderRepository.findBySeller(seller, pageable);
        return toOrderSimpleDtoList(orders);
    }

    @Transactional(readOnly = true)
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public User findUserByToken(String token) {
        return userRepository.findByEmail(jwtUtil.getEmail(token.split(" ")[1]))
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다. "));

    }
}
