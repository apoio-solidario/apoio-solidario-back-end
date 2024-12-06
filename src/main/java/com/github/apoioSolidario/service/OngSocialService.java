package com.github.apoioSolidario.service;

import com.github.apoioSolidario.dto.mapper.OngSocialMapper;
import com.github.apoioSolidario.dto.request.OngSocialRequest;
import com.github.apoioSolidario.dto.response.OngSocialResponse;
import com.github.apoioSolidario.exception.AccessDeniedException;
import com.github.apoioSolidario.model.Ong;
import com.github.apoioSolidario.model.OngSocial;
import com.github.apoioSolidario.exception.EntityNotFoundException;
import com.github.apoioSolidario.repository.OngRepository;
import com.github.apoioSolidario.repository.OngSocialRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OngSocialService {
    private final OngSocialRepository repository;
    private final OngRepository ongRepository;
    private final OngSocialMapper mapper;
    private final TokenService tokenService;

    public OngSocialService(OngSocialRepository repository, OngRepository ongRepository, OngSocialMapper mapper, TokenService tokenService) {
        this.repository = repository;
        this.ongRepository = ongRepository;
        this.mapper = mapper;
        this.tokenService = tokenService;
    }

    public Page<OngSocialResponse> findAll(Pageable pageable) {
        return mapper.toPage(repository.findAll(pageable),OngSocialResponse.class);
    }

    public OngSocialResponse findById(UUID id) {
        var entity = repository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"OngSocial"));
        if(!checkAccessOngSocial(entity.getOng().getOngId())){
            throw new AccessDeniedException("Acesso negado ao recurso solicitado");
        }
        return mapper.toObject(entity,OngSocialResponse.class);
    }

    public OngSocialResponse save( OngSocialRequest ongRequest) {
        OngSocial entity = mapper.toObject(ongRequest, OngSocial.class);
        if(!checkAccessOngSocial(ongRequest.getOngId())){
            throw new AccessDeniedException("Acesso negado ao recurso solicitado");
        }
        Ong ong = ongRepository.findById(ongRequest.getOngId()).orElseThrow(()->new EntityNotFoundException(ongRequest.getOngId(), "Ong"));
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setOng(ong);
        OngSocial response = repository.save(entity);
        return mapper.toObject(response,OngSocialResponse.class);
    }

    public OngSocialResponse update( UUID id, @Valid OngSocialRequest ongSocialRequest) {
        OngSocial entity  = repository.findById(id).orElseThrow(()->new EntityNotFoundException(id,"OngSocial"));
        if(!checkAccessOngSocial(entity.getOng().getOngId())){
            throw new AccessDeniedException("Acesso negado ao recurso solicitado");
        }
        entity.setUpdatedAt(LocalDateTime.now());
        Ong ong = ongRepository.findById(ongSocialRequest.getOngId()).orElseThrow(()->new EntityNotFoundException(ongSocialRequest.getOngId(),"Ong"));
        entity.setOng(ong);
        entity.setSocialUrl(ongSocialRequest.getSocialUrl());
        entity.setPlatform(ongSocialRequest.getPlatform());
        entity.setUsername(ongSocialRequest.getUsername());
        return mapper.toObject(repository.save(entity),OngSocialResponse.class);
    }

    public void deleteById(@Valid UUID id) {
        var entity = repository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"OngSocial"));
        if(!checkAccessOngSocial(entity.getOng().getOngId())){
            throw new AccessDeniedException("Acesso negado ao recurso solicitado");
        }
        repository.deleteById(entity.getOngSocialId());
    }
    @Transactional
    public Page<OngSocialResponse> finByOngId(UUID id, Pageable pageable) {
        if(!checkAccessOngSocial(id)){
            throw new AccessDeniedException("Acesso negado ao recurso solicitado");
        }
        return mapper.toPage(repository.findByOng_OngId(id, pageable), OngSocialResponse.class);
    }

    public boolean checkAccessOngSocial(UUID ongId) {
        var logado = tokenService.getPrincipal();
        Ong ongLogada = ongRepository.findByUser_UserId(logado.getUserId()).orElseThrow(()->new EntityNotFoundException(logado.getUserId(),"Ong"));
        return tokenService.isAdmin() || ongLogada.getOngId().equals(ongId);
    }

}
