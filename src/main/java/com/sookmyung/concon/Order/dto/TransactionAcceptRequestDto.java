package com.sookmyung.concon.Order.dto;

import lombok.Getter;

@Getter
public class TransactionAcceptRequestDto {
    private Long orderId;
    private Long buyerId;
}
