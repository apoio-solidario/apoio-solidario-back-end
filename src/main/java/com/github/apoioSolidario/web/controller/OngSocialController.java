package com.github.apoioSolidario.web.controller;

import com.github.apoioSolidario.domain.dto.request.OngSocialRequest;
import com.github.apoioSolidario.domain.dto.response.OngSocialResponse;
import com.github.apoioSolidario.services.OngSocialService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/ongSocials")
@Tag(name = "Ong Social", description = "tem todos os metodos relacionados  a imagens das ongs")

public class OngSocialController {

    private final OngSocialService service;

    public OngSocialController(OngSocialService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<OngSocialResponse>> getAllOngSocials() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OngSocialResponse> getOngSocial(@Valid @PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OngSocialResponse> updateOngSocial(@Valid  @PathVariable Long id, @RequestBody @Valid OngSocialRequest ongSocialRequest) {
        var response = service.update(id, ongSocialRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<OngSocialResponse> saveOngSocial(@Valid  @RequestBody OngSocialRequest ongSocialRequest) {
        var response = service.save(ongSocialRequest);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOngSocial(@Valid @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
