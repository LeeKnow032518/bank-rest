package com.example.bank.rest.dto.cards;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class TransferResponse {

    private String maskedCardFrom;

    private String maskedCardTo;

    private BigDecimal amount;
}
