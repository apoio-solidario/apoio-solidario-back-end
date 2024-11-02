package com.github.apoioSolidario.services;

import com.github.apoioSolidario.domain.dto.mapper.EntityMapper;
import com.github.apoioSolidario.domain.dto.request.OngSocialRequest;
import com.github.apoioSolidario.domain.dto.response.OngSocialResponse;
import com.github.apoioSolidario.domain.model.OngSocial;
import com.github.apoioSolidario.exceptions.EntityNotFoundException;
import com.github.apoioSolidario.repositories.OngSocialRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OngSocialService {
    private final OngSocialRepository repository;

    public OngSocialService(OngSocialRepository repository) {
        this.repository = repository;
    }

    public List<OngSocialResponse> findAll() {
        return EntityMapper.toList(repository.findAll(),OngSocialResponse.class);
    }

    public OngSocialResponse findById(Long id) {
        var entity = repository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"OngSocial"));
        return EntityMapper.toObject(entity,OngSocialResponse.class);
    }

    public OngSocialResponse save( OngSocialRequest ongRequest) {
        System.out.println(ongRequest);
        OngSocial entity = EntityMapper.toObject(ongRequest, OngSocial.class);
        System.out.println(entity);
        return EntityMapper.toObject(repository.save(entity),OngSocialResponse.class);
    }

    public OngSocialResponse update( Long id, @Valid OngSocialRequest ongRequest) {
        var entity  = repository.findById(id).orElseThrow(()->new EntityNotFoundException(id,"OngSocial"));
        EntityMapper.entityModelMapper.map(ongRequest, entity);
        OngSocial response = repository.save(entity);
        return EntityMapper.toObject(response,OngSocialResponse.class);
    }

    public void deleteById(@Valid Long id) {
        var entity = repository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"Ong"));
        repository.delete(entity);
    }

}
