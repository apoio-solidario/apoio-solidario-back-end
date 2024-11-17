package com.github.apoioSolidario.services;

import com.github.apoioSolidario.domain.dto.mapper.OngSocialMapper;
import com.github.apoioSolidario.domain.dto.request.OngSocialRequest;
import com.github.apoioSolidario.domain.dto.response.OngSocialResponse;
import com.github.apoioSolidario.domain.model.Ong;
import com.github.apoioSolidario.domain.model.OngSocial;
import com.github.apoioSolidario.exceptions.EntityNotFoundException;
import com.github.apoioSolidario.repositories.OngRepository;
import com.github.apoioSolidario.repositories.OngSocialRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OngSocialService {
    private final OngSocialRepository repository;
    private final OngRepository ongRepository;
    private final OngSocialMapper mapper;

    public OngSocialService(OngSocialRepository repository, OngRepository ongRepository, OngSocialMapper mapper) {
        this.repository = repository;
        this.ongRepository = ongRepository;
        this.mapper = mapper;
    }

    public Page<OngSocialResponse> findAll(Pageable pageable) {
        return mapper.toPage(repository.findAll(pageable),OngSocialResponse.class);
    }

    public OngSocialResponse findById(Long id) {
        var entity = repository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"OngSocial"));
        return mapper.toObject(entity,OngSocialResponse.class);
    }

    public OngSocialResponse save( OngSocialRequest ongRequest) {
        OngSocial entity = mapper.toObject(ongRequest, OngSocial.class);
        Ong ong = ongRepository.findById(ongRequest.getOngId()).orElseThrow(()->new EntityNotFoundException(ongRequest.getOngId(), "Ong"));
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setOng(ong);
        OngSocial response = repository.save(entity);
        return mapper.toObject(response,OngSocialResponse.class);
    }

    public OngSocialResponse update( Long id, @Valid OngSocialRequest ongSocialRequest) {
        OngSocial entity  = repository.findById(id).orElseThrow(()->new EntityNotFoundException(id,"OngSocial"));
        entity.setUpdatedAt(LocalDateTime.now());
        Ong ong = ongRepository.findById(ongSocialRequest.getOngId()).orElseThrow(()->new EntityNotFoundException(ongSocialRequest.getOngId(),"Ong"));
        entity.setOng(ong);
        entity.setSocialUrl(ongSocialRequest.getSocialUrl());
        entity.setPlatform(ongSocialRequest.getPlatform());
        entity.setUsername(ongSocialRequest.getUsername());
        return mapper.toObject(repository.save(entity),OngSocialResponse.class);
    }

    public void deleteById(@Valid Long id) {
        var entity = repository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"OngSocial"));
        repository.deleteById(entity.getId());
    }

}
