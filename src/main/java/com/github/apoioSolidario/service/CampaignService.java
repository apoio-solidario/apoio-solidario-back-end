package com.github.apoioSolidario.service;

import com.github.apoioSolidario.dto.mapper.CampaignMapper;
import com.github.apoioSolidario.dto.request.CampaignRequest;
import com.github.apoioSolidario.dto.request.UpdateStatusRequest;
import com.github.apoioSolidario.dto.response.CampaignResponse;
import com.github.apoioSolidario.dto.response.OngResponse;
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

    public CampaignService(CampaignRepository repository, OngRepository ongRepository, CampaignMapper mapper) {
        this.repository = repository;
        this.ongRepository = ongRepository;
        this.mapper = mapper;
    }

    public CampaignResponse findById(UUID id) {
        var entity = repository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"campaign"));
        return mapper.toObject(entity,CampaignResponse.class);
    }
    @Transactional()
    public CampaignResponse findByHandler(String handler) {
        var entity = repository.findByHandler(handler).orElseThrow(() -> new EntityNotFoundException(String.format("Campaingn com handler %s não encontrada", handler)));
        return  mapper.toObject(entity, CampaignResponse.class);
    }
    public CampaignResponse save( CampaignRequest request) {
        Ong ong = ongRepository.findById(request.getOngId()).orElseThrow(()-> new EntityNotFoundException(request.getOngId(), "Ong"));
        Campaign entity = mapper.toObject(request,Campaign.class);
        entity.setOng(ong);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return mapper.toObject(repository.save(entity),CampaignResponse.class);
    }

    public CampaignResponse update(UUID id, @Valid CampaignRequest request) {
        Campaign entity  = repository.findById(id).orElseThrow(
                ()->new EntityNotFoundException(id,"campaign")
        );
        Ong ong = ongRepository.findById(request.getOngId()).orElseThrow(()-> new EntityNotFoundException(request.getOngId(), "Ong"));
        entity.setOng(ong);
        entity.setDescription(request.getDescription());
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
        entity.setStatus(request.getStatus());
        entity.setUpdatedAt(LocalDateTime.now());
        return mapper.toObject(repository.save(entity),CampaignResponse.class);
    }

    public void deleteById(@Valid UUID id) {
        var entity = repository.findById(id).orElseThrow(()->new EntityNotFoundException(id,"campaign")
        );
        repository.delete(entity);
    }

    public Page<CampaignResponse> findAll(Pageable pageable) {
        return  mapper.toPage(repository.findAll(pageable), CampaignResponse.class);
    }


}
