package com.example.bank.rest.service;

import com.example.bank.rest.dto.CardCreateDto;
import com.example.bank.rest.dto.CardDto;
import com.example.bank.rest.entity.BankCards;
import com.example.bank.rest.repository.CardRepository;
import com.example.bank.rest.util.mapstruct.CardCreateMapper;
import com.example.bank.rest.util.mapstruct.CardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final CardRepository cardRepository;

    public Page<CardDto> findAllCards(int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        return cardRepository.findAll(pageable).map(CardMapper.INSTANCE::CardDataToCardDto);
    }

    public CardDto createCard(CardCreateDto cardDto) throws Exception {
        BankCards data = CardCreateMapper.INSTANCE.CardCreateDtoToCardData(cardDto);
        data.setEncryptedCardNumber(CardNumberEncryptor.encrypt(cardDto.getCardNumber()));
        data.setCreatedAt(LocalDateTime.now());
        data.setMaskedCardNumber(CardNumberEncryptor.maskCardNumber(cardDto.getCardNumber()));

        cardRepository.save(data);
        return CardMapper.INSTANCE.CardDataToCardDto(data);
    }
}
