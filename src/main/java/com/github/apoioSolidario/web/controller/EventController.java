package com.github.apoioSolidario.web.controller;

import com.github.apoioSolidario.domain.dto.request.EventRequest;
import com.github.apoioSolidario.domain.dto.response.EventResponse;
import com.github.apoioSolidario.services.EventService;
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
@Tag(name = "Evento", description = "Contém todos os métodos relacionados a eventos.")@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService service;

    public EventController(EventService eventService) {
        this.service = eventService;
    }

    @Operation(summary = "Recuperar todos os eventos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todos os eventos recuperados com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @Operation(summary = "Recuperar um evento específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEvent(@Valid @PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @Operation(summary = "Atualizar um evento específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento atualizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Entidade não processável",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(@Valid @PathVariable Long id,
                                                     @RequestBody @Valid EventRequest request) {
        var response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Criar um novo evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Entidade não processável",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    public ResponseEntity<EventResponse> saveEvent(@Valid @RequestBody EventRequest request) {
        var response = service.save(request);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    @Operation(summary = "Deletar um evento específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@Valid @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
