package com.example.bank.rest.util.mapstruct;

import com.example.bank.rest.dto.cards.CardDto;
import com.example.bank.rest.entity.BankCards;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CardMapper {

    CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);

    @Mapping(source = "user.username", target = "username")
    CardDto CardDataToCardDto(BankCards data);
    BankCards CardDroToCardData(CardDto dto);
}
