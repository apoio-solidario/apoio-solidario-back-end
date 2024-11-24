package com.github.apoioSolidario.controller;

import com.github.apoioSolidario.dto.request.AuthRequest;
import com.github.apoioSolidario.dto.request.UserRequest;
import com.github.apoioSolidario.dto.response.AuthResponse;
import com.github.apoioSolidario.dto.response.UserResponse;
import com.github.apoioSolidario.model.User;
import com.github.apoioSolidario.service.AuthService;
import com.github.apoioSolidario.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private AuthService authService;
    private TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, AuthService authService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
        this.tokenService = tokenService;
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthRequest authRequest) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest userRequestDTO) {
        UserResponse responseDTO = authService.register(userRequestDTO);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("${id}").buildAndExpand(responseDTO.getUserId()).toUri();
        return ResponseEntity.created(url).body(responseDTO);
    }
}