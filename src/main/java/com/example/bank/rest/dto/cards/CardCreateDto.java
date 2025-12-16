package com.example.bank.rest.dto.cards;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@Builder(toBuilder = true)
public class CardCreateDto {

    private String cardNumber;

    private Integer expiryMonth;

    private Integer expiryYear;

    private BigDecimal balance;
}
