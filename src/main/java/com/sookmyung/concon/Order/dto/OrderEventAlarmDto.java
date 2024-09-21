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

    public static OrderEventAlarmDto toDto(Orders orders, User buyer) {
        return OrderEventAlarmDto.builder()
                .orderId(orders.getId())
                .sellerId(orders.getSeller().getId())
                .sellerName(orders.getSeller().getUsername())
                .buyerId(buyer.getId())
                .buyerName(buyer.getUsername())
                .build();
    }
}
