package com.sookmyung.concon.Order.entity;

public enum OrderStatus {
    AVAILABLE,      // 판매 중
    COMPLETED,      // 거래 완료
    WAITING,        // 거래 요청 대기
    IN_PROGRESS     // 거래 진행 중
}
