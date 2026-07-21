package com.example;

public class CustomNumericValue {

    private final double value;

    public CustomNumericValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public String toString() {
        return Double.toString(value);
    }
}
