package com.github.apoioSolidario.web.controller;

import com.github.apoioSolidario.domain.dto.request.CampaignRequest;
import com.github.apoioSolidario.domain.dto.request.UpdateStatusRequest;
import com.github.apoioSolidario.domain.dto.response.CampaignResponse;
import com.github.apoioSolidario.domain.dto.response.EventResponse;
import com.github.apoioSolidario.services.CampaignService;
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
@RequestMapping("/campaigns")
@Tag(name = "Campanha", description = "Contém todos os métodos relacionados a campanhas.")
public class CampaignController {

    private final CampaignService service;

    public CampaignController(CampaignService service) {
        this.service = service;
    }


    @Operation(summary = "Recuperar todas as campanhas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todas as campanhas recuperadas com sucesso")
    })
    @GetMapping
    public ResponseEntity<Page<CampaignResponse>> getAllCampaigns(Pageable pageable) {
        return ResponseEntity.ok().body(service.findAll(pageable));
    }

    @Operation(summary = "Recuperar uma campanha específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Campanha encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Campanha não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<CampaignResponse> getCampaign(@Valid @PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @Operation(summary = "Atualizar de status de um campanha específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status do campanha atualizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Entidade não processável",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Campanha não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PutMapping("status/{id}")
    public ResponseEntity<CampaignResponse> updateStatusCampaign(@Valid @PathVariable Long id,
                                                           @RequestBody @Valid UpdateStatusRequest request) {
        var response = service.updateStatus(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar uma campanha específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Campanha atualizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Entidade não processável",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Campanha não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<CampaignResponse> updateCampaign(@Valid @PathVariable Long id,
                                                           @RequestBody @Valid CampaignRequest request) {
        var response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Criar uma nova campanha")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Campanha criada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Entidade não processável",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    public ResponseEntity<CampaignResponse> saveCampaign(@Valid @RequestBody CampaignRequest request) {
        var response = service.save(request);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    @Operation(summary = "Deletar uma campanha específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Campanha deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Campanha não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(@Valid @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
