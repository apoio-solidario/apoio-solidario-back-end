package com.github.apoioSolidario.services;

import com.github.apoioSolidario.domain.dto.mapper.EntityMapper;
import com.github.apoioSolidario.domain.dto.request.OngSocialRequest;
import com.github.apoioSolidario.domain.dto.response.OngSocialResponse;
import com.github.apoioSolidario.domain.model.Ong;
import com.github.apoioSolidario.domain.model.OngSocial;
import com.github.apoioSolidario.exceptions.EntityNotFoundException;
import com.github.apoioSolidario.repositories.OngRepository;
import com.github.apoioSolidario.repositories.OngSocialRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OngSocialService {
    private final OngSocialRepository repository;
    private final OngRepository ongRepository;

    public OngSocialService(OngSocialRepository repository, OngRepository ongRepository) {
        this.repository = repository;
        this.ongRepository = ongRepository;
    }

    public List<OngSocialResponse> findAll() {
        return EntityMapper.toList(repository.findAll(),OngSocialResponse.class);
    }

    public OngSocialResponse findById(Long id) {
        var entity = repository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"OngSocial"));
        return EntityMapper.toObject(entity,OngSocialResponse.class);
    }

    public OngSocialResponse save( OngSocialRequest ongRequest) {
        OngSocial entity = EntityMapper.toObject(ongRequest, OngSocial.class);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        OngSocial response = repository.save(entity);
        return EntityMapper.toObject(response,OngSocialResponse.class);
    }

    public OngSocialResponse update( Long id, @Valid OngSocialRequest ongSocialRequest) {
        OngSocial entity  = repository.findById(id).orElseThrow(()->new EntityNotFoundException(id,"OngSocial"));
        entity.setUpdatedAt(LocalDateTime.now());
        Ong ong = ongRepository.findById(ongSocialRequest.getOngId()).orElseThrow(()->new EntityNotFoundException(ongSocialRequest.getOngId(),"Ong"));
        entity.setOng(ong);
        entity.setSocialUrl(ongSocialRequest.getSocialUrl());
        entity.setPlatform(ongSocialRequest.getPlatform());
        entity.setUsername(ongSocialRequest.getUsername());
        return EntityMapper.toObject(repository.save(entity),OngSocialResponse.class);
    }

    public void deleteById(@Valid Long id) {
        var entity = repository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"OngSocial"));
        repository.deleteById(entity.getId());
    }

}
