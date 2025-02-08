package com.hussain.controller;

import com.hussain.exception.ObjectNotFoundException;
import com.hussain.request.LoginRequest;
import com.hussain.request.RegisterRequest;
import com.hussain.response.LoginResponse;
import com.hussain.response.Response;
import com.hussain.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest request) {
        log.info("Request initiated to register with details {}", request);
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
        log.info("Request initiated to login by user {}", request);
        Response response = service.authenticate(request);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
