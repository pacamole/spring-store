package com.muller.spring_store.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.muller.spring_store.dto.AuthenticationDTO;
import com.muller.spring_store.dto.RegisterDTO;
import com.muller.spring_store.infra.security.TokenService;
import com.muller.spring_store.model.User;
import com.muller.spring_store.service.AuthorizationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final AuthorizationService authService;
    private final TokenService tokenService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());

        var auth = authenticationManager.authenticate(usernamePassword);
        String token = tokenService.generateToken((User) auth.getPrincipal());

        return token;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid RegisterDTO data) {
        authService.register(data);
    }
}