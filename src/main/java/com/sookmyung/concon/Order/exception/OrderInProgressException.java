package com.sookmyung.concon.Order.exception;

public class OrderInProgressException extends RuntimeException {
    public OrderInProgressException(String message) {
        super(message);
    }
}
