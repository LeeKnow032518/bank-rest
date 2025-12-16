package com.example.bank.rest.service.admin;

import com.example.bank.rest.dto.cards.*;
import com.example.bank.rest.dto.users.UserCreateDto;
import com.example.bank.rest.dto.users.UserDto;
import com.example.bank.rest.entity.BankCards;
import com.example.bank.rest.entity.UserDetails;
import com.example.bank.rest.exception.AccessToCardDeniedException;
import com.example.bank.rest.exception.NotEnoughMoneyException;
import com.example.bank.rest.repository.CardRepository;
import com.example.bank.rest.repository.UserRepository;
import com.example.bank.rest.util.CardStatus;
import com.example.bank.rest.util.RoleEnum;
import com.example.bank.rest.util.mapstruct.CardCreateMapper;
import com.example.bank.rest.util.mapstruct.CardMapper;
import com.example.bank.rest.util.mapstruct.UserCreateMapper;
import com.example.bank.rest.util.mapstruct.UserDetailsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final CardRepository cardRepository;

    private final UserRepository userRepository;

    public Page<CardDto> findAllCards(int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        return cardRepository.findAll(pageable).map(CardMapper.INSTANCE::CardDataToCardDto);
    }

    public ResponseEntity<List<CardDto>> findByUserId(Integer id){
        List<CardDto> cards = cardRepository.findByUserId_Id(id).stream()
            .map(CardMapper.INSTANCE::CardDataToCardDto).toList();

        return ResponseEntity.ok(cards);
    }

    public ResponseEntity<CardDto> createCard(Integer id, CardCreateDto cardDto) throws Exception {
        BankCards data = CardCreateMapper.INSTANCE.CardCreateDtoToCardData(cardDto);
        data.setEncryptedCardNumber(CardNumberEncryptor.encrypt(cardDto.getCardNumber()));
        data.setCreatedAt(LocalDateTime.now());
        data.setMaskedCardNumber(CardNumberEncryptor.maskCardNumber(cardDto.getCardNumber()));
        data.setCardStatus(CardStatus.ACTIVE.getValue());

        UserDetails user = userRepository.findById(id).orElse(null);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        data.setUser(user);

        cardRepository.save(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(CardMapper.INSTANCE.CardDataToCardDto(data));
    }

    public ResponseEntity<Void> deleteCard(Integer id){
        if(cardRepository.existsById(id)) {
            cardRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<CardDto> changeStatus(Integer id, CardStatusChange status){
        BankCards card = cardRepository.findById(id).orElse(null);
        if(card == null) {
            return ResponseEntity.notFound().build();
        }

        card.setCardStatus(status.getCardStatus().getValue());
        card.setUpdatedAt(LocalDateTime.now());

        cardRepository.save(card);
        return ResponseEntity.ok(CardMapper.INSTANCE.CardDataToCardDto(card));
    }

    public Page<UserDto> findAllUsers(int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        return userRepository.findAll(pageable).map(UserDetailsMapper.INSTANCE::UserDetailsToUserDto);
    }

    public UserDto createUser(UserCreateDto user) throws Exception{
        UserDetails data = UserCreateMapper.INSTANCE.UserCreateDtoToUserDetails(user);
        data.setRole(RoleEnum.USER.getValue());

        userRepository.save(data);
        return UserDetailsMapper.INSTANCE.UserDetailsToUserDto(data);
    }

    public ResponseEntity<Void> deleteUser(Integer id){
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<TransferResponse> transferMoney(TransferRequest request) throws Exception {
        BankCards from = cardRepository.findByEncryptedCardNumber(CardNumberEncryptor.encrypt(request.getCardFrom()))
            .orElseThrow(() -> new AccessToCardDeniedException("Карта не доступна для съема денег"));

        BankCards to = cardRepository.findByEncryptedCardNumber(CardNumberEncryptor.encrypt(request.getCardTo()))
            .orElseThrow(() -> new AccessToCardDeniedException("Карта для перевода не доступна"));

        if(from.getBalance().compareTo(request.getAmount()) < 0){
            throw new NotEnoughMoneyException("Недостаточно средств на карте " + from.getMaskedCardNumber());
        }

        from.setBalance(from.getBalance().subtract(request.getAmount()));
        to.setBalance(to.getBalance().add(request.getAmount()));

        cardRepository.save(from);
        cardRepository.save(to);

        return ResponseEntity.status(200)
            .body(new TransferResponse(from.getMaskedCardNumber(), to.getMaskedCardNumber(), request.getAmount()));
    }
}
