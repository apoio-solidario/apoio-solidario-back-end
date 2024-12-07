package com.github.apoioSolidario.controller;

import com.github.apoioSolidario.dto.request.CampaignRequest;
import com.github.apoioSolidario.dto.request.UpdateStatusRequest;
import com.github.apoioSolidario.dto.response.CampaignResponse;
import com.github.apoioSolidario.dto.response.EventResponse;
import com.github.apoioSolidario.service.CampaignService;
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
@RequestMapping("/campaigns")
@Tag(name = "Campanha", description = "Contém todos os métodos relacionados a campanhas.")
public class CampaignController {

    private final CampaignService service;
    private final ResponseUtils responseUtils;

    public CampaignController(CampaignService service, ResponseUtils responseUtils) {
        this.service = service;
        this.responseUtils = responseUtils;
    }

    @Operation(summary = "Recuperar todas as campanhas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todas as campanhas recuperadas com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<CampaignResponse>> getAllCampaigns(Pageable pageable,@RequestParam(required = false) String title,@RequestParam(required = false) String status) {
        Page<CampaignResponse> campanhas = service.findAll(pageable,title,status);
        List<CampaignResponse> campanhasResponse = campanhas.getContent();
        HttpHeaders headers = responseUtils.getHeaders(campanhas);
        return ResponseEntity.ok().headers(headers).body(campanhasResponse);
    }

    @Operation(summary = "Recuperar uma campanha específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Campanha encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Campanha não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<CampaignResponse> getCampaign(@Valid @PathVariable UUID id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @Operation(summary = "Recuperar uma campanha pelo handler")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Entidade não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/handler/{handler}")
    public ResponseEntity<CampaignResponse> getByHandler(@Valid @PathVariable String handler) {
        return ResponseEntity.ok().body(service.findByHandler(handler));
    }

    @Operation(summary = "Recuperar uma Camapanhas pelo id da ong associada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso")
    })
    @GetMapping("/ong/{id}")
    public ResponseEntity<List<CampaignResponse>> getByOngId(@Valid @PathVariable UUID id, Pageable pageable) {
        Page<CampaignResponse> campaigns = service.finByOngId(id, pageable);
        List<CampaignResponse> campaignResponses = campaigns.getContent();
        HttpHeaders headers = responseUtils.getHeaders(campaigns);
        return ResponseEntity.ok().headers(headers).body(campaignResponses);
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
    public ResponseEntity<CampaignResponse> updateStatusCampaign(@Valid @PathVariable UUID id,
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
    public ResponseEntity<CampaignResponse> updateCampaign(@Valid @PathVariable UUID id,
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
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getCampaignId()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    @Operation(summary = "Deletar uma campanha específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Campanha deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Campanha não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(@Valid @PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
