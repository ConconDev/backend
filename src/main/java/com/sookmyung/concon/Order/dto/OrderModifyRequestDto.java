package com.sookmyung.concon.Order.dto;

import lombok.Getter;

@Getter
public class OrderModifyRequestDto {
    private Long orderId;
    private String title;
    private String content;
}
