package com.github.apoioSolidario.web.controller;

import com.github.apoioSolidario.domain.dto.request.OngImageRequest;
import com.github.apoioSolidario.domain.dto.response.OngImageResponse;
import com.github.apoioSolidario.services.OngImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Ong Images", description = "tem todos os metodos relacionados  a imagens das ongs")
@RestController
@RequestMapping("/ongImages")
public class OngImageController {
    
    private final OngImageService service;

    public OngImageController(OngImageService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<OngImageResponse>> getAllOngs() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OngImageResponse> getOng(@Valid @PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OngImageResponse> updateOng(@Valid  @PathVariable Long id, @RequestBody @Valid OngImageRequest ongImageRequest) {
        var response = service.update(id, ongImageRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<OngImageResponse> saveOng(@Valid  @RequestBody OngImageRequest ongImageRequest) {
        System.out.println(ongImageRequest.toString());
        var response = service.save(ongImageRequest);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOng(@Valid @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
