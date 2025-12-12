package com.example.bank.rest.controller.admin;

import com.example.bank.rest.dto.CardCreateDto;
import com.example.bank.rest.dto.CardDto;
import com.example.bank.rest.service.AdminService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@Tag(name = "Карты (ADMIN)", description = "Управление картами (только ADMIN)")
@RestController
@RequestMapping("/api/admin")
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

    @Override
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/cards",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    public ResponseEntity<CardDto> apiAdminCardsPost(
        @Parameter(name = "CardCreateRequest", description = "", required = true)
        @Valid
        @RequestBody CardCreateDto cardCreateRequest
    ) throws Exception{
        return ResponseEntity.status(201).body(adminService.createCard(cardCreateRequest));
    }
}
