package com.example.bank.rest.controller.auth;

import com.example.bank.rest.dto.auth.AuthResponse;
import com.example.bank.rest.dto.auth.LoginRequest;
import com.example.bank.rest.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Autowired
    public AuthControllerImpl(AuthService authService) {
        this.authService = authService;
    }

    @Override
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/auth/login",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    public ResponseEntity<AuthResponse> authLoginPost(
        @Parameter(name = "LoginRequest", description = "", required = true)
        @Valid
        @RequestBody LoginRequest loginRequest
    ){
        return authService.authenticate(loginRequest);
    }

}
