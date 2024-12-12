package com.github.apoioSolidario.controller;

import com.github.apoioSolidario.dto.request.EventRequest;
import com.github.apoioSolidario.dto.request.UpdateStatusRequest;
import com.github.apoioSolidario.dto.response.EventResponse;
import com.github.apoioSolidario.exception.config.ErrorMessage;
import com.github.apoioSolidario.service.EventService;
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

@Tag(name = "Evento", description = "Contém todos os métodos relacionados a eventos.")
@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService service;
    private final ResponseUtils responseUtils;

    public EventController(EventService service, ResponseUtils responseUtils) {
        this.service = service;
        this.responseUtils = responseUtils;
    }

    @Operation(summary = "Recuperar todos os eventos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todos os eventos recuperados com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents(Pageable pageable, @RequestParam(required = false) String title, @RequestParam(required = false) String status) {
        Page<EventResponse> events = service.findAll(pageable, title, status);
        List<EventResponse> eventResponses = events.getContent();
        HttpHeaders headers = responseUtils.getHeaders(events);
        return ResponseEntity.ok().headers(headers).body(eventResponses);
    }

    @Operation(summary = "Recuperar um evento específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEvent(@Valid @PathVariable UUID id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @Operation(summary = "Recuperar uma evento pelo handler")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Entidade não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/handler/{handler}")
    public ResponseEntity<EventResponse> getByHandler(@Valid @PathVariable String handler) {
        return ResponseEntity.ok().body(service.findByHandler(handler));
    }

    @Operation(summary = "Recuperar uma evento pelo id da ong associada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso")
    })
    @GetMapping("/ong/{id}")
    public ResponseEntity<List<EventResponse>> getByOngId(@Valid @PathVariable UUID id, Pageable pageable) {
        Page<EventResponse> events = service.finByOngId(id, pageable);
        List<EventResponse> eventResponses = events.getContent();
        HttpHeaders headers = responseUtils.getHeaders(events);
        return ResponseEntity.ok().headers(headers).body(eventResponses);
    }

//    @Operation(summary = "Recuperar uma evento pelo id da localização associada")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso")
//    })
//    @GetMapping("/location/{id}")
//    public ResponseEntity<List<EventResponse>> getByLocationId(@Valid @PathVariable UUID id, Pageable pageable) {
//        Page<EventResponse> events = service.finByLocationId(id,pageable);
//        List<EventResponse> eventResponses = events.getContent();
//        HttpHeaders headers = responseUtils.getHeaders(events);
//        return ResponseEntity.ok().headers(headers).body(eventResponses);
//    }

    @Operation(summary = "Atualizar um evento específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento atualizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Entidade não processável",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(@Valid @PathVariable UUID id,
                                                     @RequestBody @Valid EventRequest request) {
        var response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar de status de um evento específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status do evento atualizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Entidade não processável",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PutMapping("status/{id}")
    public ResponseEntity<EventResponse> updateStatusEvent(@Valid @PathVariable UUID id,
                                                           @RequestBody @Valid UpdateStatusRequest request) {
        return ResponseEntity.ok(service.updateStatus(id, request));
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
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getEventId()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    @Operation(summary = "Deletar um evento específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@Valid @PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
