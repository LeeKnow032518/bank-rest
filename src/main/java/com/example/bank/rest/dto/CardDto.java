package com.example.bank.rest.dto;

import com.example.bank.rest.util.CardStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CardDto {

    private String maskedCardNumber;

    private String cardOwner;

    private Integer expiryMonth;

    private Integer expiryYear;

    private CardStatus cardStatus;

    private BigDecimal balance;
}
