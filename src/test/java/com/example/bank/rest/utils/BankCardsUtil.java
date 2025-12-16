package com.example.bank.rest.utils;

import com.example.bank.rest.entity.BankCards;
import com.example.bank.rest.util.CardStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class BankCardsUtil {

    private static final LocalDateTime timestamp = LocalDateTime
        .of(2025, 10, 11, 0, 0);

    public static List<BankCards> getAllBankCards(){
        BankCards first = BankCards.builder()
            .id(1)
            .encryptedCardNumber("encryptedFirstNumber")
            .maskedCardNumber("**** **** **** 1234")
            .user(UserDetailsUtil.getAllUserDetails().getFirst())
            .expiryMonth((short) 10)
            .expiryYear((short) 2026)
            .cardStatus(CardStatus.ACTIVE.getValue())
            .balance(new BigDecimal("100.99"))
            .createdAt(timestamp)
            .updatedAt(timestamp)
            .build();

        BankCards second = BankCards.builder()
            .id(2)
            .encryptedCardNumber("encryptedSecondNumber")
            .maskedCardNumber("**** **** **** 0001")
            .user(UserDetailsUtil.getAllUserDetails().getFirst())
            .expiryMonth((short) 1)
            .expiryYear((short) 2002)
            .cardStatus(CardStatus.EXPIRED.getValue())
            .balance(new BigDecimal("0.00"))
            .createdAt(timestamp)
            .updatedAt(timestamp)
            .build();

        BankCards third = BankCards.builder()
            .id(3)
            .encryptedCardNumber("encryptedThirdNumber")
            .maskedCardNumber("**** **** **** 9999")
            .user(UserDetailsUtil.getAllUserDetails().get(1))
            .expiryMonth((short) 12)
            .expiryYear((short) 2028)
            .cardStatus(CardStatus.BLOCKED.getValue())
            .balance(new BigDecimal("10.99"))
            .createdAt(timestamp)
            .updatedAt(timestamp)
            .build();

        return List.of(first, second, third);
    }
}
