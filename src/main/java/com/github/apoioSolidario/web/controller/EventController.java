package com.github.apoioSolidario.web.controller;

import com.github.apoioSolidario.domain.dto.request.EventRequest;
import com.github.apoioSolidario.domain.dto.response.EventResponse;
import com.github.apoioSolidario.services.EventService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
@Tag(name = "Event", description = "tem todos os metodos relacionados  a event")
@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService service;

    public EventController(EventService eventService) {
        this.service = eventService;
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEvent(@Valid @PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(@Valid  @PathVariable Long id, @RequestBody @Valid EventRequest request) {
        var response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<EventResponse> saveEvent(@Valid  @RequestBody EventRequest request) {
        var response = service.save(request);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(url).body(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@Valid @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
