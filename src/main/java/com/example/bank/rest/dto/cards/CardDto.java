package com.example.bank.rest.dto.cards;

import com.example.bank.rest.util.CardStatus;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {

    private String maskedCardNumber;

    private String username;

    private Integer expiryMonth;

    private Integer expiryYear;

    private CardStatus cardStatus;

    private BigDecimal balance;
}
