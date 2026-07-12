package com.example.otherpackage;

import java.util.List;

import com.example.OrderStatus;

public enum OtherEnum {
    FIRST(1, "First enum", OrderStatus.PENDING, List.of("item1", "item2")),
    SECOND(2, "Second enum", OrderStatus.SHIPPED, List.of("item3", "item4")),
    THIRD(3, "Third enum", OrderStatus.DELIVERED, List.of("item5", "item6"));

    private final int code;
    private final String message;
    private final OrderStatus status;
    private final List<String> listItems;

    OtherEnum(int code, String message, OrderStatus status, List<String> listItems) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.listItems = listItems;
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

    public List<String> getListItems() {
        return listItems;
    }
}