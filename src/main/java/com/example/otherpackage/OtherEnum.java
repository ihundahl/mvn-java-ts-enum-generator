package com.example.otherpackage;

import com.example.OrderStatus;

public enum OtherEnum {
    FIRST(1, "First enum", OrderStatus.PENDING),
    SECOND(2, "Second enum", OrderStatus.SHIPPED),
    THIRD(3, "Third enum", OrderStatus.DELIVERED);

    private final int code;
    private final String message;
    private final OrderStatus status;

    OtherEnum(int code, String message, OrderStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public OrderStatus getStatus() {
        return status;
    }
}