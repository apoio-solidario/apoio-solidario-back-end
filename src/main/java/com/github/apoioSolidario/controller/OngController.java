package com.github.apoioSolidario.controller;

import com.github.apoioSolidario.dto.request.OngRequest;
import com.github.apoioSolidario.dto.request.UpdateStatusRequest;
import com.github.apoioSolidario.dto.response.OngResponse;
import com.github.apoioSolidario.service.OngService;
import com.github.apoioSolidario.exception.config.ErrorMessage;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Tag(name = "ONGs", description = "Contém todos os métodos relacionados a organizações (ONGs).")@RestController
@RequestMapping("/ongs")
public class OngController {
    private final OngService ongService;
    private final ResponseUtils responseUtils;

    public OngController(OngService ongService, ResponseUtils responseUtils) {
        this.ongService = ongService;
        this.responseUtils = responseUtils;
    }

    @Operation(summary = "Recuperar todas as organizações")
    @ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso")
    @GetMapping
    public ResponseEntity<List<OngResponse>> getAllOngs(Pageable pageable) {
        Page<OngResponse> ongs = ongService.findAll(pageable);
        List<OngResponse> ongResponses = ongs.getContent();
        HttpHeaders headers = responseUtils.getHeaders(ongs);
        return ResponseEntity.ok().headers(headers).body(ongResponses);
    }

    @Operation(summary = "Recuperar uma organização pelo ID do usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Entidade não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<List<OngResponse>> getByUserId(@Valid @PathVariable UUID id) {
        return ResponseEntity.ok().body(ongService.findByUserId(id));
    }

    @Operation(summary = "Recuperar uma organização pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Entidade não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<OngResponse> getOng(@Valid @PathVariable UUID id) {
        return ResponseEntity.ok().body(ongService.findById(id));
    }

    @Operation(summary = "Recuperar uma organização pelo handler")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Entidade não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/handler/{handler}")
    public ResponseEntity<OngResponse> getByHandler(@Valid @PathVariable String handler) {
        return ResponseEntity.ok().body(ongService.findByHandler(handler));
    }

    @Operation(summary = "Atualizar uma organização pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurso atualizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Entidade não processável",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Entidade não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PutMapping("status/{id}")
    public ResponseEntity<OngResponse> updateOngStatus(@Valid @PathVariable UUID id,
                                                 @RequestBody @Valid UpdateStatusRequest ongRequest) {
        return ResponseEntity.ok(ongService.updateStatus(id, ongRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OngResponse> updateOng(@Valid @PathVariable UUID id,
                                                 @RequestBody @Valid OngRequest ongRequest) {
        var response = ongService.update(id, ongRequest);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Criar uma nova organização")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Entidade não processável",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    public ResponseEntity<OngResponse> saveOng(@Valid @RequestBody OngRequest ongRequest) {
        var response = ongService.save(ongRequest);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.getOngId()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    @Operation(summary = "Excluir uma organização pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurso excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Entidade não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOng(@Valid @PathVariable UUID id) {
        ongService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
