package com.github.apoioSolidario.services;

import com.github.apoioSolidario.domain.dto.mapper.EntityMapper;
import com.github.apoioSolidario.domain.dto.request.OngRequest;
import com.github.apoioSolidario.domain.dto.response.OngResponse;
import com.github.apoioSolidario.domain.model.Ong;
import com.github.apoioSolidario.exceptions.EntityNotFoundException;
import com.github.apoioSolidario.repositories.OngRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OngService {

    private final OngRepository repository;

    public OngService(OngRepository repository) {
        this.repository = repository;
    }

    public Page<OngResponse> findAll(Pageable pageable) {
        return EntityMapper.toPage(repository.findAll(pageable),OngResponse.class);
    }

    public OngResponse findById(Long id) {
        var entity = repository.findById(id).orElseThrow(()-> new EntityNotFoundException(String.format("Ong com id %s não encontrada",id)));
        return EntityMapper.toObject(entity,OngResponse.class);
    }

    public OngResponse save( OngRequest ongRequest) {
        Ong entity = EntityMapper.toObject(ongRequest, Ong.class);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return EntityMapper.toObject(repository.save(entity),OngResponse.class);
    }

    public OngResponse update( Long id, @Valid OngRequest ongRequest) {
        var entity  = repository.findById(id).orElseThrow(()->new EntityNotFoundException(id,"Ong"));
        entity.setUpdatedAt(LocalDateTime.now());
        EntityMapper.entityModelMapper.map(ongRequest, entity);
        Ong response = repository.save(entity);
        return EntityMapper.toObject(response,OngResponse.class);
    }

    public void deleteById(@Valid Long id) {
        var entity = repository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"Ong"));
        repository.delete(entity);
    }
}