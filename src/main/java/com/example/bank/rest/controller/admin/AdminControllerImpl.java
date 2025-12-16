package com.example.bank.rest.controller.admin;

import com.example.bank.rest.dto.cards.*;
import com.example.bank.rest.dto.users.UserCreateDto;
import com.example.bank.rest.dto.users.UserDto;
import com.example.bank.rest.service.admin.AdminService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@Validated
@RestController
@RequestMapping("/api")
public class AdminControllerImpl implements AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminControllerImpl(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/cards",
        produces = { "application/json" }
    )public ResponseEntity<Page<CardDto>> getCardsAdmin(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size){

        return ResponseEntity.status(200).body(adminService.findAllCards(page, size));
    }

    @RequestMapping(
        method = RequestMethod.GET,
        value = "/users/{id}/cards",
        produces = {"application/json"}
    )
    public ResponseEntity<List<CardDto>> getCardsByUserId(@PathVariable Integer id){
        return adminService.findByUserId(id);
    }

    @Override
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/users/{id}/cards",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    public ResponseEntity<CardDto> apiAdminCardsPost(
        @PathVariable Integer id,

        @Parameter(name = "CardCreateRequest", description = "", required = true)
        @Valid
        @RequestBody CardCreateDto cardCreateRequest
    ) throws Exception{
        return adminService.createCard(id, cardCreateRequest);
    }

    @Override
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/cards/{card_id}"
    )
    public ResponseEntity<Void> apiAdminCardsIdDelete(@PathVariable Integer cardId) {
        return adminService.deleteCard(cardId);
    }

    @Override
    @RequestMapping(
        method = RequestMethod.PATCH,
        value = "/cards/{card_id}/status",
        consumes = { "application/json" }
    )
    public ResponseEntity<CardDto> apiAdminCardsIdStatusPatch(@PathVariable Integer cardId,
                                                              @RequestBody CardStatusChange cardStatus) {
        return adminService.changeStatus(cardId, cardStatus);
    }


    @Override
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/users",
        produces = { "application/json" }
    )public ResponseEntity<Page<UserDto>> getUsersAdmin(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size){

        return ResponseEntity.status(200).body(adminService.findAllUsers(page, size));
    }


    @Override
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/users",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    public ResponseEntity<UserDto> apiAdminUsersPost(
        @Parameter(name = "UserCreateRequest", description = "", required = true)
        @Valid
        @RequestBody UserCreateDto userCreateRequest
    ) throws Exception{
        return ResponseEntity.status(201).body(adminService.createUser(userCreateRequest));
    }

    @Override
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/users/{id}"
    )
    public ResponseEntity<Void> apiAdminUsersIdDelete(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH)
        @PathVariable("id")
        Integer id
    ) {
        return adminService.deleteUser(id);
    }

    @RequestMapping(
        method = RequestMethod.POST,
        value = "/users/{id}/cards/transfers",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    public ResponseEntity<TransferResponse> apiTransfersPost(
        @PathVariable Integer userId,

        @Parameter(name = "TransferRequest", description = "", required = true)
        @Valid
        @RequestBody TransferRequest transferRequest
    ) throws Exception {
        return adminService.transferMoney(transferRequest);
    }
}
