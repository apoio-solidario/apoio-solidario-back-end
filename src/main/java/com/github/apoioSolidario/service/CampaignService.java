package com.github.apoioSolidario.service;

import com.github.apoioSolidario.dto.mapper.CampaignMapper;
import com.github.apoioSolidario.dto.request.CampaignRequest;
import com.github.apoioSolidario.dto.request.UpdateStatusRequest;
import com.github.apoioSolidario.dto.response.CampaignResponse;
import com.github.apoioSolidario.dto.response.EventResponse;
import com.github.apoioSolidario.dto.response.OngResponse;
import com.github.apoioSolidario.exception.AccessDeniedException;
import com.github.apoioSolidario.model.Campaign;
import com.github.apoioSolidario.model.Ong;
import com.github.apoioSolidario.exception.EntityNotFoundException;
import com.github.apoioSolidario.repository.CampaignRepository;
import com.github.apoioSolidario.repository.OngRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CampaignService {

    private final CampaignRepository repository;
    private final OngRepository ongRepository;
    private final CampaignMapper mapper;
    private final TokenService tokenService;

    public CampaignService(CampaignRepository repository, OngRepository ongRepository, CampaignMapper mapper, TokenService tokenService) {
        this.repository = repository;
        this.ongRepository = ongRepository;
        this.mapper = mapper;
        this.tokenService = tokenService;
    }

    public CampaignResponse findById(UUID id) {
        var entity = repository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"campaign"));
        if(!checkAccessOngCampaign(entity.getOng().getOngId())){
            throw new AccessDeniedException("Acesso negado ao recurso solicitado");
        }
        return mapper.toObject(entity,CampaignResponse.class);
    }
    @Transactional()
    public CampaignResponse findByHandler(String handler) {
        var entity = repository.findByHandler(handler).orElseThrow(() -> new EntityNotFoundException(String.format("Campaingn com handler %s não encontrada", handler)));
        if(!checkAccessOngCampaign(entity.getOng().getOngId())){
            throw new AccessDeniedException("Acesso negado ao recurso solicitado");
        }
        return  mapper.toObject(entity, CampaignResponse.class);
    }
    public CampaignResponse save( CampaignRequest request) {
        Ong ong = ongRepository.findById(request.getOngId()).orElseThrow(()-> new EntityNotFoundException(request.getOngId(), "Ong"));
        Campaign entity = mapper.toObject(request,Campaign.class);
        if(!checkAccessOngCampaign(entity.getOng().getOngId())){
            throw new AccessDeniedException("Acesso negado ao recurso solicitado");
        }
        entity.setOng(ong);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return mapper.toObject(repository.save(entity),CampaignResponse.class);
    }

    public CampaignResponse update(UUID id, @Valid CampaignRequest request) {
        Campaign entity  = repository.findById(id).orElseThrow(
                ()->new EntityNotFoundException(id,"campaign")
        );
        if(!checkAccessOngCampaign(entity.getOng().getOngId())){
            throw new AccessDeniedException("Acesso negado ao recurso solicitado");
        }
        Ong ong = ongRepository.findById(request.getOngId()).orElseThrow(()-> new EntityNotFoundException(request.getOngId(), "Ong"));
        entity.setOng(ong);
        entity.setDescription(request.getDescription());
        entity.setContent(request.getContent());
        entity.setHandler(request.getHandler());
        entity.setGoalAmount(request.getGoalAmount());
        entity.setAmountRaised(request.getAmountRaised());
        entity.setStatus(request.getStatus());
        entity.setTitle(request.getTitle());
        entity.setStartData(request.getStartData());
        entity.setEndData(request.getEndData());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        return mapper.toObject(repository.save(entity),CampaignResponse.class);
    }
    public CampaignResponse updateStatus(@Valid UUID id, @Valid UpdateStatusRequest request) {
        Campaign entity  = repository.findById(id).orElseThrow(
                ()->new EntityNotFoundException(id,"campaign")
        );
        if(!checkAccessOngCampaign(entity.getOng().getOngId())){
            throw new AccessDeniedException("Acesso negado ao recurso solicitado");
        }
        entity.setStatus(request.getStatus());
        entity.setUpdatedAt(LocalDateTime.now());
        return mapper.toObject(repository.save(entity),CampaignResponse.class);
    }

    public void deleteById(@Valid UUID id) {
        var entity = repository.findById(id).orElseThrow(()->new EntityNotFoundException(id,"campaign")
        );
        if(!checkAccessOngCampaign(entity.getOng().getOngId())){
            throw new AccessDeniedException("Acesso negado ao recurso solicitado");
        }
        repository.delete(entity);
    }

    public Page<CampaignResponse> findAll(Pageable pageable) {
        return  mapper.toPage(repository.findAll(pageable), CampaignResponse.class);
    }
    @Transactional
    public Page<CampaignResponse> finByOngId(UUID id, Pageable pageable) {
        if(!checkAccessOngCampaign(id)){
            throw new AccessDeniedException("Acesso negado ao recurso solicitado");
        }
        return mapper.toPage(repository.findByOng_OngId(id, pageable), CampaignResponse.class);
    }
    public boolean checkAccessOngCampaign(UUID ongId) {
        var logado = tokenService.getPrincipal();
        Ong ongLogada = ongRepository.findByUser_UserId(logado.getUserId()).orElseThrow(()->new EntityNotFoundException(logado.getUserId(),"Ong"));
        return tokenService.isAdmin() || ongLogada.getOngId().equals(ongId);
    }

}
