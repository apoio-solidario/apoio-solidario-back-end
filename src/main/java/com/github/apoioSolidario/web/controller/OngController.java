package com.github.apoioSolidario.web.controller;

import com.github.apoioSolidario.domain.model.Ong;
import com.github.apoioSolidario.services.OngService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ongs")
public class OngController {
    @Autowired
    private OngService ongService;

    @GetMapping
    public ResponseEntity<List<Ong>> getAllAnimals() {
        return ResponseEntity.ok().body(ongService.findAll());
    }
}
