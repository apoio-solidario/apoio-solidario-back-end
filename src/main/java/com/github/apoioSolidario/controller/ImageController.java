package com.github.apoioSolidario.controller;

import com.github.apoioSolidario.dto.request.ImageRequest;
import com.github.apoioSolidario.dto.request.ImageUpdateRequest;
import com.github.apoioSolidario.dto.response.EventResponse;
import com.github.apoioSolidario.dto.response.ImageResponse;
import com.github.apoioSolidario.service.ImageService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Tag(name = "Imagens", description = "Contém todos os métodos relacionados a imagens.")@RestController
@RequestMapping("/images")
public class ImageController {
    
    private final ImageService service;
    private final ResponseUtils responseUtils;

    public ImageController(ImageService service, ResponseUtils responseUtils) {
        this.service = service;
        this.responseUtils = responseUtils;
    }

    @Operation(summary = "Recuperar todas as imagens")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todas as imagens recuperadas com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<ImageResponse>> getAllImages(Pageable pageable) {
        Page<ImageResponse> images = service.findAll(pageable);
        List<ImageResponse> imageResponses = images.getContent();
        HttpHeaders headers = responseUtils.getHeaders(images);
        return ResponseEntity.ok().headers(headers).body(imageResponses);
    }

    @Operation(summary = "Recuperar uma imagem específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagem encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Imagem não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ImageResponse> getImage(@Valid @PathVariable UUID id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @Operation(summary = "Recuperar uma imagens pelo id da ong associada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso")
    })
    @GetMapping("/ong/{id}")
    public ResponseEntity<List<ImageResponse>> getByOngId(@Valid @PathVariable UUID id, Pageable pageable) {
        Page<ImageResponse> images = service.finByEntityId(id,pageable);
        List<ImageResponse> imageResponse = images.getContent();
        HttpHeaders headers = responseUtils.getHeaders(images);
        return ResponseEntity.ok().headers(headers).body(imageResponse);
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
    public ResponseEntity<ImageResponse> updateImage(@PathVariable UUID id,
                                                     @Valid ImageUpdateRequest imageUpdateRequest) {
        var response = service.update(id, imageUpdateRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Criar uma nova imagem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Imagem criada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Entidade não processável",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    public ResponseEntity<ImageResponse> saveImage(@ModelAttribute @Valid ImageRequest imageRequest) {
        System.out.println(imageRequest);
        var response = service.save(imageRequest);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getImageId()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    @Operation(summary = "Deletar uma imagem específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagem deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Imagem não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@Valid @PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
