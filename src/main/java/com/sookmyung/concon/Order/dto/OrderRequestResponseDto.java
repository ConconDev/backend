package com.sookmyung.concon.Order.dto;

import com.sookmyung.concon.Order.entity.Orders;
import com.sookmyung.concon.User.Entity.User;
import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class OrderRequestResponseDto {
    private Long orderId;
    private Long requesterId;
    private String requesterName;
    private String qrImage;

    public static OrderRequestResponseDto toDto(Orders orders, User requester, String qrImage) {
        return OrderRequestResponseDto.builder()
                .orderId(orders.getId())
                .requesterId(requester.getId())
                .requesterName(requester.getUsername())
                .qrImage(qrImage)
                .build();
    }
}
