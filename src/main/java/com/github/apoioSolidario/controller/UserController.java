package com.github.apoioSolidario.controller;

import com.github.apoioSolidario.dto.request.UserRequest;
import com.github.apoioSolidario.dto.response.UserResponse;
import com.github.apoioSolidario.exception.config.ErrorMessage;
import com.github.apoioSolidario.service.UserService;
import com.github.apoioSolidario.utils.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Usuarios", description = "Contem os metodos necessarios para gerenciar usuarios")

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final ResponseUtils responseUtils;

    public UserController(UserService userService, ResponseUtils responseUtils) {
        this.userService = userService;
        this.responseUtils = responseUtils;
    }

    @Operation(summary = "Recuperar todos os usuarios")
    @ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll(Pageable pageable) {
        Page<UserResponse> users = userService.findAll(pageable);
        List<UserResponse> userResponse = users.getContent();
        HttpHeaders headers = responseUtils.getHeaders(users);
        return ResponseEntity.ok().headers(headers).body(userResponse);
    }

    @Operation(summary = "Recuperar um usuario pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Entidade não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getByUserId(@Valid @PathVariable UUID id) {
        return ResponseEntity.ok().body(userService.finById(id));
    }


    @Operation(summary = "Atualizar um usuario pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurso atualizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Entidade não processável",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Entidade não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@Valid @PathVariable UUID id,
                                                   @RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.ok(userService.update(id, userRequest));
    }


    @Operation(summary = "Excluir um usuario pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurso excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Entidade não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOng(@Valid @PathVariable UUID id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
