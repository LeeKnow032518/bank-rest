package com.example.bank.rest.dto.cards;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransferRequest {

    private String cardFrom;

    private String cardTo;

    private BigDecimal amount;
}
