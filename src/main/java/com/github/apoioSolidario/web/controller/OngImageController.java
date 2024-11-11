package com.github.apoioSolidario.web.controller;

import com.github.apoioSolidario.domain.dto.request.OngImageRequest;
import com.github.apoioSolidario.domain.dto.response.OngImageResponse;
import com.github.apoioSolidario.services.OngImageService;
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

@Tag(name = "Imagens de ONGs", description = "Contém todos os métodos relacionados a imagens de organizações (ONGs).")@RestController
@RequestMapping("/ongImages")
public class OngImageController {
    
    private final OngImageService service;

    public OngImageController(OngImageService service) {
        this.service = service;
    }

    @Operation(summary = "Recuperar todas as imagens de ONGs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todas as imagens de ONGs recuperadas com sucesso")
    })
    @GetMapping
    public ResponseEntity<Page<OngImageResponse>> getAllOngImages(Pageable pageable) {
        return ResponseEntity.ok().body(service.findAll(pageable));
    }

    @Operation(summary = "Recuperar uma imagem específica de ONG pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagem de ONG encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Imagem de ONG não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<OngImageResponse> getOngImage(@Valid @PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @Operation(summary = "Atualizar uma imagem específica de ONG pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagem de ONG atualizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Entidade não processável",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Imagem de ONG não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<OngImageResponse> updateOngImage(@Valid @PathVariable Long id,
                                                           @RequestBody @Valid OngImageRequest ongImageRequest) {
        var response = service.update(id, ongImageRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Criar uma nova imagem de ONG")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Imagem de ONG criada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Entidade não processável",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    public ResponseEntity<OngImageResponse> saveOngImage(@Valid @RequestBody OngImageRequest ongImageRequest) {
        var response = service.save(ongImageRequest);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    @Operation(summary = "Deletar uma imagem específica de ONG pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagem de ONG deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Imagem de ONG não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOngImage(@Valid @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
