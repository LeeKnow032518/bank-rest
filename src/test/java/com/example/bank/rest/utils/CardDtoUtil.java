package com.example.bank.rest.utils;

import com.example.bank.rest.dto.cards.CardDto;
import com.example.bank.rest.util.CardStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CardDtoUtil {

    private static final LocalDateTime timestamp = LocalDateTime
        .of(2025, 10, 11, 0, 0);


    public static List<CardDto> getAllCardDto(){
        CardDto first = CardDto.builder()
            .maskedCardNumber("**** **** **** 1234")
            .username("IVAN IVANOV")
            .expiryMonth(10)
            .expiryYear(2026)
            .cardStatus(CardStatus.ACTIVE)
            .balance(new BigDecimal("100.99"))
            .build();

        CardDto second = CardDto.builder()
            .maskedCardNumber("**** **** **** 0001")
            .username("IVAN IVANOV")
            .expiryMonth(1)
            .expiryYear(2002)
            .cardStatus(CardStatus.EXPIRED)
            .balance(new BigDecimal("0.00"))
            .build();

        CardDto third = CardDto.builder()
            .maskedCardNumber("**** **** **** 9999")
            .username("OLEG IVANOV")
            .expiryMonth(12)
            .expiryYear(2028)
            .cardStatus(CardStatus.BLOCKED)
            .balance(new BigDecimal("10.99"))
            .build();

        return List.of(first, second, third);
    }
}
