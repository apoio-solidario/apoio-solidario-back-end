package com.github.apoioSolidario.services;

import com.github.apoioSolidario.domain.model.Ong;
import com.github.apoioSolidario.repositories.OngRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OngService {
    @Autowired
    private OngRepository repository;

    public List<Ong> findAll() {
            return  repository.findAll();
    }
}