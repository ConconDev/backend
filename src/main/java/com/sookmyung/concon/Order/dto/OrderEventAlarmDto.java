package com.sookmyung.concon.Order.dto;

import com.sookmyung.concon.Order.entity.Orders;
import com.sookmyung.concon.User.Entity.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderEventAlarmDto {
    private Long orderId;
    private String orderTitle;
    private Long sellerId;
    private String sellerName;
    private Long buyerId;
    private String buyerName;

    public static OrderEventAlarmDto toDto(Orders orders) {
        return OrderEventAlarmDto.builder()
                .orderId(orders.getId())
                .orderTitle(orders.getTitle())
                .sellerId(orders.getSeller().getId())
                .sellerName(orders.getSeller().getUsername())
                .buyerId(orders.getBuyer().getId())
                .buyerName(orders.getBuyer().getUsername())
                .build();
    }
}
