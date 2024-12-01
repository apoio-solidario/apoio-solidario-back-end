package com.github.apoioSolidario.controller;

import com.github.apoioSolidario.dto.request.AuthRequest;
import com.github.apoioSolidario.dto.request.UserRequest;
import com.github.apoioSolidario.dto.response.AuthResponse;
import com.github.apoioSolidario.dto.response.UserResponse;
import com.github.apoioSolidario.model.User;
import com.github.apoioSolidario.service.AuthService;
import com.github.apoioSolidario.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Autenticação", description = "Contem os metodos necessarios para realizar autenticação de usário")
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

    @Operation(summary = "Realizar login no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso")
    })
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthRequest authRequest) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @Operation(summary = "Registrar um novo usuário no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso")
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest userRequestDTO) {
        UserResponse responseDTO = authService.register(userRequestDTO);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("${id}").buildAndExpand(responseDTO.getUserId()).toUri();
        return ResponseEntity.created(url).body(responseDTO);
    }

    @Operation(summary = "Recuperar senha de um usuário no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso")
    })
    @PostMapping("/reset-password")
    public ResponseEntity<String> redefinirSenha(@RequestParam @Valid String username) {
            authService.gerarResetToken(username);
            return ResponseEntity.ok("Um link para redefinir sua senha foi enviado ao e-mail.");
    }

    @Operation(summary = "Confirma o reset de senha de um usuário no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso")
    })
    @PostMapping("/reset-password/confirm")
    public ResponseEntity<String> confirmPasswordReset(
            @RequestParam String token,
            @RequestParam String novaSenha) {
        try {
            authService.resetPassword(token, novaSenha);
            return ResponseEntity.ok("Senha redefinida com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao redefinir a senha.");
        }
    }

    @Operation(summary = "Realizar logout no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout realizado com sucesso")
    })
    @PostMapping("/Logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (this.tokenService.validateToken(token).isEmpty()) {
            this.tokenService.addToBlackList(token);
        }
        return ResponseEntity.ok().build();
    }
}