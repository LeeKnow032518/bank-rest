package com.example.bank.rest.service;

import com.example.bank.rest.dto.cards.*;
import com.example.bank.rest.dto.users.UserCreateDto;
import com.example.bank.rest.dto.users.UserDto;
import com.example.bank.rest.entity.BankCards;
import com.example.bank.rest.entity.UserDetails;
import com.example.bank.rest.exception.AccessToCardDeniedException;
import com.example.bank.rest.exception.NotEnoughMoneyException;
import com.example.bank.rest.repository.CardRepository;
import com.example.bank.rest.repository.UserRepository;
import com.example.bank.rest.service.admin.AdminService;
import com.example.bank.rest.util.CardStatus;
import com.example.bank.rest.util.RoleEnum;
import com.example.bank.rest.utils.BankCardsUtil;
import com.example.bank.rest.utils.CardDtoUtil;
import com.example.bank.rest.utils.UserDetailsUtil;
import com.example.bank.rest.utils.UserDtoUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CardRepository cardRepository;

    @BeforeEach
    public void setUp(){
        adminService = new AdminService(cardRepository, userRepository);
    }

    @Test
    @DisplayName("Test find all cards method")
    public void givenVoid_whenFindAllCards_thenReturnListOfCards(){
        // given
        Pageable pageable = PageRequest.of(0, 10);
        Page<BankCards> cards = new PageImpl<>(BankCardsUtil.getAllBankCards());
        when(cardRepository.findAll(pageable)).thenReturn(cards);

        // when
        Page<CardDto> result = adminService.findAllCards(0, 10);
        List<CardDto> expected = CardDtoUtil.getAllCardDto();

        // then
        for(CardDto card : result){
            Assertions.assertTrue(expected.contains(card));
        }
    }

    @Test
    @DisplayName("Test find by userId method")
    public void givenUserId_whenFindByUserId_thenReturnResponseEntityListCardDto(){
        // given
        Integer id = 1;

        // when
        when(cardRepository.findByUserId_Id(id)).thenReturn(BankCardsUtil.getAllBankCards().subList(0, 2));

        ResponseEntity<List<CardDto>> result = adminService.findByUserId(id);
        List<CardDto> expected = CardDtoUtil.getAllCardDto().subList(0, 2);

        //then
        Assertions.assertEquals(result.getBody(), expected);
        Assertions.assertEquals(result.getStatusCode(), HttpStatusCode.valueOf(200));
    }

    @Test
    @DisplayName("Test create card method")
    public void givenIdCardCreateDto_whenCreateCard_thenReturnResponseEntityCardDto() throws Exception {
        // given
        Integer id = 2;
        CardCreateDto cardCreate = CardCreateDto.builder()
            .cardNumber("1111 2222 3333 4444")
            .expiryMonth(1)
            .expiryYear(2030)
            .balance(new BigDecimal("100.00")).build();

        // when
        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(UserDetailsUtil.getAllUserDetails().get(1)));
        ResponseEntity<CardDto> result = adminService.createCard(id, cardCreate);
        CardDto expected = CardDto.builder()
            .maskedCardNumber("**** **** **** 4444")
            .username("OLEG IVANOV")
            .expiryMonth(1)
            .expiryYear(2030)
            .cardStatus(CardStatus.ACTIVE)
            .balance(new BigDecimal("100.00")).build();

        // then
        Assertions.assertEquals(expected, result.getBody());
        Assertions.assertEquals(result.getStatusCode(), HttpStatusCode.valueOf(201));
    }

    @Test
    @DisplayName("Test create card method with no user")
    public void givenIdCardCreateDto_whenCreateCard_thenReturnNotFound() throws Exception {
        // given
        Integer id = 3;
        CardCreateDto cardCreate = CardCreateDto.builder()
            .cardNumber("1111 2222 3333 4444")
            .expiryMonth(1)
            .expiryYear(2030)
            .balance(new BigDecimal("100.00")).build();

        // when
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        ResponseEntity<CardDto> result = adminService.createCard(id, cardCreate);

        Assertions.assertEquals(result.getStatusCode(), HttpStatusCode.valueOf(404));
        Assertions.assertNull(result.getBody());
    }

    @Test
    @DisplayName("Test delete card method")
    public void givenIntegerId_whenDeleteCard_thenReturnResponseEntityVoid(){
        // given
        Integer id = 1;

        // when
        when(cardRepository.existsById(id)).thenReturn(true);
        ResponseEntity<Void> result = adminService.deleteCard(id);

        // then
        Assertions.assertNull(result.getBody());
        Assertions.assertEquals(result.getStatusCode(), HttpStatusCode.valueOf(204));
    }


    @Test
    @DisplayName("Test delete card method without user")
    public void givenWrongId_whenDeleteCard_ThenReturnResponseEntityNotFound(){
        // given
        Integer id = 4;

        // when
        when(cardRepository.existsById(id)).thenReturn(false);
        ResponseEntity<Void> result = adminService.deleteCard(id);

        // then
        Assertions.assertEquals(result.getStatusCode(), HttpStatusCode.valueOf(404));
        Assertions.assertNull(result.getBody());
    }

    @Test
    @DisplayName("Test change status method")
    public void givenIdAndStatus_whenChangeStatus_thenReturnResponseEntityCardDto(){
        // given
        Integer id = 1;
        CardStatusChange change = new CardStatusChange(CardStatus.BLOCKED);

        // when
        when(cardRepository.findById(id)).thenReturn(Optional.ofNullable(BankCardsUtil.getAllBankCards().getFirst()));
        ResponseEntity<CardDto> result = adminService.changeStatus(id, change);
        CardDto expected = CardDtoUtil.getAllCardDto().getFirst();
        expected.setCardStatus(CardStatus.BLOCKED);

        // then
        Assertions.assertEquals(result.getBody(), expected);
        Assertions.assertEquals(result.getStatusCode(), HttpStatusCode.valueOf(200));
    }

    @Test
    @DisplayName("Test change status method with no card")
    public void givenIdAndStatus_whenChangeStatus_thenReturnNotFound() {
        // given
        Integer id = 1;
        CardStatusChange change = new CardStatusChange(CardStatus.BLOCKED);

        // when
        when(cardRepository.findById(id)).thenReturn(Optional.empty());
        ResponseEntity<CardDto> result = adminService.changeStatus(id, change);

        // then
        Assertions.assertEquals(result.getStatusCode(), HttpStatusCode.valueOf(404));
        Assertions.assertNull(result.getBody());
    }

    @Test
    @DisplayName("Test find all users method")
    public void givenVoid_whenFindAllUsers_thenReturnPageUserDto(){
        // given
        Pageable pageable = PageRequest.of(0, 10);
        Page<UserDetails> users = new PageImpl<>(UserDetailsUtil.getAllUserDetails());

        // when
        when(userRepository.findAll(pageable)).thenReturn(users);
        Page<UserDto> result = adminService.findAllUsers(0, 10);
        List<UserDto> expected = UserDtoUtil.getAllUserDto();

        // then
        for(UserDto user : result){
            Assertions.assertTrue(expected.contains(user));
        }
    }

    @Test
    @DisplayName("Test create user method")
    public void givenUserCreateDto_whenCreateUser_thenReturnUserDto() throws Exception {
        // given
        UserCreateDto user = UserCreateDto.builder()
            .login("userDima")
            .password("passwordDima")
            .username("DMITRY PETROV").build();

        // when
        UserDto result = adminService.createUser(user);
        UserDto expected = UserDto.builder()
            .role(RoleEnum.USER.getValue())
            .username("DMITRY PETROV").build();

        // then
        Assertions.assertEquals(result, expected);
    }

    @Test
    @DisplayName("Test delete user method")
    public void givenIntegerId_whenDeleteUser_thenReturnResponseEntityVoid(){
        // given
        Integer id = 1;

        // when
        when(userRepository.existsById(id)).thenReturn(true);
        ResponseEntity<Void> result = adminService.deleteUser(id);

        // then
        Assertions.assertEquals(result.getStatusCode(), HttpStatusCode.valueOf(204));
        Assertions.assertNull(result.getBody());
    }

    @Test
    @DisplayName("Test delete user method with wrond user id")
    public void givenWrongIntegerId_whenDeleteUser_thenReturnNotFound(){
        // given
        Integer id = 4;

        // when
        when(userRepository.existsById(id)).thenReturn(false);
        ResponseEntity<Void> result = adminService.deleteUser(id);

        // then
        Assertions.assertEquals(result.getStatusCode(), HttpStatusCode.valueOf(404));
        Assertions.assertNull(result.getBody());
    }

    @Test
    @DisplayName("Test transfer money method")
    public void givenTransferRequest_whenTransferMoney_thenReturnTransferResponse() throws Exception {
        // given
        TransferRequest request = TransferRequest.builder()
            .cardFrom("1234 1234 1234 1234")
            .cardTo("0001 0001 0001 0001")
            .amount(new BigDecimal("100"))
            .build();

        // when
        when(cardRepository.findByEncryptedCardNumber(anyString()))
            .thenReturn(Optional.ofNullable(BankCardsUtil.getAllBankCards().getFirst()))
            .thenReturn(Optional.ofNullable(BankCardsUtil.getAllBankCards().get(1)));

        ResponseEntity<TransferResponse> result = adminService.transferMoney(request);
        TransferResponse expected = TransferResponse.builder()
            .maskedCardFrom("**** **** **** 1234")
            .maskedCardTo("**** **** **** 0001")
            .amount(new BigDecimal("100"))
            .build();

        // then
        Assertions.assertEquals(result.getBody(), expected);
        Assertions.assertEquals(result.getStatusCode(), HttpStatusCode.valueOf(200));

    }

    @Test
    @DisplayName("Test transfer money method no from card")
    public void givenTransferRequestWrongFrom_whenTransferMoney_thenTrowAccessToCardDeniedException() throws Exception {
        // given
        TransferRequest request = TransferRequest.builder()
            .cardFrom("1234 1234 1234 1234")
            .cardTo("0001 0001 0001 0001")
            .amount(new BigDecimal("100"))
            .build();

        // when
        when(cardRepository.findByEncryptedCardNumber(anyString())).thenReturn(Optional.empty());

        // then
        Exception exception = Assertions
            .assertThrows(AccessToCardDeniedException.class,() -> adminService.transferMoney(request));
        Assertions.assertEquals(exception.getMessage(), "Карта не доступна для съема денег");
    }

    @Test
    @DisplayName("Test transfer money method no to card")
    public void givenTransferRequestWrongTo_whenTransferMoney_thenTrowAccessToCardDeniedException() throws Exception {
        // given
        TransferRequest request = TransferRequest.builder()
            .cardFrom("1234 1234 1234 1234")
            .cardTo("0001 0001 0001 0001")
            .amount(new BigDecimal("100"))
            .build();

        // when
        when(cardRepository.findByEncryptedCardNumber(anyString()))
            .thenReturn(Optional.ofNullable(BankCardsUtil.getAllBankCards().get(1)))
            .thenReturn(Optional.empty());

        // then
        Exception exception = Assertions
            .assertThrows(AccessToCardDeniedException.class,() -> adminService.transferMoney(request));
        Assertions.assertEquals(exception.getMessage(), "Карта для перевода не доступна");
    }

    @Test
    @DisplayName("Test transfer money method small balance")
    public void givenTransferRequestWrongBalance_whenTransferMoney_thenTrowAccessToCardDeniedException() throws Exception {
        // given
        TransferRequest request = TransferRequest.builder()
            .cardFrom("1234 1234 1234 1234")
            .cardTo("0001 0001 0001 0001")
            .amount(new BigDecimal("1000"))
            .build();

        // when
        when(cardRepository.findByEncryptedCardNumber(anyString()))
            .thenReturn(Optional.ofNullable(BankCardsUtil.getAllBankCards().getFirst()))
            .thenReturn(Optional.ofNullable(BankCardsUtil.getAllBankCards().get(1)));

        // then
        Exception exception = Assertions.assertThrows(NotEnoughMoneyException.class, () -> adminService.transferMoney(request));
        Assertions.assertEquals(exception.getMessage(), "Недостаточно средств на карте **** **** **** 1234" );
    }
}
