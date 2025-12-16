package com.example.bank.rest.dto.cards;

import com.example.bank.rest.util.CardStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardStatusChange {

    private CardStatus cardStatus;
}
