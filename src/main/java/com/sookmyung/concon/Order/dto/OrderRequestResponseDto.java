package com.sookmyung.concon.Order.dto;

import com.sookmyung.concon.Order.entity.Orders;
import com.sookmyung.concon.User.Entity.User;
import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class OrderRequestResponseDto {
    private Long orderId;
    private String orderTitle;
    private Long requesterId;
    private String requesterName;

    public static OrderRequestResponseDto toDto(Orders orders, User requester) {
        return OrderRequestResponseDto.builder()
                .orderId(orders.getId())
                .orderTitle(orders.getTitle())
                .requesterId(requester.getId())
                .requesterName(requester.getUsername())
                .build();
    }
}
