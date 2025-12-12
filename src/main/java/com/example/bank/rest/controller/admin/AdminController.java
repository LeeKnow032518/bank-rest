package com.example.bank.rest.controller.admin;

import com.example.bank.rest.dto.CardCreateDto;
import com.example.bank.rest.dto.CardDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public interface AdminController {
    /**
     * GET /api/admin/cards : Получить все карты (ADMIN)
     *
     * @param page  (optional, default to 0)
     * @param size  (optional, default to 20)
     * @return Список всех карт (status code 200)
     */
    @Operation(
        operationId = "apiAdminCardsGet",
        summary = "Получить все карты (ADMIN)",
        tags = { "Карты (ADMIN)" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Список всех карт", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = CardDto.class))
            })
        }
//        security = {
//            @SecurityRequirement(name = "bearerAuth")
//        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/admin/cards",
        produces = { "application/json" }
    )
    ResponseEntity<Page<CardDto>> getCardsAdmin(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size);

    /**
     * POST /cards : Создать новую карту (ADMIN)
     *
     * @param cardCreateRequest  (required)
     * @return Карта создана (status code 201)
     *         or Доступ запрещён (не ADMIN) (status code 403)
     */
    @Operation(
        operationId = "apiAdminCardsPost",
        summary = "Создать новую карту (ADMIN)",
        tags = { "Карты (ADMIN)" },
        responses = {
            @ApiResponse(responseCode = "201", description = "Карта создана", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = CardDto.class))
            }),
            @ApiResponse(responseCode = "403", description = "Доступ запрещён (не ADMIN)")
        }
//        security = {
//            @SecurityRequirement(name = "bearerAuth")
//        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/api/admin/cards",
        produces = { "application/json" },
        consumes = { "application/json" }
    )

    ResponseEntity<CardDto> apiAdminCardsPost(
        @Parameter(name = "CardCreateRequest", description = "", required = true)
        @Valid
        @RequestBody CardCreateDto cardCreateRequest
    ) throws Exception;
}
