package com.github.apoioSolidario.web.controller;

import com.github.apoioSolidario.domain.dto.request.LocationRequest;
import com.github.apoioSolidario.domain.dto.request.OngRequest;
import com.github.apoioSolidario.domain.dto.response.LocationResponse;
import com.github.apoioSolidario.domain.dto.response.OngResponse;
import com.github.apoioSolidario.services.LocationService;
import com.github.apoioSolidario.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
@Tag(name = "Locais", description = "Contém todos os métodos relacionados a locais.")
@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService service;

    public LocationController(LocationService service) {
        this.service = service;
    }


    @Operation(summary = "Recuperar todos os locais")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todos os locais recuperados com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<LocationResponse>> getAllLocations() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @Operation(summary = "Recuperar um local específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Local encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Local não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<LocationResponse> getLocation(@Valid @PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @Operation(summary = "Atualizar um local específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Local atualizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Entidade não processável",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Local não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<LocationResponse> updateLocation(@Valid @PathVariable Long id,
                                                           @RequestBody @Valid LocationRequest request) {
        var response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Criar um novo local")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Local criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Entidade não processável",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    public ResponseEntity<LocationResponse> saveLocation(@Valid @RequestBody LocationRequest request) {
        var response = service.save(request);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    @Operation(summary = "Deletar um local específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Local deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Local não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@Valid @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
