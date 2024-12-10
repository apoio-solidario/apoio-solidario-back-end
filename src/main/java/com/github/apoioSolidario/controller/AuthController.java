package com.github.apoioSolidario.controller;

import com.github.apoioSolidario.dto.request.AuthLoginRequest;
import com.github.apoioSolidario.dto.request.AuthRegisterRequest;
import com.github.apoioSolidario.dto.response.AuthResponse;
import com.github.apoioSolidario.model.User;
import com.github.apoioSolidario.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Autenticação", description = "Contem os metodos necessarios para realizar autenticação de usário")
@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
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
    public ResponseEntity<?> login(@RequestBody @Valid AuthLoginRequest authRequest, HttpServletRequest request) {
        HttpSession currentSession = request.getSession(false);
        if (currentSession != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("An active session already exists. Please log out first to create a new session.");
        }

        var authToken = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Regenerate session ID to prevent session fixation attacks
        HttpSession session = request.getSession();
        session.invalidate();

        // Generate new session
        session = request.getSession(true);
        session.setAttribute("username", authRequest.getUsername());

        var user = (User) authentication.getPrincipal();
        var response = new AuthResponse();
        response.setUserId(user.getUserId());
        response.setUsername(user.getUsername());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        return ResponseEntity.ok(response);
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
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        log.warn(String.valueOf(session));
        if (session != null) {
            log.warn("Invalidated");
            session.invalidate();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No active session found");
        }

        String username = (String) session.getAttribute("username");
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        User user = authService.loadUserByUsername(username);
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