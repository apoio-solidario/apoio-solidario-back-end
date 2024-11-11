package com.github.apoioSolidario.web.controller;

import com.github.apoioSolidario.domain.dto.request.OngSocialRequest;
import com.github.apoioSolidario.domain.dto.response.OngSocialResponse;
import com.github.apoioSolidario.services.OngSocialService;
import com.github.apoioSolidario.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/ongSocials")
@Tag(name = "Redes Sociais de ONGs", description = "Contém todos os métodos relacionados às redes sociais de organizações (ONGs).")
public class OngSocialController {

    private final OngSocialService service;

    public OngSocialController(OngSocialService service) {
        this.service = service;
    }

    @Operation(summary = "Recuperar todas as redes sociais de ONGs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todas as redes sociais de ONGs recuperadas com sucesso")
    })
    @GetMapping
    public ResponseEntity<Page<OngSocialResponse>> getAllOngSocials(Pageable pageable) {
        return ResponseEntity.ok().body(service.findAll(pageable));
    }

    @Operation(summary = "Recuperar uma rede social específica de ONG pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rede social de ONG encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Rede social de ONG não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<OngSocialResponse> getOngSocial(@Valid @PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @Operation(summary = "Atualizar uma rede social específica de ONG pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rede social de ONG atualizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Entidade não processável",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Rede social de ONG não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<OngSocialResponse> updateOngSocial(@Valid @PathVariable Long id,
                                                             @RequestBody @Valid OngSocialRequest ongSocialRequest) {
        var response = service.update(id, ongSocialRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Criar uma nova rede social de ONG")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rede social de ONG criada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Entidade não processável",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    public ResponseEntity<OngSocialResponse> saveOngSocial(@Valid @RequestBody OngSocialRequest ongSocialRequest) {
        var response = service.save(ongSocialRequest);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    @Operation(summary = "Deletar uma rede social específica de ONG pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rede social de ONG deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Rede social de ONG não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOngSocial(@Valid @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
