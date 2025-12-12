package com.example.bank.rest.entity;

import com.example.bank.rest.util.CardStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class BankCards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String encryptedCardNumber;

    private String maskedCardNumber;

    private String cardOwner;

    private Short expiryMonth;

    private Short expiryYear;

    private String cardStatus;

    private BigDecimal balance;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
