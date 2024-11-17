package com.github.apoioSolidario.web.controller;

import com.github.apoioSolidario.domain.dto.request.OngRequest;
import com.github.apoioSolidario.domain.dto.request.UpdateStatusRequest;
import com.github.apoioSolidario.domain.dto.response.OngResponse;
import com.github.apoioSolidario.domain.model.Ong;
import com.github.apoioSolidario.services.OngService;
import com.github.apoioSolidario.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
@Tag(name = "ONGs", description = "Contém todos os métodos relacionados a organizações (ONGs).")@RestController
@RequestMapping("/ongs")
public class OngController {
    private final OngService ongService;

    public OngController(OngService ongService) {
        this.ongService = ongService;
    }

    @Operation(summary = "Recuperar todas as organizações")
    @ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso")
    @GetMapping
    public ResponseEntity<Page<OngResponse>> getAllOngs(Pageable pageable) {
        return ResponseEntity.ok().body(ongService.findAll(pageable));
    }

    @Operation(summary = "Recuperar uma organização pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Entidade não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<OngResponse> getOng(@Valid @PathVariable Long id) {
        return ResponseEntity.ok().body(ongService.findById(id));
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
    public ResponseEntity<OngResponse> updateOngStatus(@Valid @PathVariable Long id,
                                                 @RequestBody @Valid UpdateStatusRequest ongRequest) {
        return ResponseEntity.ok(ongService.updateStatus(id, ongRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OngResponse> updateOng(@Valid @PathVariable Long id,
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
                .buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    @Operation(summary = "Excluir uma organização pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurso excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Entidade não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOng(@Valid @PathVariable Long id) {
        ongService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
