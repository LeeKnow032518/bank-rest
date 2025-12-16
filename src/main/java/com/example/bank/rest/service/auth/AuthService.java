package com.example.bank.rest.service.auth;

import com.example.bank.rest.dto.auth.AuthResponse;
import com.example.bank.rest.dto.auth.LoginRequest;
import com.example.bank.rest.entity.UserDetails;
import com.example.bank.rest.repository.UserRepository;
import com.example.bank.rest.security.JwtService;
import com.example.bank.rest.util.RoleEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    public AuthService(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public ResponseEntity<AuthResponse> authenticate(LoginRequest request){
        String username = request.getLogin();
        String password = request.getPassword();

        UserDetails user = userRepository.findByLogin(username)
            .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        if(user.getRole().equals("user") && user.getPassword().equals(password)){
            String token = jwtService.generateToken(user.getLogin()+ " " +user.getId(), RoleEnum.USER);
            return ResponseEntity.ok(new AuthResponse(token, RoleEnum.USER));
        }
        if(user.getRole().equals("admin") && user.getPassword().equals(password)){
            String token = jwtService.generateToken(user.getLogin(), RoleEnum.ADMIN);
            return ResponseEntity.ok(new AuthResponse(token, RoleEnum.ADMIN));
        }
        return ResponseEntity.status(401).build();
    }
}
