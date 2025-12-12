package com.example.bank.rest.util;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RoleEnum {
    ADMIN("ADMIN"),

    USER("USER");

    private final String value;

    RoleEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}