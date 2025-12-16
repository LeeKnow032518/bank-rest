package com.example.bank.rest.controller.auth;

import com.example.bank.rest.dto.auth.AuthResponse;
import com.example.bank.rest.dto.auth.LoginRequest;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@OpenAPIDefinition(info = @Info(title = "API аутентификации", version = "1.0"))
@Validated
@Tag(name = "Аутентификация", description = "Получение JWT-токена")
public interface AuthController {

    /**
     * POST /auth/login : Аутентификация и получение JWT
     *
     * @param loginRequest  (required)
     * @return Успешная аутентификация (status code 200)
     *         or Неверные учётные данные (status code 401)
     */
    @Operation(
        operationId = "authLoginPost",
        summary = "Аутентификация и получение JWT",
        tags = { "Аутентификация" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Успешная аутентификация", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))
            }),
            @ApiResponse(responseCode = "401", description = "Неверные учётные данные")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/auth/login",
        produces = { "application/json" },
        consumes = { "application/json" }
    )

    ResponseEntity<AuthResponse> authLoginPost(
        @Parameter(name = "LoginRequest", description = "", required = true)
        @Valid
        @RequestBody LoginRequest loginRequest
    );
}
