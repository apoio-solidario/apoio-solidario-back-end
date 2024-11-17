package com.github.apoioSolidario.services;

import com.github.apoioSolidario.domain.dto.mapper.GenericMapper;
import com.github.apoioSolidario.domain.dto.request.OngRequest;
import com.github.apoioSolidario.domain.dto.request.UpdateStatusRequest;
import com.github.apoioSolidario.domain.dto.response.OngResponse;
import com.github.apoioSolidario.domain.model.Ong;
import com.github.apoioSolidario.exceptions.EntityNotFoundException;
import com.github.apoioSolidario.repositories.OngRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OngService {

    private final OngRepository repository;
    private final GenericMapper mapper;

    public OngService(OngRepository repository, GenericMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<OngResponse> findAll(Pageable pageable) {
        return mapper.toPage(repository.findAll(pageable),OngResponse.class);
    }

    public OngResponse findById(Long id) {
        var entity = repository.findById(id).orElseThrow(()-> new EntityNotFoundException(String.format("Ong com id %s não encontrada",id)));
        return mapper.toObject(entity,OngResponse.class);
    }

    public OngResponse save( OngRequest ongRequest) {
        Ong entity = mapper.toObject(ongRequest, Ong.class);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return mapper.toObject(repository.save(entity),OngResponse.class);
    }

    public OngResponse update( Long id, @Valid OngRequest ongRequest) {
        var entity  = repository.findById(id).orElseThrow(()->new EntityNotFoundException(id,"Ong"));
        entity.setUpdatedAt(LocalDateTime.now());
        mapper.objectToObject(ongRequest, entity);
        Ong response = repository.save(entity);
        return mapper.toObject(response,OngResponse.class);
    }
    public OngResponse updateStatus(@Valid Long id, @Valid UpdateStatusRequest ongRequest) {
        var entity  = repository.findById(id).orElseThrow(()->new EntityNotFoundException(id,"Ong"));
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setStatus(ongRequest.getStatus());
        return  mapper.toObject(repository.save(entity),OngResponse.class);
    }

    public void deleteById(@Valid Long id) {
        var entity = repository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"Ong"));
        repository.delete(entity);
    }


}