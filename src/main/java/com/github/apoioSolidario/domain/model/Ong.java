package com.github.apoioSolidario.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ongs")
public class Ong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
