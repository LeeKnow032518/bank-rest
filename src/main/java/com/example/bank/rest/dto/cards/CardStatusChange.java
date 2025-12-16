package com.example.bank.rest.dto.cards;

import com.example.bank.rest.util.CardStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardStatusChange {

    private CardStatus cardStatus;
}
