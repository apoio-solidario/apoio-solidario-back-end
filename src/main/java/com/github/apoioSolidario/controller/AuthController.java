package com.github.apoioSolidario.controller;

import com.github.apoioSolidario.dto.request.AuthRegisterRequest;
import com.github.apoioSolidario.dto.request.AuthRequest;
import com.github.apoioSolidario.dto.response.AuthResponse;
import com.github.apoioSolidario.model.User;
import com.github.apoioSolidario.service.AuthService;
import com.github.apoioSolidario.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Autenticação", description = "Contem os metodos necessarios para realizar autenticação de usário")
@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, AuthService authService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @Operation(summary = "Registrar um novo usuário no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthRegisterRequest userRequestDTO) {
        AuthResponse responseDTO = authService.save(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @Operation(summary = "Realizar login no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest authRequest, HttpServletResponse response) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        ResponseCookie resCookie = ResponseCookie.from("SESSION_ID", token)
                .httpOnly(false)
                .sameSite("Strict")
                .secure(false)
                .path("/")
                .maxAge(Math.toIntExact(60 * 60))
                .build();
        response.addHeader("Set-Cookie", resCookie.toString());

        return ResponseEntity.ok().build();
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

    @GetMapping("/me")
    public ResponseEntity<?> me(HttpServletRequest request) {
        var user = tokenService.getPrincipal();
        var response = new AuthResponse();
        response.setUserId(user.getUserId());
        response.setUsername(user.getUsername());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        return ResponseEntity.ok(response);
    }
}