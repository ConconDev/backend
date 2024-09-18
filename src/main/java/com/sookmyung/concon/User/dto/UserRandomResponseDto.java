package com.sookmyung.concon.User.dto;

import com.sookmyung.concon.Order.entity.Orders;

import java.util.List;
import java.util.Optional;

public class UserRandomResponseDto {
    private Long userId;
    private String username;
    private List<Optional<Orders>> orders;
}
