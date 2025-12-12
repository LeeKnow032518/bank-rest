package com.example.bank.rest.util;

public enum CardStatus {
    ACTIVE("ACTIVE"),

    BLOCKED("BLOCKED"),

    EXPIRED("EXPIRED");

    private final String value;

    CardStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
