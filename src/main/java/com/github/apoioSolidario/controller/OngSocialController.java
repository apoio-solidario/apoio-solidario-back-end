package com.github.apoioSolidario.controller;

import com.github.apoioSolidario.dto.request.OngSocialRequest;
import com.github.apoioSolidario.dto.response.ImageResponse;
import com.github.apoioSolidario.dto.response.OngSocialResponse;
import com.github.apoioSolidario.service.OngSocialService;
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

@RestController
@RequestMapping("/ongSocials")
@Tag(name = "Redes Sociais de ONGs", description = "Contém todos os métodos relacionados às redes sociais de organizações (ONGs).")
public class OngSocialController {

    private final OngSocialService service;
    private final ResponseUtils responseUtils;

    public OngSocialController(OngSocialService service, ResponseUtils responseUtils) {
        this.service = service;
        this.responseUtils = responseUtils;
    }

    @Operation(summary = "Recuperar todas as redes sociais de ONGs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todas as redes sociais de ONGs recuperadas com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<OngSocialResponse>> getAllOngSocials(Pageable pageable) {
        Page<OngSocialResponse> ongSocials = service.findAll(pageable);
        List<OngSocialResponse> ongSocialResponses = ongSocials.getContent();
        HttpHeaders headers = responseUtils.getHeaders(ongSocials);
        return ResponseEntity.ok().headers(headers).body(ongSocialResponses);    }

    @Operation(summary = "Recuperar uma rede social específica de ONG pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rede social de ONG encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Rede social de ONG não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<OngSocialResponse> getOngSocial(@Valid @PathVariable UUID id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @Operation(summary = "Recuperar uma redes siciais pelo id da ong associada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso")
    })
    @GetMapping("/ong/{id}")
    public ResponseEntity<List<OngSocialResponse>> getByOngId(@Valid @PathVariable UUID id, Pageable pageable) {
        Page<OngSocialResponse> ongSocials = service.finByOngId(id,pageable);
        List<OngSocialResponse> ongSocialResponse = ongSocials.getContent();
        HttpHeaders headers = responseUtils.getHeaders(ongSocials);
        return ResponseEntity.ok().headers(headers).body(ongSocialResponse);
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
    public ResponseEntity<OngSocialResponse> updateOngSocial(@Valid @PathVariable UUID id,
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
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getOngId()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    @Operation(summary = "Deletar uma rede social específica de ONG pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rede social de ONG deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Rede social de ONG não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOngSocial(@Valid @PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
