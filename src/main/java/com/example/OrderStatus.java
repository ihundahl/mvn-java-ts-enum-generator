package com.example;

public enum OrderStatus {
    PENDING(1, "Awaiting processing", new CustomNumericValue(1.0)),
    SHIPPED(2, "On its way", new CustomNumericValue(2.0)),
    DELIVERED(3, "Arrived at destination", new CustomNumericValue(3.0));

    private final int code;
    private final String message;
    private final CustomNumericValue customValue;

    OrderStatus(int code, String message, CustomNumericValue customValue) {
        this.code = code;
        this.message = message;
        this.customValue = customValue;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public CustomNumericValue getCustomValue() {
        return customValue;
    }
}