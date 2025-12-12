package com.example.bank.rest.util.mapstruct;

import com.example.bank.rest.dto.CardCreateDto;
import com.example.bank.rest.dto.CardDto;
import com.example.bank.rest.entity.BankCards;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CardCreateMapper {
    CardCreateMapper INSTANCE = Mappers.getMapper(CardCreateMapper.class);

    BankCards CardCreateDtoToCardData(CardCreateDto dto);
    CardDto CardDataToCardCreateDto(BankCards data);
}
