package com.example.bank.rest.dto.cards;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CardCreateDto {

    private String cardNumber;

    private Integer expiryMonth;

    private Integer expiryYear;

    private BigDecimal balance;
}
