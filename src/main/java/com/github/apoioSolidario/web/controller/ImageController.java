package com.github.apoioSolidario.web.controller;

import com.github.apoioSolidario.domain.dto.request.ImageRequest;
import com.github.apoioSolidario.domain.dto.response.ImageResponse;
import com.github.apoioSolidario.services.ImageService;
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

@Tag(name = "Imagens", description = "Contém todos os métodos relacionados a imagens.")@RestController
@RequestMapping("/images")
public class ImageController {
    
    private final ImageService service;

    public ImageController(ImageService service) {
        this.service = service;
    }

    @Operation(summary = "Recuperar todas as imagens")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todas as imagens recuperadas com sucesso")
    })
    @GetMapping
    public ResponseEntity<Page<ImageResponse>> getAllImages(Pageable pageable) {
        return ResponseEntity.ok().body(service.findAll(pageable));
    }

    @Operation(summary = "Recuperar uma imagem específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagem encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Imagem não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ImageResponse> getImage(@Valid @PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @Operation(summary = "Atualizar uma imagem específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagem atualizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Entidade não processável",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Imagem não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ImageResponse> updateImage(@Valid @PathVariable Long id,
                                                        @RequestBody @Valid ImageRequest imageRequest) {
        var response = service.update(id, imageRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Criar uma nova imagem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Imagem criada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Entidade não processável",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    public ResponseEntity<ImageResponse> saveImage(@Valid @RequestBody ImageRequest imageRequest) {
        var response = service.save(imageRequest);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    @Operation(summary = "Deletar uma imagem específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagem deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Imagem não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@Valid @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
