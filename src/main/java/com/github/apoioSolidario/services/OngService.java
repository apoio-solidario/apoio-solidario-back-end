package com.github.apoioSolidario.services;

import com.github.apoioSolidario.domain.dto.mapper.EntityMapper;
import com.github.apoioSolidario.domain.dto.request.OngRequest;
import com.github.apoioSolidario.domain.dto.response.OngResponse;
import com.github.apoioSolidario.domain.model.Ong;
import com.github.apoioSolidario.repositories.OngRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OngService {

    private final OngRepository repository;

    public OngService(OngRepository repository) {
        this.repository = repository;
    }

    public List<OngResponse> findAll() {
        return EntityMapper.toList(repository.findAll(),OngResponse.class);
    }

    public OngResponse findById(Long id) {
        return EntityMapper.toObject(repository.findById(id),OngResponse.class);
    }

    public OngResponse save( OngRequest ongRequest) {
        System.out.println("pre mapper");
        System.out.printf(ongRequest.toString());
        Ong entity = EntityMapper.toObject(ongRequest, Ong.class);
        System.out.println("pos mapper");
        System.out.printf(entity.toString());
        return EntityMapper.toObject(repository.save(entity),OngResponse.class);
    }

    public OngResponse update( Long id, @Valid OngRequest ongRequest) {
        Ong entity = EntityMapper.toObject(findById(id),Ong.class);
        EntityMapper.entityModelMapper.map(ongRequest, entity);
        Ong response = repository.save(entity);
        return EntityMapper.toObject(response,OngResponse.class);
    }
}