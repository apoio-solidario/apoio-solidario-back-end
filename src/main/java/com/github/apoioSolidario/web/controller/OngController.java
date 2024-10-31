package com.github.apoioSolidario.web.controller;

import com.github.apoioSolidario.domain.dto.request.OngRequest;
import com.github.apoioSolidario.domain.dto.response.OngResponse;
import com.github.apoioSolidario.domain.model.Ong;
import com.github.apoioSolidario.services.OngService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
@Tag(name = "Orgs", description = "tem todos os metodos relacionados  a ong")
@RestController
@RequestMapping("/ongs")
public class OngController {
    private final OngService ongService;

    public OngController(OngService ongService) {
        this.ongService = ongService;
    }

    @GetMapping
    public ResponseEntity<List<OngResponse>> getAllOngs() {
        return ResponseEntity.ok().body(ongService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OngResponse> getOng(@Valid  @PathVariable Long id) {
        return ResponseEntity.ok().body(ongService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OngResponse> updateOng(@Valid  @PathVariable Long id, @RequestBody @Valid OngRequest ongRequest) {
        var response = ongService.update(id, ongRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<OngResponse> saveOng(@Valid  @RequestBody OngRequest ongRequest) {
        var response = ongService.save(ongRequest);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(url).body(response);
    }

}
