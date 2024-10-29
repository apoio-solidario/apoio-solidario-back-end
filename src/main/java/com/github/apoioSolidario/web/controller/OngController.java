package com.github.apoioSolidario.web.controller;

import com.github.apoioSolidario.domain.dto.request.OngRequest;
import com.github.apoioSolidario.domain.dto.response.OngResponse;
import com.github.apoioSolidario.domain.model.Ong;
import com.github.apoioSolidario.services.OngService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.ok().body(ongService.update(id,ongRequest));
    }

    @PostMapping
    public ResponseEntity<OngResponse> saveOng(@Valid  @RequestBody OngRequest ongRequest) {
        return ResponseEntity.ok().body(ongService.save(ongRequest));
    }

}
