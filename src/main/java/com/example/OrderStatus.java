package com.example;

public enum OrderStatus {
    PENDING(1, "Awaiting processing"),
    SHIPPED(2, "On its way"),
    DELIVERED(3, "Arrived at destination");

    private final int code;
    private final String message;

    OrderStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // Jackson automatically finds these and treats them as keys ("code" and
    // "message")
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}